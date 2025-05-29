/*
 * Genome.kt
 *
 * Purpose:
 *   Defines the core data structures for the minimal genome encoding of an evolved neural network ("brain").
 *   This is the genotype that evolution operates on to discover minimal, biologically plausible neural networks
 *   that can solve cognitive tasks efficiently while maintaining biological realism.
 *
 * Project Context:
 *   This project aims to discover minimal neural networks for cognitive tasks using evolutionary algorithms
 *   with biological plausibility constraints. The goal is to find the smallest possible "brains" that can
 *   solve problems like sequence memory, pattern recognition, and temporal prediction while using
 *   biologically-inspired mechanisms like neuromodulation, local plasticity, and diverse neuron types.
 *
 * Genome Structure:
 *   - Neurons: Individual processing units with types, activation functions, and state variables
 *   - Connections: Weighted links between neurons with plasticity and delay properties
 *   - Neuromodulators: Global signaling molecules that affect network behavior
 *   - Modules: Organized subnetworks for specific functions
 *   - Resource Budgets: Constraints to prevent runaway complexity
 *   - Innovation History: Tracking for speciation and crossover compatibility
 *
 * Biological Inspiration:
 *   - Diverse neuron types (bursting, adapting, pacemaker, inhibitory, modulatory)
 *   - Neuromodulator systems (dopamine, serotonin, acetylcholine, etc.)
 *   - Local plasticity mechanisms (Hebbian, STDP, homeostatic)
 *   - Temporal dynamics and state variables (calcium, fatigue, membrane potential)
 *   - Modular organization and hierarchical structure
 *
 * Evolution Compatibility:
 *   - Innovation IDs for tracking genetic lineage and enabling crossover
 *   - Resource budgets for multi-objective optimization
 *   - Modular structure for compositional evolution
 *   - Serializable for persistence and analysis
 */

package gen.ai

import gen.ai.ActivationFunction
import kotlinx.serialization.Serializable

@Serializable
sealed class NeuronType {
    @Serializable object Input : NeuronType()
    @Serializable object Output : NeuronType()
    @Serializable object Hidden : NeuronType()
}

@Serializable
data class NeuronState(
    val previousOutput: Double = 0.0
)


@Serializable
data class Neuron(
    val id: Long,
    val type: NeuronType,
    val activation: ActivationFunction, // Assumes ActivationFunction.kt is used as is for now
    val initialState: NeuronState = NeuronState(),
    val bias: Double = 0.0,
    val threshold: Double = 0.0, // Evolvable parameter
    val innovationId: Long
)

@Serializable
data class Connection(
    val sourceId: Long,
    val targetId: Long,
    val weight: Double,
    val learningRate: Double = 0.01, // For Hebbian 'eta'
    val delay: Int = 0, // Evolvable, constrained by ResourceBudgets
    val plasticityType: PlasticityType = PlasticityType.None, // Implies if plastic or not
    val innovationId: Long
    // MVP: Deferring direct plasticity boolean if type covers it
    // val plasticity: Boolean = false,
)

@Serializable
sealed class PlasticityType {
    @Serializable object None : PlasticityType()
    @Serializable object Hebbian : PlasticityType()
}

@Serializable
data class Genome(
    val neurons: List<Neuron>,
    val connections: List<Connection>,
    val resourceBudgets: ResourceBudgets,
    val innovationHistory: List<Long> = emptyList(), // Useful for NEAT-like EA later
    val generation: Int = 0
)

@Serializable
data class ResourceBudgets(
    val maxNeurons: Int,
    val maxConnections: Int,
    val maxDelay: Int = 5, // Max synaptic delay for MVP
    val maxActivationFunctionDepth: Int = 7 // Example: constraint for evolved AFs
) 