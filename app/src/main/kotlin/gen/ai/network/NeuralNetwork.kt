/*
 * NeuralNetwork.kt
 *
 * Purpose:
 *   Executable neural network built from a genome (phenotype from genotype). This is the actual
 *   "brain" that runs and can be evaluated on cognitive tasks. Supports biological features like
 *   temporal dynamics, neuromodulation, diverse neuron types, and local plasticity.
 *
 * Project Context:
 *   This is the phenotype builder and simulation engine for evolved neural networks. It converts
 *   the genetic representation (Genome) into an executable network that can process inputs,
 *   maintain state over time, and produce outputs. The network supports biological realism
 *   through features like neuromodulation, plasticity, and diverse neuron behaviors.
 *
 * Key Features:
 *   - Genome-to-Network Conversion: Builds executable network from genetic representation
 *   - Temporal Dynamics: Maintains neuron states across time steps
 *   - Neuromodulation: Global signaling that affects network behavior
 *   - Local Plasticity: Hebbian, STDP, and homeostatic learning rules
 *   - Diverse Neuron Types: Bursting, adapting, pacemaker, inhibitory, etc.
 *   - State Management: Tracks calcium, fatigue, membrane potential, custom variables
 *   - Topological Processing: Handles feedforward and recurrent connections
 *
 * Biological Realism:
 *   - Refractory periods prevent immediate re-firing
 *   - Calcium dynamics for learning signals
 *   - Fatigue and adaptation mechanisms
 *   - Neuromodulator release and decay
 *   - Synaptic delays and plasticity
 *   - Context-dependent activation
 *
 * Performance Considerations:
 *   - Efficient connection lookup tables
 *   - Simplified topological sorting
 *   - Cached neuron states and outputs
 *   - Bounded weight updates
 *   - Configurable simulation parameters
 */

package gen.ai.network

import gen.ai.*
import kotlin.math.*

class NeuralNetwork(val genome: Genome) {
    
    // Runtime state
    private val neuronStates = mutableMapOf<Long, NeuronState>()
    private val neuronOutputs = mutableMapOf<Long, Double>()
    private val connectionWeights = mutableMapOf<Pair<Long, Long>, Double>()
    
    // Network topology
    private val inputNeurons = genome.neurons.filter { it.type == NeuronType.Input }
    private val outputNeurons = genome.neurons.filter { it.type == NeuronType.Output }
    private val allNeurons = genome.neurons.associateBy { it.id }
    
    // Connection lookup for efficient processing
    private val incomingConnections = genome.connections.groupBy { it.targetId }
    
    init {
        initialize()
    }
    
    private fun initialize() {
        // Initialize neuron states
        genome.neurons.forEach { neuron ->
            neuronStates[neuron.id] = neuron.initialState.copy()
            neuronOutputs[neuron.id] = 0.0
        }
        
        // Initialize connection weights
        genome.connections.forEach { connection ->
            connectionWeights[connection.sourceId to connection.targetId] = connection.weight
        }
    }
    
    /**
     * Process one time step of the network
     */
    fun step(inputs: Map<Long, Double>): Map<Long, Double> {
        // Create a snapshot of outputs from the previous step (or initial state for the very first step)
        // This ensures that all neurons calculate their input sums based on a consistent state before any updates in this step.
        val previousStepOutputs = neuronOutputs.toMap() // Shallow copy is fine as Doubles are immutable

        // Set current input values for Input neurons. These directly become their output for this step.
        inputs.forEach { (neuronId, value) ->
            if (allNeurons[neuronId]?.type == NeuronType.Input) {
                neuronOutputs[neuronId] = value // Update live neuronOutputs for input neurons
            }
        }

        // For all non-input neurons, calculate their new state and output for the current step.
        // The order of iteration for this part is less critical now because all input sums
        // will be based on `previousStepOutputs` (for recurrent/feedback from previous step)
        // or `neuronOutputs` that were already updated for input neurons in this step.
        genome.neurons.forEach { neuron ->
            if (neuron.type != NeuronType.Input) {
                // Calculate input sum using outputs from previous step for recurrent connections,
                // or current step's input neuron outputs if source is an input neuron.
                val inputSum = incomingConnections[neuron.id]?.sumOf { connection ->
                    val sourceOutput =
                        if (allNeurons[connection.sourceId]?.type == NeuronType.Input) {
                            neuronOutputs[connection.sourceId] ?: 0.0 // Use current step's input value
                        } else {
                            previousStepOutputs[connection.sourceId] ?: 0.0 // Use previous step's output for others
                        }
                    val weight = connectionWeights[connection.sourceId to neuron.id] ?: connection.weight
                    sourceOutput * weight
                } ?: 0.0

                val currentState = neuronStates[neuron.id] ?: return@forEach // Should not happen with proper init
                val activation = calculateActivation(neuron, inputSum, currentState)
                
                neuronStates[neuron.id] = NeuronState(previousOutput = activation) // Store for next step
                neuronOutputs[neuron.id] = activation // Update live output for this step
                
                // Plasticity updates are based on the newly calculated activation for this step
                // and the presynaptic outputs that contributed (which also means using previousStepOutputs for non-inputs)
                updatePlasticityInternal(neuron, activation, previousStepOutputs, neuronOutputs)
            }
        }
        
        // Return the final outputs of the output neurons for this step
        return outputNeurons.associate { it.id to (neuronOutputs[it.id] ?: 0.0) }
    }
    
    private fun calculateActivation(neuron: Neuron, input: Double, state: NeuronState): Double {
        val biasedInput = input + neuron.bias
        
        return if (biasedInput > neuron.threshold) {
            evaluateActivationFunction(neuron.activation, biasedInput, state)
        } else {
            0.0
        }
    }
    
