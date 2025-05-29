/*
 * ActivationFunction.kt
 *
 * Purpose:
 *   Defines the abstract syntax tree (AST) for neuron activation functions in evolved neural networks.
 *   Each activation function is represented as a composable expression tree of mathematical primitives,
 *   variables, and constants. This allows evolution to discover novel activation functions tailored
 *   to specific cognitive tasks while maintaining biological plausibility.
 *
 * Project Context:
 *   This is a key component for discovering minimal neural networks that solve cognitive tasks.
 *   Instead of using fixed activation functions (like ReLU or sigmoid), each neuron can evolve
 *   its own unique activation function as an expression tree. This provides enormous flexibility
 *   for evolution to discover novel computational primitives while maintaining efficiency.
 *
 * AST Structure:
 *   - Primitives: Basic mathematical operations (add, multiply, sin, tanh, etc.)
 *   - Variables: References to neuron inputs, state, and global signals
 *   - Constants: Evolved numerical parameters
 *   - Conditionals: If-then-else logic for complex behaviors
 *   - Depth Tracking: Resource budgets prevent overly complex functions
 *
 * Variable Bindings:
 *   - "input"/"sum": Current weighted input sum to the neuron
 *   - "prev_output": Previous activation value (for temporal dynamics)
 *   - "calcium": Calcium concentration (learning signal)
 *   - "fatigue": Adaptation/fatigue level
 *   - "membrane": Membrane potential
 *   - "context": Contextual state information
 *   - Custom variables: Neuron-specific state variables
 *
 * Evolutionary Benefits:
 *   - Evolvable Complexity: Functions can start simple and grow more complex
 *   - Task Specialization: Different neurons can evolve different functions
 *   - Biological Realism: Can model complex neuron behaviors
 *   - Resource Efficiency: Depth limits prevent computational bloat
 *   - Crossover Compatibility: AST structure enables meaningful recombination
 *
 * Performance Considerations:
 *   - Runtime evaluation uses recursive tree traversal
 *   - Variable lookup by string name (could be optimized to indices)
 *   - Depth limits prevent exponential evaluation time
 *   - Safe division and bounded operations prevent numerical issues
 *   - Caching could be added for repeated evaluations
 */
package gen.ai

import kotlinx.serialization.Serializable

@Serializable
sealed class ActivationFunction {
    abstract val depth: Int

    @Serializable
    data class Const(val value: Double) : ActivationFunction() {
        override val depth: Int = 1
    }

    @Serializable
    data class Variable(val name: String) : ActivationFunction() {
        override val depth: Int = 1
    }

    @Serializable
    data class Add(val left: ActivationFunction, val right: ActivationFunction) : ActivationFunction() {
        override val depth: Int = 1 + maxOf(left.depth, right.depth)
    }
    @Serializable
    data class Sub(val left: ActivationFunction, val right: ActivationFunction) : ActivationFunction() {
        override val depth: Int = 1 + maxOf(left.depth, right.depth)
    }
    @Serializable
    data class Mul(val left: ActivationFunction, val right: ActivationFunction) : ActivationFunction() {
        override val depth: Int = 1 + maxOf(left.depth, right.depth)
    }
    @Serializable
    data class Div(val left: ActivationFunction, val right: ActivationFunction) : ActivationFunction() {
        override val depth: Int = 1 + maxOf(left.depth, right.depth)
    }
    @Serializable
    data class Exp(val arg: ActivationFunction) : ActivationFunction() {
        override val depth: Int = 1 + arg.depth
    }
    @Serializable
    data class Sin(val arg: ActivationFunction) : ActivationFunction() {
        override val depth: Int = 1 + arg.depth
    }
    @Serializable
    data class Tanh(val arg: ActivationFunction) : ActivationFunction() {
        override val depth: Int = 1 + arg.depth
    }
    @Serializable
    data class Sigmoid(val arg: ActivationFunction) : ActivationFunction() {
        override val depth: Int = 1 + arg.depth
    }
    @Serializable
    object Sigmoidal : ActivationFunction() {
        override val depth: Int = 1
    }
    @Serializable
    data class Max(val left: ActivationFunction, val right: ActivationFunction) : ActivationFunction() {
        override val depth: Int = 1 + maxOf(left.depth, right.depth)
    }
    @Serializable
    data class ReLU(val arg: ActivationFunction) : ActivationFunction() {
        override val depth: Int = 1 + arg.depth
    }
    @Serializable
    data class If(
        val condition: ActivationFunction,
        val ifTrue: ActivationFunction,
        val ifFalse: ActivationFunction
    ) : ActivationFunction() {
        override val depth: Int = 1 + maxOf(condition.depth, ifTrue.depth, ifFalse.depth)
    }
} 