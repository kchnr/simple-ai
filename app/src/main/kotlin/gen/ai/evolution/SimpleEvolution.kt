/*
 * SimpleEvolution.kt
 *
 * Purpose:
 *   Basic evolutionary algorithm for evolving neural networks to solve cognitive tasks.
 *   Implements selection, mutation, crossover, and population management to discover
 *   minimal, biologically plausible neural networks through evolutionary optimization.
 *
 * Project Context:
 *   This is the evolutionary engine that drives the discovery of minimal neural networks.
 *   It operates on genome populations, evaluates fitness on cognitive tasks, and uses
 *   genetic operators to evolve better solutions over generations. The goal is to find
 *   the smallest possible "brains" that can solve problems while maintaining biological
 *   plausibility through diverse neuron types, plasticity, and neuromodulation.
 *
 * Evolutionary Algorithm:
 *   1. Initialize random population of genomes
 *   2. Evaluate fitness on cognitive tasks (sequence prediction, etc.)
 *   3. Select parents using tournament selection
 *   4. Create offspring through crossover and mutation
 *   5. Maintain elite individuals across generations
 *   6. Repeat until convergence or maximum generations
 *
 * Genetic Operators:
 *   - Mutation: Modify neuron parameters, connection weights, add/remove components
 *   - Crossover: Combine neurons and connections from two parent genomes
 *   - Selection: Tournament selection favoring higher fitness individuals
 *   - Elitism: Preserve best individuals to prevent fitness regression
 *
 * Population Management:
 *   - Fixed population size with generational replacement
 *   - Innovation tracking for crossover compatibility
 *   - Resource budget constraints to prevent bloat
 *   - Diversity maintenance through varied neuron types
 *
 * Fitness Evaluation:
 *   - Networks are built from genomes and tested on tasks
 *   - Multiple trials ensure robust performance measurement
 *   - Fitness combines task performance with resource efficiency
 *   - Early stopping when good solutions are found
 *
 * Biological Inspiration:
 *   - Structural mutations can add new neuron types and connections
 *   - Plasticity parameters evolve alongside network structure
 *   - Neuromodulator systems can emerge through evolution
 *   - Resource constraints mimic biological energy limitations
 */

package gen.ai.evolution

import gen.ai.*
import gen.ai.network.NeuralNetwork
import gen.ai.tasks.SequencePredictionTask
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

