/*
 * EvolutionDemo.kt
 *
 * Purpose:
 *   Demonstration of the neural evolution system in action.
 *   Shows how networks evolve to solve temporal sequence prediction.
 *
 * Project Context:
 *   - Minimum viable demo to see evolution working
 *   - Tests the complete pipeline: genome → network → task → fitness → evolution
 *   - Provides insights into what kinds of brains are evolving
 */

package gen.ai

import org.slf4j.LoggerFactory
import gen.ai.network.NeuralNetwork
import gen.ai.tasks.SequencePredictionTask
import gen.ai.evolution.PipelineConfig
import pipeline.Pipeline

private val logger = LoggerFactory.getLogger("EvolutionDemo")

fun runEvolutionDemo() {
    logger.info("🧠 Neural Evolution Demo via EvolutionPipeline")
    logger.info("{}", "=".repeat(60))
    // Configure and run the pipeline
    val config = PipelineConfig(populationSize = 30, generationCount = 20, seed = 42)
    val pipeline = Pipeline(config)
    val finalPopulation = pipeline.run()
    logger.info("✅ Evolution pipeline complete. Final population size: {}", finalPopulation.size)
}

fun analyzeBestGenome(genome: Genome, task: SequencePredictionTask) {
    logger.info("\n🔍 Analyzing best genome:")
    
    // Basic stats
    logger.info("  Neurons: {}", genome.neurons.size)
    logger.info("  Connections: {}", genome.connections.size)
    logger.info("  Generation: {}", genome.generation)
    
    // Neuron type distribution
    val neuronTypes = genome.neurons.groupBy { it.type }.mapValues { it.value.size }
    logger.info("  Neuron types: {}", neuronTypes)
    
    // Plasticity info
    val plasticConnections = genome.connections.count { it.plasticityType != PlasticityType.None }
    logger.info("  Plastic connections: {} / {}", plasticConnections, genome.connections.size)
    
    // Test on different sequence types
    val network = NeuralNetwork(genome)
    logger.info("  Performance on different sequences:")
    
    gen.ai.tasks.SequenceType.values().forEach { seqType ->
        val sequence = task.generateSequence(seqType)
        val result = task.evaluate(network, numTrials = 1)
        logger.info("    {} : {}", seqType, String.format("%.4f", result.fitness))
    }
    
    // Show network activity
    showNetworkActivity(network, task)
}

fun showNetworkActivity(network: NeuralNetwork, task: SequencePredictionTask) {
    logger.info("  Network activity sample:")
    
    val sequence = task.generateSequence(gen.ai.tasks.SequenceType.SINE_WAVE)
    network.reset()
    
    logger.info("    Input -> Output (Target)")
    sequence.take(5).forEachIndexed { i, input ->
        val output = network.step(mapOf(1L to input))
        val prediction = output[2L] ?: 0.0
        val target = if (i < sequence.size - 1) sequence[i + 1] else 0.0
        
        logger.info("    {} -> {} ({})", String.format("%.3f", input), String.format("%.3f", prediction), String.format("%.3f", target))
    }
    
    // Show network stats
    val stats = network.getStats()
    logger.info("  Network stats: {}", stats)
}

// Extension function for string repetition
operator fun String.times(n: Int): String = this.repeat(n) 