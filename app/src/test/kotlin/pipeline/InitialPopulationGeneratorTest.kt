package pipeline

import gen.ai.evolution.PipelineConfig
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize

class InitialPopulationGeneratorTest : StringSpec({

    "generate should produce initial population of the configured size" {
        val config = PipelineConfig(populationSize = 3)
        val generator = InitialPopulationGenerator()
        val population = generator.generate(config)
        population.shouldHaveSize(3)
    }

}) 