class SimpleEvolution(
    val populationSize: Int = 50,
    val mutationRate: Double = 0.1,
    val crossoverRate: Double = 0.7,
    val eliteSize: Int = 5,
    val seed: Long = 42
) {
    
    private val random = Random(seed)
    private var innovationCounter = 1000L
    private var generation = 0
    
    /**
     * Create initial population with minimal networks
     */
    fun createInitialPopulation(): List<Genome> {
        return (0 until populationSize).map { createRandomGenome() }
    }
    
    private fun createRandomGenome(): Genome {
        val neurons = mutableListOf<Neuron>()
        val connections = mutableListOf<Connection>()
        
        // Always start with input and output neurons
        neurons.add(
            Neuron(
                id = 1L,
                type = NeuronType.Input,
                activation = ActivationFunction.Sigmoidal,
                bias = 0.0,
                threshold = 0.0,
                innovationId = getNextInnovationId()
            )
        )
        
        neurons.add(
            Neuron(
                id = 2L,
                type = NeuronType.Output,
                activation = randomActivationFunction(),
                bias = random.nextGaussian() * 0.5,
                threshold = random.nextDouble(-1.0, 1.0),
                innovationId = getNextInnovationId()
            )
        )
        
        // Add 0-2 hidden neurons for MVP
        val hiddenCount = random.nextInt(0, 3)
        repeat(hiddenCount) { i ->
            neurons.add(
                Neuron(
                    id = 3L + i,
                    type = NeuronType.Hidden,
                    activation = randomActivationFunction(),
                    bias = random.nextGaussian() * 0.5,
                    threshold = random.nextDouble(-1.0, 1.0),
                    innovationId = getNextInnovationId()
                )
            )
        }
        
        // Add random connections (ensure some connections if hidden neurons exist or direct I/O)
        val minConnections = if (hiddenCount > 0) 1 else 0
        val maxPossibleConnections = neurons.size * (neurons.size -1) / 2 + neurons.size
        val connectionCount = random.nextInt(minConnections, max(minConnections + 1, min(neurons.size * 2, maxPossibleConnections + 1)))
        
        val existingConnections = mutableSetOf<Pair<Long, Long>>()

        repeat(connectionCount) {
            val source = neurons.random(random)
            // Ensure target is not an Input neuron
            val possibleTargets = neurons.filter { it.type != NeuronType.Input }
            if (possibleTargets.isEmpty()) return@repeat

            val target = possibleTargets.random(random)

            if (source.id != target.id || random.nextDouble() < 0.2) { // Allow some self-connections for non-input neurons
                if (source.type == NeuronType.Input && target.type == NeuronType.Input) return@repeat

                if (existingConnections.add(source.id to target.id)) {
                    connections.add(
                        Connection(
                            sourceId = source.id,
                            targetId = target.id,
                            weight = random.nextGaussian() * 0.5,
                            learningRate = random.nextDouble(0.001, 0.1),
                            delay = random.nextInt(0, 3),
                            plasticityType = randomPlasticityType(),
                            innovationId = getNextInnovationId()
                        )
                    )
                }
            }
        }
        
        return Genome(
            neurons = neurons,
            connections = connections,
            resourceBudgets = ResourceBudgets(
                maxNeurons = 20,
                maxConnections = 50,
                maxDelay = 5,
                maxActivationFunctionDepth = 7
            ),
            generation = generation
        )
    }
    
    private fun randomActivationFunction(): ActivationFunction {
        return when (random.nextInt(6)) {
            0 -> ActivationFunction.Variable("sum")
            1 -> ActivationFunction.Tanh(ActivationFunction.Variable("sum"))
            2 -> ActivationFunction.ReLU(ActivationFunction.Variable("sum"))
            3 -> ActivationFunction.Add(
                ActivationFunction.Variable("sum"),
                ActivationFunction.Const(random.nextGaussian() * 0.1)
            )
            4 -> ActivationFunction.Mul(
                ActivationFunction.Variable("sum"),
                ActivationFunction.Const(random.nextDouble(0.5, 2.0))
            )
            else -> ActivationFunction.Sin(ActivationFunction.Variable("sum"))
        }
    }
    
    private fun randomPlasticityType(): PlasticityType {
        return listOf(
            PlasticityType.None,
            PlasticityType.Hebbian
        ).random(random)
    }
    
    /**
     * Evolve population for one generation
     */
    fun evolveGeneration(population: List<Genome>, task: SequencePredictionTask): EvolutionResult {
        generation++
        
        // Evaluate fitness
        val evaluatedPopulation = population.map { genome ->
            val network = NeuralNetwork(genome)
            val result = task.evaluate(network, numTrials = 3)
            EvaluatedGenome(genome, result.fitness, result)
        }.sortedByDescending { it.fitness }
        
        // Selection and reproduction
        val newPopulation = mutableListOf<Genome>()
        
        // Elite selection
        newPopulation.addAll(evaluatedPopulation.take(eliteSize).map { it.genome })
        
        // Generate offspring
        while (newPopulation.size < populationSize) {
            if (random.nextDouble() < crossoverRate && newPopulation.size < populationSize - 1) {
                // Crossover
                val parent1 = tournamentSelection(evaluatedPopulation)
                val parent2 = tournamentSelection(evaluatedPopulation)
                val offspring = crossover(parent1.genome, parent2.genome)
                newPopulation.addAll(offspring.map { mutate(it) })
            } else {
                // Mutation only
                val parent = tournamentSelection(evaluatedPopulation)
                newPopulation.add(mutate(parent.genome))
            }
        }
        
        val stats = GenerationStats(
            generation = generation,
            bestFitness = evaluatedPopulation.first().fitness,
            averageFitness = evaluatedPopulation.map { it.fitness }.average(),
            worstFitness = evaluatedPopulation.last().fitness,
            averageNeurons = population.map { it.neurons.size }.average(),
            averageConnections = population.map { it.connections.size }.average()
        )
        
        return EvolutionResult(
            newPopulation = newPopulation.take(populationSize),
            stats = stats,
            bestGenome = evaluatedPopulation.first().genome,
            evaluatedPopulation = evaluatedPopulation
        )
    }
    
    private fun tournamentSelection(population: List<EvaluatedGenome>, tournamentSize: Int = 3): EvaluatedGenome {
        return (0 until tournamentSize).map { population.random(random) }.maxByOrNull { it.fitness }!!
    }
    
    private fun crossover(parent1: Genome, parent2: Genome): List<Genome> {
        // Simple crossover: combine neurons and connections from both parents
        val allNeurons = (parent1.neurons + parent2.neurons).distinctBy { it.id }
        val allConnections = (parent1.connections + parent2.connections).distinctBy { it.sourceId to it.targetId }
        
        // Create two offspring with different combinations
        val offspring1 = parent1.copy(
            neurons = allNeurons.take((allNeurons.size * 0.7).toInt()),
            connections = allConnections.filter { conn ->
                allNeurons.any { it.id == conn.sourceId } && allNeurons.any { it.id == conn.targetId }
            },
            generation = generation
        )
        
        val offspring2 = parent2.copy(
            neurons = allNeurons.drop((allNeurons.size * 0.3).toInt()),
            connections = allConnections.filter { conn ->
                allNeurons.any { it.id == conn.sourceId } && allNeurons.any { it.id == conn.targetId }
            },
            generation = generation
        )
        
        return listOf(offspring1, offspring2)
    }
    
    internal fun mutate(genome: Genome): Genome {
        val mutatedNeurons = genome.neurons.map { neuron ->
            if (random.nextDouble() < mutationRate) {
                neuron.copy(
                    bias = neuron.bias + random.nextGaussian() * 0.1,
                    threshold = neuron.threshold + random.nextGaussian() * 0.1
                )
            } else neuron
        }.toMutableList()
        
        val mutatedConnections = genome.connections.map { connection ->
            var tempConnection = connection
            if (random.nextDouble() < mutationRate) { // Mutate weight
                tempConnection = tempConnection.copy(
                    weight = tempConnection.weight + random.nextGaussian() * 0.1
                )
            }
            if (random.nextDouble() < mutationRate) { // Mutate learningRate
                tempConnection = tempConnection.copy(
                    learningRate = (tempConnection.learningRate + random.nextGaussian() * 0.005).coerceIn(0.0001, 0.1)
                )
            }
            if (random.nextDouble() < mutationRate) { // Mutate delay
                val newDelay = tempConnection.delay + random.nextInt(-1, 2) // -1, 0, or 1
                tempConnection = tempConnection.copy(
                    delay = newDelay.coerceIn(0, genome.resourceBudgets.maxDelay)
                )
            }
            if (random.nextDouble() < mutationRate) { // Mutate plasticityType
                tempConnection = tempConnection.copy(
                    plasticityType = if (tempConnection.plasticityType == PlasticityType.None) PlasticityType.Hebbian else PlasticityType.None
                )
            }
            tempConnection
        }.toMutableList()
        
        // Structural mutations
        if (random.nextDouble() < mutationRate * 0.5) {
            // Add neuron
            if (mutatedNeurons.size < genome.resourceBudgets.maxNeurons) {
                val newNeuron = Neuron(
                    id = mutatedNeurons.maxOfOrNull { it.id }?.plus(1) ?: 1000L,
                    type = NeuronType.Hidden,
                    activation = randomActivationFunction(),
                    bias = random.nextGaussian() * 0.5,
                    threshold = random.nextDouble(-1.0, 1.0),
                    innovationId = getNextInnovationId()
                )
                mutatedNeurons.add(newNeuron)
            }
        }
        
        if (random.nextDouble() < mutationRate * 0.5) {
            // Add connection
            if (mutatedConnections.size < genome.resourceBudgets.maxConnections) {
                val source = mutatedNeurons.random(random)
                val target = mutatedNeurons.filter { it.type != NeuronType.Input }.random(random)
                
                val newConnection = Connection(
                    sourceId = source.id,
                    targetId = target.id,
                    weight = random.nextGaussian() * 0.5,
                    learningRate = random.nextDouble(0.001, 0.1),
                    delay = random.nextInt(0, 3),
                    plasticityType = randomPlasticityType(),
                    innovationId = getNextInnovationId()
                )
                
                if (mutatedConnections.none { it.sourceId == newConnection.sourceId && it.targetId == newConnection.targetId }) {
                    mutatedConnections.add(newConnection)
                }
            }
        }
        
        return genome.copy(
            neurons = mutatedNeurons,
            connections = mutatedConnections,
            generation = generation
        )
    }
    
    private fun getNextInnovationId(): Long = innovationCounter++
}

data class EvaluatedGenome(
    val genome: Genome,
    val fitness: Double,
    val taskResult: gen.ai.tasks.TaskResult
)

data class GenerationStats(
    val generation: Int,
    val bestFitness: Double,
    val averageFitness: Double,
    val worstFitness: Double,
    val averageNeurons: Double,
    val averageConnections: Double
)

data class EvolutionResult(
    val newPopulation: List<Genome>,
    val stats: GenerationStats,
    val bestGenome: Genome,
    val evaluatedPopulation: List<EvaluatedGenome>
) 