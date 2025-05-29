package gen.ai

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class GenomeTest : StringSpec({

    // Helper to create a pretty printing Json instance for easier debugging if tests fail
    val json = Json { prettyPrint = true }

    "Genome should serialize and deserialize correctly" {
        // Use the object version of Sigmoidal for a simple, parameterless activation function
        val sampleActivation = ActivationFunction.Sigmoidal

        val originalGenome = Genome(
            neurons = listOf(
                Neuron(
                    id = 1L,
                    type = NeuronType.Input,
                    activation = sampleActivation,
                    bias = 0.1,
                    threshold = 0.5,
                    innovationId = 101L
                ),
                Neuron(
                    id = 2L,
                    type = NeuronType.Hidden,
                    activation = sampleActivation,
                    bias = 0.2,
                    threshold = 0.6,
                    innovationId = 102L
                ),
                Neuron(
                    id = 3L,
                    type = NeuronType.Output,
                    activation = sampleActivation,
                    bias = 0.3,
                    threshold = 0.7,
                    innovationId = 103L
                )
            ),
            connections = listOf(
                Connection(
                    sourceId = 1L,
                    targetId = 2L,
                    weight = 0.5,
                    learningRate = 0.01,
                    delay = 1,
                    plasticityType = PlasticityType.Hebbian,
                    innovationId = 201L
                ),
                Connection(
                    sourceId = 2L,
                    targetId = 3L,
                    weight = 0.6,
                    learningRate = 0.02,
                    delay = 0,
                    plasticityType = PlasticityType.None,
                    innovationId = 202L
                )
            ),
            resourceBudgets = ResourceBudgets(
                maxNeurons = 10,
                maxConnections = 20,
                maxDelay = 5,
                maxActivationFunctionDepth = 7
            ),
            generation = 1,
            innovationHistory = listOf(101L, 102L, 103L, 201L, 202L)
        )

        val jsonString = json.encodeToString(originalGenome)
        println("Serialized Genome:\n$jsonString") // For manual inspection during test run

        val deserializedGenome = Json.decodeFromString<Genome>(jsonString)

        deserializedGenome shouldBe originalGenome
    }
}) 