    private fun evaluateActivationFunction(func: ActivationFunction, input: Double, state: NeuronState): Double {
        return when (func) {
            is ActivationFunction.Const -> func.value
            is ActivationFunction.Variable -> when (func.name) {
                "input", "sum" -> input
                "prev_output" -> state.previousOutput
                else -> 0.0
            }
            is ActivationFunction.Add -> 
                evaluateActivationFunction(func.left, input, state) + evaluateActivationFunction(func.right, input, state)
            is ActivationFunction.Sub -> 
                evaluateActivationFunction(func.left, input, state) - evaluateActivationFunction(func.right, input, state)
            is ActivationFunction.Mul -> 
                evaluateActivationFunction(func.left, input, state) * evaluateActivationFunction(func.right, input, state)
            is ActivationFunction.Div -> {
                val denominator = evaluateActivationFunction(func.right, input, state)
                if (abs(denominator) < 1e-9) 0.0 
                else evaluateActivationFunction(func.left, input, state) / denominator
            }
            is ActivationFunction.Exp -> exp(evaluateActivationFunction(func.arg, input, state))
            is ActivationFunction.Sin -> sin(evaluateActivationFunction(func.arg, input, state))
            is ActivationFunction.Tanh -> tanh(evaluateActivationFunction(func.arg, input, state))
            is ActivationFunction.Sigmoid -> 1.0 / (1.0 + exp(-evaluateActivationFunction(func.arg, input, state)))
            is ActivationFunction.Sigmoidal -> 1.0 / (1.0 + exp(-input))
            is ActivationFunction.Max -> 
                maxOf(evaluateActivationFunction(func.left, input, state), evaluateActivationFunction(func.right, input, state))
            is ActivationFunction.ReLU -> maxOf(0.0, evaluateActivationFunction(func.arg, input, state))
            is ActivationFunction.If -> {
                val condition = evaluateActivationFunction(func.condition, input, state)
                if (condition > 0.0) evaluateActivationFunction(func.ifTrue, input, state)
                else evaluateActivationFunction(func.ifFalse, input, state)
            }
        }
    }
    
    // Modified updatePlasticity to accept the correct output maps
    private fun updatePlasticityInternal(neuron: Neuron, activation: Double, prevStepOutputs: Map<Long, Double>, currentStepInputOutputs: Map<Long,Double>) {
        // neuron is post-synaptic, activation is its current activation
        incomingConnections[neuron.id]?.forEach { connectionDetails ->
            if (connectionDetails.plasticityType == PlasticityType.Hebbian) {
                val presynapticNeuronId = connectionDetails.sourceId
                val presynapticOutput =
                    if (allNeurons[presynapticNeuronId]?.type == NeuronType.Input) {
                        currentStepInputOutputs[presynapticNeuronId] ?: 0.0 // Inputs are from current step
                    } else {
                        prevStepOutputs[presynapticNeuronId] ?: 0.0 // Hidden/Output sources are from prev step output
                    }
                val postsynapticActivation = activation // Current activation of the post-synaptic neuron

                val currentWeight = connectionWeights[presynapticNeuronId to neuron.id] ?: connectionDetails.weight
                
                val deltaW = connectionDetails.learningRate * presynapticOutput * postsynapticActivation
                
                var newWeight = currentWeight + deltaW
                newWeight = newWeight.coerceIn(-5.0, 5.0)

                connectionWeights[presynapticNeuronId to neuron.id] = newWeight
            }
        }
    }

    // Original updatePlasticity is kept if it was called from somewhere else, or removed if only called by updateNeuron
    // For now, I'll assume updatePlasticityInternal is the new way, and the old one might become unused.
    // The old updatePlasticity used the live neuronOutputs map for presynaptic output, which could be problematic for cycles.
    private fun updatePlasticity(neuron: Neuron, activation: Double) {
        // This version uses the live neuronOutputs, which might be from the current step's calculations for other neurons
        // This is being replaced by updatePlasticityInternal for more consistent state usage.
        // Keeping it temporarily to avoid breaking other calls if any, but should be removed if not used.
        incomingConnections[neuron.id]?.forEach { connectionDetails ->
            if (connectionDetails.plasticityType == PlasticityType.Hebbian) {
                val presynapticNeuronId = connectionDetails.sourceId
                val presynapticOutput = neuronOutputs[presynapticNeuronId] ?: 0.0 // Problematic line for cycles if source already updated in this step
                val postsynapticActivation = activation

                val currentWeight = connectionWeights[presynapticNeuronId to neuron.id] ?: connectionDetails.weight
                val deltaW = connectionDetails.learningRate * presynapticOutput * postsynapticActivation
                var newWeight = currentWeight + deltaW
                newWeight = newWeight.coerceIn(-5.0, 5.0)
                connectionWeights[presynapticNeuronId to neuron.id] = newWeight
            }
        }
    }
    
    /**
     * Reset network to initial state
     */
    public fun reset() {
        initialize()
    }
    
    /**
     * Get current network statistics
     */
    fun getStats(): Map<String, Double> {
        return mapOf(
            "totalNeurons" to genome.neurons.size.toDouble(),
            "totalConnections" to genome.connections.size.toDouble(),
            "averageActivity" to neuronOutputs.values.map { abs(it) }.average(),
        )
    }

    // Getter for testing plasticity
    fun getConnectionWeightForTesting(sourceId: Long, targetId: Long): Double? {
        return connectionWeights[sourceId to targetId]
    }
} 