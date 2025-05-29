package gen.ai.network

import gen.ai.Connection
import gen.ai.Genome
import gen.ai.Neuron
import gen.ai.NeuronType
import gen.ai.PlasticityType
import gen.ai.ActivationFunction
import gen.ai.ResourceBudgets
import kotlin.math.exp
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.doubles.shouldBeExactly
import io.kotest.matchers.comparables.shouldBeGreaterThan

class NeuralNetworkTopologyTest : StringSpec({

    "feed-forward chain input->hidden->output produces expected output" {
        val inputNeuron = Neuron(
            id = 1L,
            type = NeuronType.Input,
            activation = ActivationFunction.Sigmoidal,
            bias = 0.0,
            threshold = 0.0,
            innovationId = 1L
        )
        val hiddenNeuron = Neuron(
            id = 2L,
            type = NeuronType.Hidden,
            activation = ActivationFunction.Sigmoidal,
            bias = 0.0,
            threshold = 0.0,
            innovationId = 2L
        )
        val outputNeuron = Neuron(
            id = 3L,
            type = NeuronType.Output,
            activation = ActivationFunction.Sigmoidal,
            bias = 0.0,
            threshold = 0.0,
            innovationId = 3L
        )
        val connections = listOf(
            Connection(sourceId = 1L, targetId = 2L, weight = 1.0, learningRate = 0.0, delay = 0, plasticityType = PlasticityType.None, innovationId = 4L),
            Connection(sourceId = 2L, targetId = 3L, weight = 1.0, learningRate = 0.0, delay = 0, plasticityType = PlasticityType.None, innovationId = 5L)
        )
        val genome = Genome(
            neurons = listOf(inputNeuron, hiddenNeuron, outputNeuron),
            connections = connections,
            resourceBudgets = ResourceBudgets(maxNeurons = 3, maxConnections = 2, maxDelay = 0, maxActivationFunctionDepth = 1),
            generation = 0
        )
        val network = NeuralNetwork(genome)
        // first step: no propagation to output
        val outputs1 = network.step(mapOf(1L to 1.0))
        outputs1[3L]!!.shouldBeExactly(0.0)
        // second step: hidden output propagates to output
        val outputs2 = network.step(mapOf(1L to 1.0))
        val hiddenOut = 1.0 / (1.0 + exp(-1.0))
        val expected = 1.0 / (1.0 + exp(-hiddenOut))
        outputs2[3L]!!.shouldBeExactly(expected)
    }

    "self-connection influences second step but not first" {
        val inputNeuron = Neuron(
            id = 1L,
            type = NeuronType.Input,
            activation = ActivationFunction.Sigmoidal,
            bias = 0.0,
            threshold = 0.0,
            innovationId = 1L
        )
        val outputNeuron = Neuron(
            id = 2L,
            type = NeuronType.Output,
            activation = ActivationFunction.Sigmoidal,
            bias = 0.0,
            threshold = 0.0,
            innovationId = 2L
        )
        val connections = listOf(
            Connection(sourceId = 1L, targetId = 2L, weight = 1.0, learningRate = 0.0, delay = 0, plasticityType = PlasticityType.None, innovationId = 3L),
            Connection(sourceId = 2L, targetId = 2L, weight = 0.5, learningRate = 0.0, delay = 0, plasticityType = PlasticityType.None, innovationId = 4L)
        )
        val genome = Genome(
            neurons = listOf(inputNeuron, outputNeuron),
            connections = connections,
            resourceBudgets = ResourceBudgets(maxNeurons = 2, maxConnections = 2, maxDelay = 0, maxActivationFunctionDepth = 1),
            generation = 0
        )
        val network = NeuralNetwork(genome)
        val out1 = network.step(mapOf(1L to 1.0))[2L]!!
        val out2 = network.step(mapOf(1L to 1.0))[2L]!!
        out2.shouldBeGreaterThan(out1)
    }

}) 