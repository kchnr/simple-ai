package pipeline

import gen.ai.Genome
import gen.ai.evolution.SimpleEvolution
import gen.ai.evolution.PipelineConfig

class InitialPopulationGenerator {
    fun generate(config: PipelineConfig): List<Genome> {
        val evolution = SimpleEvolution(
            populationSize = config.populationSize,
            mutationRate = config.mutationRate,
            crossoverRate = config.crossoverRate,
            eliteSize = config.eliteSize,
            seed = config.seed
        )
        return evolution.createInitialPopulation()
    }
} 