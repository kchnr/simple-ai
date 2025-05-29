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

import gen.ai.evolution.SimpleEvolution
import gen.ai.network.NeuralNetwork
import gen.ai.tasks.SequencePredictionTask

fun runEvolutionDemo() {
    println("🧠 Neural Evolution Demo - Discovering Minimal Brains")
    println("=" * 60)
    
    // Setup
    val task = SequencePredictionTask(sequenceLength = 20)
    val evolution = SimpleEvolution(populationSize = 30, seed = 42)
    
    // Create initial population
    println("Creating initial population...")
    var population = evolution.createInitialPopulation()
    
    // Test a simple network first
    println("\n🔬 Testing basic network functionality...")
    val testNetwork = task.createTestNetwork()
    val testResult = task.evaluate(testNetwork, numTrials = 3)
    println("Test network fitness: ${String.format("%.4f", testResult.fitness)}")
    println("Test network error: ${String.format("%.4f", testResult.averageError)}")
    
    // Evolution loop
    println("\n🧬 Starting evolution...")
    for (generation in 0 until 20) {
        val result = evolution.evolveGeneration(population, task)
        population = result.newPopulation
        
        // Print progress
        println("\nGeneration ${result.stats.generation}:")
        println("  Best fitness:    ${String.format("%.4f", result.stats.bestFitness)}")
        println("  Average fitness: ${String.format("%.4f", result.stats.averageFitness)}")
        println("  Worst fitness:   ${String.format("%.4f", result.stats.worstFitness)}")
        println("  Avg neurons:     ${String.format("%.1f", result.stats.averageNeurons)}")
        println("  Avg connections: ${String.format("%.1f", result.stats.averageConnections)}")
        
        // Analyze best genome
        if (generation % 5 == 0) {
            analyzeBestGenome(result.bestGenome, task)
        }
        
        // Early stopping if we find a good solution
        if (result.stats.bestFitness > 0.95) {
            println("\n🎉 Found good solution! Stopping early.")
            break
        }
    }
    
    println("\n✅ Evolution complete!")
}

fun analyzeBestGenome(genome: Genome, task: SequencePredictionTask) {
    println("\n🔍 Analyzing best genome:")
    
    // Basic stats
    println("  Neurons: ${genome.neurons.size}")
    println("  Connections: ${genome.connections.size}")
    println("  Generation: ${genome.generation}")
    
    // Neuron type distribution
    val neuronTypes = genome.neurons.groupBy { it.type }.mapValues { it.value.size }
    println("  Neuron types: $neuronTypes")
    
    // Plasticity info
    val plasticConnections = genome.connections.count { it.plasticityType != PlasticityType.None }
    println("  Plastic connections: $plasticConnections/${genome.connections.size}")
    
    // Test on different sequence types
    val network = NeuralNetwork(genome)
    println("  Performance on different sequences:")
    
    gen.ai.tasks.SequenceType.values().forEach { seqType ->
        val sequence = task.generateSequence(seqType)
        val result = task.evaluate(network, numTrials = 1)
        println("    $seqType: ${String.format("%.4f", result.fitness)}")
    }
    
    // Show network activity
    showNetworkActivity(network, task)
}

fun showNetworkActivity(network: NeuralNetwork, task: SequencePredictionTask) {
    println("  Network activity sample:")
    
    val sequence = task.generateSequence(gen.ai.tasks.SequenceType.SINE_WAVE)
    network.reset()
    
    println("    Input -> Output (Target)")
    sequence.take(5).forEachIndexed { i, input ->
        val output = network.step(mapOf(1L to input))
        val prediction = output[2L] ?: 0.0
        val target = if (i < sequence.size - 1) sequence[i + 1] else 0.0
        
        println("    ${String.format("%.3f", input)} -> ${String.format("%.3f", prediction)} (${String.format("%.3f", target)})")
    }
    
    // Show network stats
    val stats = network.getStats()
    println("  Network stats: $stats")
}

// Extension function for string repetition
operator fun String.times(n: Int): String = this.repeat(n) 