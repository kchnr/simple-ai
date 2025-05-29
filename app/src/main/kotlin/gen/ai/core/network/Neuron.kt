package gen.ai.core.network

import kotlinx.serialization.Serializable

@Serializable
data class Neuron(
    val id: String, // Using String for ID for potential future flexibility (e.g., UUIDs)
    val activationFunctionType: ActivationFunctionType,
    var currentActivationValue: Double = 0.0 // Mutable state for activation
) {
    // Companion object can hold actual activation function implementations if needed
    companion object {
        fun sigmoid(x: Double): Double {
            return 1.0 / (1.0 + kotlin.math.exp(-x))
        }

        fun relu(x: Double): Double {
            return kotlin.math.max(0.0, x)
        }
    }

    // Method to apply the activation function - decided against this for now.
    // The network execution logic will handle applying the function based on the type.
    // This keeps Neuron as a pure data holder as much as possible for the MVP.
} 