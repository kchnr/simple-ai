package pipeline

import gen.ai.evolution.PipelineConfig
import pipeline.Pipeline
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldNotBeEmpty

class PipelineTest : StringSpec({

    "pipeline runner should produce a non-empty population" {
        val config = PipelineConfig(populationSize = 5, generationCount = 1)
        val pipeline = Pipeline(config)
        val newPop = pipeline.run()
        newPop.shouldNotBeEmpty()
    }

}) 