package gen.ai.network

import gen.ai.ActivationFunction
import gen.ai.Connection
import gen.ai.Genome
import gen.ai.Neuron
import gen.ai.NeuronType
import gen.ai.PlasticityType
import gen.ai.ResourceBudgets
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.doubles.shouldBeExactly
import io.kotest.matchers.maps.shouldContainKey
import kotlin.math.exp
import kotlin.math.tanh

class NeuralNetworkTest : StringSpec({
    "step should propagate input and activate output neuron correctly" {
        val inputNeuron = Neuron(
            id = 1L,
            type = NeuronType.Input,
            activation = ActivationFunction.Sigmoidal, // Input neuron activation usually not critical for direct output
            bias = 0.0,
            threshold = 0.0,
            innovationId = 1L
        )
        val outputNeuron = Neuron(
            id = 2L,
            type = NeuronType.Output,
            activation = ActivationFunction.Sigmoidal, // Output neuron uses Sigmoidal
            bias = 0.0,
            threshold = 0.0,
            innovationId = 2L
        )
        val connection = Connection(
            sourceId = 1L,
            targetId = 2L,
            weight = 1.0,
            learningRate = 0.01,
            delay = 0,
            plasticityType = PlasticityType.None,
            innovationId = 3L
        )
        val genome = Genome(
            neurons = listOf(inputNeuron, outputNeuron),
            connections = listOf(connection),
            resourceBudgets = ResourceBudgets(2, 1, 0, 1),
            generation = 0
        )

        val network = NeuralNetwork(genome)
        val inputs = mapOf(1L to 0.5) // Input 0.5 to neuron 1
        val outputs = network.step(inputs)

        outputs shouldContainKey 2L
        val expectedOutput = 1.0 / (1.0 + exp(-(0.5 * 1.0 + 0.0))) // input * weight + bias, then sigmoid
        outputs[2L]?.shouldBeExactly(expectedOutput)
    }

    "step should correctly evaluate Tanh(Variable('sum')) activation" {
        val inputNeuron = Neuron(
            id = 1L,
            type = NeuronType.Input,
            activation = ActivationFunction.Sigmoidal, // Not critical for this test focus
            bias = 0.0,
            threshold = 0.0,
            innovationId = 1L
        )
        val outputNeuron = Neuron(
            id = 2L,
            type = NeuronType.Output,
            activation = ActivationFunction.Tanh(ActivationFunction.Variable("sum")),
            bias = 0.0,
            threshold = 0.0,
            innovationId = 2L
        )
        val connection = Connection(
            sourceId = 1L,
            targetId = 2L,
            weight = 0.5, // Test weight
            learningRate = 0.0,
            delay = 0,
            plasticityType = PlasticityType.None,
            innovationId = 3L
        )
        val genome = Genome(
            neurons = listOf(inputNeuron, outputNeuron),
            connections = listOf(connection),
            resourceBudgets = ResourceBudgets(2, 1, 0, 2), // Depth is 2 for Tanh(Variable)
            generation = 0
        )

        val network = NeuralNetwork(genome)
        val inputs = mapOf(1L to 0.8) // Input 0.8 to neuron 1
        val outputs = network.step(inputs)

        outputs shouldContainKey 2L
        val inputSum = 0.8 * 0.5 + 0.0 // inputVal * weight + bias
        val expectedOutputVal = kotlin.math.tanh(inputSum)
        outputs[2L]?.shouldBeExactly(expectedOutputVal)
    }

    "step should apply bias and threshold correctly" {
        val inputNeuron = Neuron(
            id = 1L,
            type = NeuronType.Input,
            activation = ActivationFunction.Sigmoidal, // Not critical
            bias = 0.0,
            threshold = 0.0,
            innovationId = 1L
        )
        val outputNeuron = Neuron(
            id = 2L,
            type = NeuronType.Output,
            activation = ActivationFunction.Sigmoidal, // Using Sigmoidal for testing output
            bias = 0.1,
            threshold = 0.5,
            innovationId = 2L
        )
        val connection = Connection(
            sourceId = 1L,
            targetId = 2L,
            weight = 1.0,
            learningRate = 0.0,
            delay = 0,
            plasticityType = PlasticityType.None,
            innovationId = 3L
        )
        val genome = Genome(
            neurons = listOf(inputNeuron, outputNeuron),
            connections = listOf(connection),
            resourceBudgets = ResourceBudgets(2, 1, 0, 1),
            generation = 0
        )

        val network = NeuralNetwork(genome)

        // Test Case 1: Threshold MET
        var inputs = mapOf(1L to 0.5) // Input value of 0.5
        var outputs = network.step(inputs)
        outputs shouldContainKey 2L
        var inputSum = 0.5 * 1.0 // signal * weight
        var biasedInput = inputSum + outputNeuron.bias // add bias
        // biasedInput = 0.5 + 0.1 = 0.6. Threshold is 0.5. 0.6 > 0.5 is true.
        var expectedOutput = 1.0 / (1.0 + exp(-biasedInput)) // Sigmoidal of biasedInput
        outputs[2L]?.shouldBeExactly(expectedOutput)

        // Test Case 2: Threshold NOT MET
        // Reset network state if necessary (step might have side effects on NeuronState, though not for this simple case)
        // For this stateless activation and no plasticity, reset might not be strictly needed for subsequent calls to step
        // but good practice if state was involved. Here, a fresh 'step' is fine as inputs are per-step.
        inputs = mapOf(1L to 0.3) // Input value of 0.3
        outputs = network.step(inputs)
        outputs shouldContainKey 2L
        inputSum = 0.3 * 1.0 // signal * weight
        biasedInput = inputSum + outputNeuron.bias // add bias
        // biasedInput = 0.3 + 0.1 = 0.4. Threshold is 0.5. 0.4 > 0.5 is false.
        expectedOutput = 0.0 // Output should be 0.0 when threshold is not met
        outputs[2L]?.shouldBeExactly(expectedOutput)
    }

    "step should apply Hebbian plasticity correctly and not change non-plastic weights" {
        val inputNeuron = Neuron(id = 1L, type = NeuronType.Input, activation = ActivationFunction.Sigmoidal, bias = 0.0, threshold = 0.0, innovationId = 1L)
        val outputNeuron = Neuron(id = 2L, type = NeuronType.Output, activation = ActivationFunction.Sigmoidal, bias = 0.0, threshold = 0.0, innovationId = 2L)
        
        val hebbianConnection = Connection(
            sourceId = 1L, targetId = 2L, weight = 0.5, learningRate = 0.1, delay = 0, 
            plasticityType = PlasticityType.Hebbian, innovationId = 3L
        )
        val nonPlasticConnection = Connection(
            sourceId = 1L, targetId = 2L, weight = 0.7, learningRate = 0.1, delay = 0, 
            plasticityType = PlasticityType.None, innovationId = 4L // Different innovation ID for a different conceptual connection if it were part of a list
        )

        // Test Hebbian Connection
        var genome = Genome(
            neurons = listOf(inputNeuron, outputNeuron),
            connections = listOf(hebbianConnection),
            resourceBudgets = ResourceBudgets(2,1,0,1), generation = 0
        )
        var network = NeuralNetwork(genome)
        var inputs = mapOf(1L to 1.0) // Pre-synaptic neuron fires strongly
        
        val presynapticOutput = 1.0 // Neuron 1 output is its input
        val postsynapticInputSum = presynapticOutput * hebbianConnection.weight // 1.0 * 0.5 = 0.5
        val postsynapticActivation = 1.0 / (1.0 + exp(-postsynapticInputSum)) // Sigmoidal(0.5)

        network.step(inputs) // This will trigger updatePlasticity

        val deltaW = hebbianConnection.learningRate * presynapticOutput * postsynapticActivation
        var expectedNewWeightHebbian = hebbianConnection.weight + deltaW
        expectedNewWeightHebbian = expectedNewWeightHebbian.coerceIn(-5.0, 5.0)
        
        network.getConnectionWeightForTesting(1L, 2L)?.shouldBeExactly(expectedNewWeightHebbian)

        // Test Non-Plastic Connection
        genome = Genome(
            neurons = listOf(inputNeuron, outputNeuron),
            connections = listOf(nonPlasticConnection),
            resourceBudgets = ResourceBudgets(2,1,0,1), generation = 0
        )
        network = NeuralNetwork(genome)
        inputs = mapOf(1L to 1.0)
        network.step(inputs)
        
        // Weight should remain unchanged for non-plastic connection
        network.getConnectionWeightForTesting(1L, 2L)?.shouldBeExactly(nonPlasticConnection.weight)
    }
}) 