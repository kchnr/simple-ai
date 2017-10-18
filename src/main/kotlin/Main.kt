import java.util.*
import java.util.stream.Collectors

//85bilion
//1750 connections each


abstract class Signaler {
    abstract fun signal(x: Double)
}

class OutputSignal(val fn: (Double) -> Unit) : Signaler() {
    override fun signal(x: Double) = fn(x)
}

class Axon(var weight: Double, val neuron: Neuron) : Signaler() {
    override fun signal(x: Double) = neuron.onSignal(x * weight)
}

class Neuron(val bias: Double, val axons: List<Signaler>) {
    private var sum: Double = 0.0

    fun onSignal(input: Double) {
        sum += input
    }

    fun process() {
        val sig = sigmoid()
        axons.forEach { onSignal(sig) }
        sum = 0.0
    }

    private fun calcZ(): Double = sum + bias
    private fun sigmoid(): Double = 1.0 / (1.0 * Math.pow(Math.E, calcZ()))
}

typealias Layer = List<Neuron>

class Network(val layers: List<Layer>) {
    fun exec() {
        layers.forEach { it.forEach { it.process() } }
    }
}

fun lastLayer(n: Int): Layer {
    return 0.rangeTo(n).map {
        val o = OutputSignal(::println)
        Neuron(Random().nextDouble(), listOf(o))
    }
}

fun buildListOfAxons(toLayer: Layer): List<Axon> {
    return toLayer.map {
        Axon(Random().nextDouble(), it)
    }
}

fun layer(n: Int, nextLayer: Layer): Layer {
    return 0.rangeTo(n).map {
        Neuron(Random().nextDouble(), buildListOfAxons(nextLayer))
    }
}

fun buildNetwork(inputFns: List<() -> Double>, vararg layers: Int): Network {
    return Network(layers.foldRight(Pair(emptyList<Layer>(), null as Layer), { n, acc ->
        return when (n) {
            layers.size - 1 -> {
                val last = lastLayer(layers[n])
                Pair(listOf(last), last)
            }
            1 -> {
                val last = lastLayer(layers[n])
                Pair(acc.first + listOf(last), last)
            }
            else -> {
                val last = layer(n, acc.second)
                Pair(acc.first + listOf(last), last)
            }
        }
    }).first)
}


fun main(args: Array<String>) {


}

fun randAxons(n: Int, neuron: Neuron): List<Axon> {
    return Random().doubles(n.toLong())
            .mapToObj { Axon(it, neuron) }
            .collect(Collectors.toList())
}

