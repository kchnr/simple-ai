package gen.ai.core.network

import kotlinx.serialization.Serializable

@Serializable
data class Connection(
    val sourceNeuronId: String,
    val targetNeuronId: String,
    var weight: Double, // Mutable, as it will be updated by plasticity
    val eta: Double // Learning rate for the Hebbian-like rule, evolvable
) 