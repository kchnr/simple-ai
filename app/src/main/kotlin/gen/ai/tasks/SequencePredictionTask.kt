/*
 * SequencePredictionTask.kt
 *
 * Purpose:
 *   Simple temporal sequence prediction task for testing evolved neural networks.
 *   Networks must predict the next value in a temporal sequence, testing their ability
 *   to learn patterns, maintain memory, and process temporal information.
 *
 * Project Context:
 *   This is one of the core cognitive tasks used to evaluate evolved neural networks.
 *   The goal is to find minimal "brains" that can solve temporal prediction problems
 *   using biologically plausible mechanisms. This task tests memory, pattern recognition,
 *   and temporal processing capabilities.
 *
 * Task Description:
 *   - Networks receive a sequence of values one at a time
 *   - They must predict the next value in the sequence
 *   - Sequences can be sine waves, repeating patterns, Fibonacci-like, or random walks
 *   - Fitness is based on prediction accuracy (lower error = higher fitness)
 *   - Multiple trials test generalization across different sequence types
 *
 * Sequence Types:
 *   - SINE_WAVE: Smooth periodic pattern testing continuous prediction
 *   - REPEATING_PATTERN: Fixed cycle testing discrete pattern memory
 *   - FIBONACCI_LIKE: Mathematical sequence testing rule learning
 *   - RANDOM_WALK: Stochastic sequence testing adaptation and tracking
 *
 * Evaluation Metrics:
 *   - Prediction Error: Absolute difference between prediction and target
 *   - Fitness: 1/(1+error) - higher values for better performance
 *   - Generalization: Performance across different sequence types
 *   - Consistency: Stable performance across multiple trials
 *
 * Biological Relevance:
 *   - Tests temporal memory and prediction (key cognitive functions)
 *   - Requires integration of past information with current input
 *   - May benefit from plasticity and adaptation mechanisms
 *   - Simple enough to see clear evolutionary progress
 *   - Analogous to biological sequence learning and prediction tasks
 */

package gen.ai.tasks

import gen.ai.network.NeuralNetwork
import kotlin.math.*
import kotlin.random.Random

// Helper function for Gaussian random numbers
fun Random.nextGaussian(): Double {
    var u1: Double
    var u2: Double
    var w: Double
    do {
        u1 = 2.0 * nextDouble() - 1.0
        u2 = 2.0 * nextDouble() - 1.0
        w = u1 * u1 + u2 * u2
    } while (w >= 1.0)
    
    w = sqrt(-2.0 * ln(w) / w)
    return u1 * w
}

/**
 * A simple sequence prediction task where the network must predict the next value
 * in a temporal sequence. Tests memory and temporal processing capabilities.
 */
