package gen.ai.evolution

/**
 * Configuration for the evolutionary pipeline hyperparameters.
 */
data class PipelineConfig(
    val populationSize: Int = 30,
    val mutationRate: Double = 0.1,
    val crossoverRate: Double = 0.7,
    val eliteSize: Int = 5,
    val seed: Long = 42,
    val generationCount: Int = 20,
    val sequenceLength: Int = 20,
    val sequenceTrials: Int = 3
) 