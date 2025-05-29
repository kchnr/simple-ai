package gen.ai.evolution

import gen.ai.Genome
import gen.ai.Neuron
import gen.ai.Connection
import gen.ai.NeuronType
import gen.ai.PlasticityType
import gen.ai.ActivationFunction
import gen.ai.ResourceBudgets
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forAll
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldNotBe

class SimpleEvolutionTest : StringSpec({
    val evolution = SimpleEvolution(populationSize = 10, seed = 12345L)

    "createInitialPopulation should generate valid genomes for MVP" {
        val population = evolution.createInitialPopulation()

        population shouldHaveSize 10

        population.forAll { genome: Genome ->
            genome.neurons.isEmpty() shouldBe false
            genome.connections.isEmpty() shouldBe false

            // Check neuron types - only Input, Output, Hidden for MVP
            val allowedNeuronTypes = listOf(NeuronType.Input, NeuronType.Output, NeuronType.Hidden)
            genome.neurons.forAll { neuron: Neuron ->
                neuron.type shouldBeIn allowedNeuronTypes
                // Ensure input neurons are of type Input, output neurons are of type Output
                if (neuron.id == 1L) neuron.type shouldBe NeuronType.Input
                if (neuron.id == 2L) neuron.type shouldBe NeuronType.Output
            }

            // Check plasticity types - only None or Hebbian for MVP
            val allowedPlasticityTypes = listOf(PlasticityType.None, PlasticityType.Hebbian)
            genome.connections.forAll { connection: Connection ->
                connection.plasticityType shouldBeIn allowedPlasticityTypes
            }

            // Check resource budgets are initialized (values can be more specific if needed)
            genome.resourceBudgets.maxNeurons shouldBeGreaterThanOrEqual 1
            genome.resourceBudgets.maxConnections shouldBeGreaterThanOrEqual 1
            genome.resourceBudgets.maxDelay shouldBeGreaterThanOrEqual 0
            genome.resourceBudgets.maxActivationFunctionDepth shouldBeGreaterThanOrEqual 1

            // Basic structural integrity: connections should point to valid neuron IDs
            val neuronIds = genome.neurons.map { it.id }.toSet()
            genome.connections.forAll { connection: Connection ->
                neuronIds.contains(connection.sourceId) shouldBe true
                neuronIds.contains(connection.targetId) shouldBe true
            }
        }
    }

    "mutate should change neuron bias or threshold" {
        val originalGenome = evolution.createInitialPopulation().first().let { genome ->
            // Ensure there is at least one hidden or output neuron to mutate, besides the fixed input neuron
            if (genome.neurons.count { it.type != NeuronType.Input } == 0) {
                // If by chance the random genome is too minimal, create a slightly more complex one
                // This is a simplified way to ensure mutability for the test.
                // A more robust approach might be to construct a specific genome for this test.
                val neurons = mutableListOf(
                    Neuron(id = 1L, type = NeuronType.Input, activation = ActivationFunction.Sigmoidal, bias = 0.0, threshold = 0.0, innovationId = 1L),
                    Neuron(id = 2L, type = NeuronType.Output, activation = ActivationFunction.Sigmoidal, bias = 0.1, threshold = 0.5, innovationId = 2L)
                )
                val connections = mutableListOf(
                    Connection(sourceId = 1L, targetId = 2L, weight = 0.5, learningRate = 0.01, delay = 0, plasticityType = PlasticityType.None, innovationId = 3L)
                )
                Genome(neurons, connections, ResourceBudgets(3,3,3,3), generation = 0, innovationHistory = listOf(1L,2L,3L))
            } else {
                genome
            }
        }
        
        // Ensure there are non-input neurons to mutate their bias/threshold
        val nonInputNeuronsOriginal = originalGenome.neurons.filter { it.type != NeuronType.Input }
        nonInputNeuronsOriginal.isNotEmpty() shouldBe true // Make sure we have something to test

        // Get original bias and threshold from the first non-input neuron for simplicity
        val targetNeuronOriginal = nonInputNeuronsOriginal.first()
        val originalBias = targetNeuronOriginal.bias
        val originalThreshold = targetNeuronOriginal.threshold

        // Mutate the genome multiple times to increase chance of this specific mutation occurring
        var mutatedGenome = originalGenome
        var biasChanged = false
        var thresholdChanged = false
        val mutationAttempts = 100 // High number of attempts due to probabilistic nature

        for (i in 0 until mutationAttempts) {
            mutatedGenome = evolution.mutate(mutatedGenome)
            val mutatedTargetNeuron = mutatedGenome.neurons.find { it.id == targetNeuronOriginal.id }
            mutatedTargetNeuron.shouldNotBeNull()

            if (mutatedTargetNeuron.bias != originalBias) {
                biasChanged = true
            }
            if (mutatedTargetNeuron.threshold != originalThreshold) {
                thresholdChanged = true
            }
            if (biasChanged && thresholdChanged) break // Stop if both have changed
        }
        
        // Assert that at least one of them changed. 
        // Due to randomness, it's hard to guarantee both change or a specific one changes easily.
        (biasChanged || thresholdChanged) shouldBe true
    }

    "mutate should change connection weight, learningRate, or delay" {
        val originalGenome = evolution.createInitialPopulation().first().let { genome ->
            // Ensure there are connections to mutate
            if (genome.connections.isEmpty()) {
                val neurons = mutableListOf(
                    Neuron(id = 1L, type = NeuronType.Input, activation = ActivationFunction.Sigmoidal, bias = 0.0, threshold = 0.0, innovationId = 1L),
                    Neuron(id = 2L, type = NeuronType.Output, activation = ActivationFunction.Sigmoidal, bias = 0.1, threshold = 0.5, innovationId = 2L)
                )
                val connections = mutableListOf(
                    Connection(sourceId = 1L, targetId = 2L, weight = 0.5, learningRate = 0.01, delay = 1, plasticityType = PlasticityType.None, innovationId = 3L)
                )
                Genome(neurons, connections, ResourceBudgets(2,1,5,5), generation = 0, innovationHistory = listOf(1L,2L,3L))
            } else {
                genome
            }
        }

        originalGenome.connections.isEmpty() shouldBe false // Make sure we have a connection

        val targetConnectionOriginal = originalGenome.connections.first()
        val originalWeight = targetConnectionOriginal.weight
        val originalLearningRate = targetConnectionOriginal.learningRate
        val originalDelay = targetConnectionOriginal.delay

        var mutatedGenome = originalGenome
        var weightChanged = false
        var learningRateChanged = false
        var delayChanged = false
        val mutationAttempts = 100 // Increased attempts if individual param mutations are rare

        for (i in 0 until mutationAttempts) {
            mutatedGenome = evolution.mutate(mutatedGenome)
            val mutatedTargetConnection = mutatedGenome.connections.find { it.innovationId == targetConnectionOriginal.innovationId }
            mutatedTargetConnection.shouldNotBeNull()

            if (mutatedTargetConnection.weight != originalWeight) {
                weightChanged = true
            }
            if (mutatedTargetConnection.learningRate != originalLearningRate) {
                learningRateChanged = true
            }
            if (mutatedTargetConnection.delay != originalDelay) {
                delayChanged = true
            }
            if (weightChanged && learningRateChanged && delayChanged) break
        }

        (weightChanged || learningRateChanged || delayChanged) shouldBe true
    }

    "mutate should change connection plasticityType" {
        val initialGenomeWithConnection = evolution.createInitialPopulation().first().let { genome ->
            if (genome.connections.isEmpty()) {
                val neurons = mutableListOf(
                    Neuron(id = 1L, type = NeuronType.Input, activation = ActivationFunction.Sigmoidal, bias = 0.0, threshold = 0.0, innovationId = 1L),
                    Neuron(id = 2L, type = NeuronType.Output, activation = ActivationFunction.Sigmoidal, bias = 0.1, threshold = 0.5, innovationId = 2L)
                )
                val connections = mutableListOf(
                    // Start with a known plasticity type, e.g., None
                    Connection(sourceId = 1L, targetId = 2L, weight = 0.5, learningRate = 0.01, delay = 1, plasticityType = PlasticityType.None, innovationId = 3L)
                )
                Genome(neurons, connections, ResourceBudgets(2,1,5,5), generation = 0, innovationHistory = listOf(1L,2L,3L))
            } else {
                // If connections exist, ensure at least one has a type we can flip from (e.g. ensure one is None or make it so)
                // For simplicity, if the first connection is already Hebbian, we'll make it None for the test to ensure a change can be detected.
                if (genome.connections.first().plasticityType == PlasticityType.Hebbian) {
                    val modifiedConnections = genome.connections.toMutableList()
                    modifiedConnections[0] = modifiedConnections[0].copy(plasticityType = PlasticityType.None)
                    genome.copy(connections = modifiedConnections)
                } else {
                    genome
                }
            }
        }

        initialGenomeWithConnection.connections.isEmpty() shouldBe false
        val targetConnectionOriginal = initialGenomeWithConnection.connections.first()
        val originalPlasticityType = targetConnectionOriginal.plasticityType

        // Define the expected flipped type
        val expectedFlippedType = if (originalPlasticityType == PlasticityType.None) PlasticityType.Hebbian else PlasticityType.None

        var mutatedGenome = initialGenomeWithConnection
        var plasticityTypeChanged = false
        val mutationAttempts = 100 // Give it enough chances

        for (i in 0 until mutationAttempts) {
            mutatedGenome = evolution.mutate(mutatedGenome)
            val mutatedTargetConnection = mutatedGenome.connections.find { it.innovationId == targetConnectionOriginal.innovationId }
            mutatedTargetConnection.shouldNotBeNull()

            if (mutatedTargetConnection.plasticityType == expectedFlippedType) {
                plasticityTypeChanged = true
                break // Found the change
            }
        }
        plasticityTypeChanged shouldBe true
    }

    "mutate should add a valid new connection" {
        // Start with a genome that has room for more connections and valid neuron pairs
        val initialGenome = Genome(
            neurons = listOf(
                Neuron(id = 1L, type = NeuronType.Input, activation = ActivationFunction.Sigmoidal, innovationId = 1L),
                Neuron(id = 2L, type = NeuronType.Hidden, activation = ActivationFunction.Sigmoidal, innovationId = 2L),
                Neuron(id = 3L, type = NeuronType.Output, activation = ActivationFunction.Sigmoidal, innovationId = 3L)
            ),
            connections = mutableListOf(), // Start with no connections or few connections
            resourceBudgets = ResourceBudgets(
                maxNeurons = 5,
                maxConnections = 5, // Allow a few connections
                maxDelay = 3,
                maxActivationFunctionDepth = 5
            ),
            generation = 0
        )

        val originalConnectionCount = initialGenome.connections.size
        val neuronIds = initialGenome.neurons.map { it.id }.toSet()

        var mutatedGenome = initialGenome
        var connectionAdded = false
        val mutationAttempts = 200 // Higher attempts as structural mutations might be rarer or fail preconditions

        for (i in 0 until mutationAttempts) {
            mutatedGenome = evolution.mutate(mutatedGenome)
            if (mutatedGenome.connections.size > originalConnectionCount) {
                connectionAdded = true
                break
            }
        }

        connectionAdded shouldBe true
        mutatedGenome.connections.size shouldBe (originalConnectionCount + 1)

        // Validate the newly added connection (assuming it's the last one for simplicity if only one was added)
        val newConnection = mutatedGenome.connections.last()
        neuronIds.contains(newConnection.sourceId) shouldBe true
        neuronIds.contains(newConnection.targetId) shouldBe true
        
        val targetNeuron = mutatedGenome.neurons.find { it.id == newConnection.targetId }
        targetNeuron.shouldNotBeNull()
        targetNeuron.type shouldNotBe NeuronType.Input // Target should not be an input neuron

        // Check for self-connection to Input neuron (should not happen ideally, unless specifically allowed for others)
        if (newConnection.sourceId == newConnection.targetId) {
             val sourceNeuron = mutatedGenome.neurons.find { it.id == newConnection.sourceId }
             sourceNeuron.shouldNotBeNull()
             sourceNeuron.type shouldNotBe NeuronType.Input // Self-connections on input neurons are usually disallowed
        }

        (mutatedGenome.connections.size <= mutatedGenome.resourceBudgets.maxConnections) shouldBe true
    }

    "mutate should add a new hidden neuron" {
        val initialGenome = Genome(
            neurons = mutableListOf(
                Neuron(id = 1L, type = NeuronType.Input, activation = ActivationFunction.Sigmoidal, innovationId = 1L),
                Neuron(id = 2L, type = NeuronType.Output, activation = ActivationFunction.Sigmoidal, innovationId = 2L)
            ),
            connections = mutableListOf(
                Connection(sourceId = 1L, targetId = 2L, weight = 0.5, innovationId = 3L) // A connection is present
            ),
            resourceBudgets = ResourceBudgets(
                maxNeurons = 5, // Allow adding neurons
                maxConnections = 5,
                maxDelay = 3,
                maxActivationFunctionDepth = 5
            ),
            generation = 0
        )

        val originalNeuronCount = initialGenome.neurons.size

        var mutatedGenome = initialGenome
        var neuronAdded = false
        val mutationAttempts = 200 // Higher attempts for structural mutations

        for (i in 0 until mutationAttempts) {
            mutatedGenome = evolution.mutate(mutatedGenome)
            if (mutatedGenome.neurons.size > originalNeuronCount) {
                neuronAdded = true
                break
            }
        }

        neuronAdded shouldBe true
        mutatedGenome.neurons.size shouldBe (originalNeuronCount + 1)

        val newNeuron = mutatedGenome.neurons.last() // Assuming new neuron is added at the end
        newNeuron.type shouldBe NeuronType.Hidden
        (mutatedGenome.neurons.size <= mutatedGenome.resourceBudgets.maxNeurons) shouldBe true
        // Note: This test does not verify connection splitting, only addition of an isolated neuron as per current code.
    }
}) 