class SequencePredictionTask(
    val sequenceLength: Int = 10,
    val inputSize: Int = 1,
    val outputSize: Int = 1,
    val seed: Long = 42
) {
    
    private val random = Random(seed)
    
    /**
     * Generate a test sequence with patterns
     */
    fun generateSequence(type: SequenceType = SequenceType.SINE_WAVE): List<Double> {
        return when (type) {
            SequenceType.SINE_WAVE -> {
                (0 until sequenceLength).map { t ->
                    sin(t * 0.3) * 0.5 + 0.5 // Normalized sine wave
                }
            }
            SequenceType.REPEATING_PATTERN -> {
                val pattern = listOf(0.1, 0.3, 0.7, 0.2)
                (0 until sequenceLength).map { t ->
                    pattern[t % pattern.size]
                }
            }
            SequenceType.FIBONACCI_LIKE -> {
                val sequence = mutableListOf(0.1, 0.2)
                while (sequence.size < sequenceLength) {
                    val next = (sequence[sequence.size - 1] + sequence[sequence.size - 2]) % 1.0
                    sequence.add(next)
                }
                sequence.take(sequenceLength)
            }
            SequenceType.RANDOM_WALK -> {
                var value = 0.5
                (0 until sequenceLength).map {
                    value += random.nextGaussian() * 0.1
                    value = value.coerceIn(0.0, 1.0)
                    value
                }
            }
        }
    }
    
    /**
     * Evaluate a network on the sequence prediction task
     */
    fun evaluate(network: NeuralNetwork, numTrials: Int = 5): TaskResult {
        var totalError = 0.0
        var totalPredictions = 0
        val trialResults = mutableListOf<TrialResult>()
        
        repeat(numTrials) { trial ->
            val sequence = generateSequence(SequenceType.values().random(random))
            val result = evaluateSingleTrial(network, sequence)
            trialResults.add(result)
            totalError += result.error
            totalPredictions += result.predictions
        }
        
        val averageError = if (totalPredictions > 0) totalError / totalPredictions else 1.0
        val fitness = 1.0 / (1.0 + averageError) // Higher fitness for lower error
        
        return TaskResult(
            fitness = fitness,
            averageError = averageError,
            totalPredictions = totalPredictions,
            trials = trialResults
        )
    }
    
    private fun evaluateSingleTrial(network: NeuralNetwork, sequence: List<Double>): TrialResult {
        network.reset()
        
        var totalError = 0.0
        var predictions = 0
        val errors = mutableListOf<Double>()
        
        // Present sequence and collect predictions
        for (i in 0 until sequence.size - 1) {
            val input = mapOf(1L to sequence[i]) // Assuming input neuron has ID 1
            val output = network.step(input)
            
            // Get prediction (assuming output neuron has ID 2)
            val prediction = output[2L] ?: 0.0
            val target = sequence[i + 1]
            val error = abs(prediction - target)
            
            totalError += error
            predictions++
            errors.add(error)
        }
        
        return TrialResult(
            error = totalError,
            predictions = predictions,
            errors = errors
        )
    }
    
    /**
     * Create a simple test network for debugging
     */
    fun createTestNetwork(): NeuralNetwork {
        val neurons = listOf(
            gen.ai.Neuron(
                id = 1L,
                type = gen.ai.NeuronType.Input,
                activation = gen.ai.ActivationFunction.Sigmoidal,
                bias = 0.0,
                threshold = 0.0,
                innovationId = 1L
            ),
            gen.ai.Neuron(
                id = 2L,
                type = gen.ai.NeuronType.Output,
                activation = gen.ai.ActivationFunction.Tanh(gen.ai.ActivationFunction.Variable("sum")),
                bias = 0.1,
                threshold = 0.5,
                innovationId = 2L
            ),
            gen.ai.Neuron(
                id = 3L,
                type = gen.ai.NeuronType.Hidden,
                activation = gen.ai.ActivationFunction.Sigmoidal,
                bias = -0.1,
                threshold = 0.3,
                innovationId = 3L
            )
        )
        
        val connections = listOf(
            gen.ai.Connection(
                sourceId = 1L,
                targetId = 3L,
                weight = 0.5,
                learningRate = 0.01,
                delay = 0,
                plasticityType = gen.ai.PlasticityType.Hebbian,
                innovationId = 4L
            ),
            gen.ai.Connection(
                sourceId = 3L,
                targetId = 2L,
                weight = 0.8,
                learningRate = 0.01,
                delay = 0,
                plasticityType = gen.ai.PlasticityType.Hebbian,
                innovationId = 5L
            ),
            gen.ai.Connection(
                sourceId = 3L,
                targetId = 3L,
                weight = 0.3,
                learningRate = 0.005,
                delay = 1,
                plasticityType = gen.ai.PlasticityType.Hebbian,
                innovationId = 6L
            )
        )
        
        val genome = gen.ai.Genome(
            neurons = neurons,
            connections = connections,
            resourceBudgets = gen.ai.ResourceBudgets(
                maxNeurons = 10,
                maxConnections = 20,
                maxDelay = 5,
                maxActivationFunctionDepth = 7
            ),
            generation = 0,
            innovationHistory = listOf(1L,2L,3L,4L,5L,6L)
        )
        
        return NeuralNetwork(genome)
    }
}

enum class SequenceType {
    SINE_WAVE,
    REPEATING_PATTERN,
    FIBONACCI_LIKE,
    RANDOM_WALK
}

data class TaskResult(
    val fitness: Double,
    val averageError: Double,
    val totalPredictions: Int,
    val trials: List<TrialResult>
)

data class TrialResult(
    val error: Double,
    val predictions: Int,
    val errors: List<Double>
) 