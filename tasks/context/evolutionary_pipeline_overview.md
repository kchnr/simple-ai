# Evolutionary Pipeline Overview

This document describes the high-level components and dataflow of the BrainScale evolutionary pipeline (MVP) on the JVM.

---

## Pipeline Stages

1. **Genome Sourcing**
    - Generate initial genomes (randomly) or load saved genomes from storage (future).
2. **Phenotype Builder**
    - Map each `Genome` to a `NeuralNetwork` instance, initializing states and weights.
3. **Evaluator**
    - Run each network through the specified `Task` (e.g., `SequencePredictionTask`), producing fitness scores.
4. **Selector**
    - Choose parent genomes from the evaluated population (e.g., tournament selection) based on fitness.
5. **Reproducer**
    - Apply genetic operators (crossover, mutation) to parents to create offspring genomes.
6. **Population Output**
    - Assemble the new generation, optionally serialize to file or database (future).
7. **Pipeline Runner**
    - Orchestrates the above stages for each generation, handles logging, early stopping, and persistence.

---

## Future Extensions

-   Add persistence layers for checkpointing and resuming evolution.
-   Integrate new stages for speciation or multi-objective ranking.
-   Plug in alternative evaluators for different tasks. 