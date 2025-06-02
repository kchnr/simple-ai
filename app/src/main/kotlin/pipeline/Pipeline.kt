package pipeline

import org.slf4j.LoggerFactory
import gen.ai.Genome
import gen.ai.evolution.SimpleEvolution
import gen.ai.network.NeuralNetwork
import gen.ai.evolution.PipelineConfig
import gen.ai.tasks.SequencePredictionTask

private val logger = LoggerFactory.getLogger(Pipeline::class.java)

/**
 * A simple pipeline runner that wires together genome generation, mapping, evaluation, and evolution.
 * Moved into the evolution domain and renamed from SimplePipelineRunner.
 */
class Pipeline(private val config: PipelineConfig) {

    /**
     * Runs the evolutionary pipeline for the configured number of generations.
     * @return the final population of genomes.
     */
    fun run(): List<Genome> {
        logger.info("Starting pipeline with config: {}", config)
        val evolution = SimpleEvolution(
            populationSize = config.populationSize,
            mutationRate = config.mutationRate,
            crossoverRate = config.crossoverRate,
            eliteSize = config.eliteSize,
            seed = config.seed
        )
        var population = evolution.createInitialPopulation()
        val task = SequencePredictionTask(sequenceLength = config.sequenceLength)
        repeat(config.generationCount) {
            val result = evolution.evolveGeneration(population, task)
            population = result.newPopulation
        }
        logger.info("Pipeline complete. Final population size: {}", population.size)
        return population
    }
} 