# Project Context & Business Requirements

## Objective

-   Discover the smallest possible neural networks ("brains") that can solve a suite of cognitive tasks with maximum performance and minimal resources (neurons, synapses, compute cycles).

## Key Business Requirements

-   **Resource Efficiency:** Solutions must minimize neuron count, connection count, and computational cost.
-   **Biological Plausibility:** Networks should use local plasticity and neuromodulation (no backpropagation), with neuron types inspired by real biology, including pacemaker (self-activating) neurons, inhibitory neurons (e.g., GABAergic, glycinergic), neuromodulatory neurons (dopaminergic, serotonergic, noradrenergic, cholinergic, opioidergic), bursting neurons, threshold neurons, sigmoidal neurons, linear neurons, adapting/fatiguing neurons, non-monotonic neurons, context-modulated neurons, mirror neurons, time cells, place cells, grid cells, and neurons for surprise/prediction error.
-   **Diversity & Modularity:** Encourage the evolution and reuse of diverse neuron types and sub-networks (modules), supporting innovation and speciation. Modules may include reusable subgraphs, dendritic compartmentalization, and named sub-networks.
-   **Multi-Task Capability:** Each brain must be evaluated on multiple tasks/environments, including temporal prediction (memory and rhythms), spatial navigation/foraging (planning and movement), associative learning (reward/punishment), and pattern classification (sensory to motor mapping).
-   **Robustness:** Solutions should be robust to noise and generalize across different task instances, including performance variance under parameter noise and different environment conditions.
-   **Local-First & Extensible:** All data and experiments are stored locally (SQLite), and the system should be easy to extend with new neuron types, tasks, evolutionary operators, and environments without major refactoring.
-   **Performance Constraints:** The system must run efficiently on local hardware, supporting hundreds of brains and generations, with resource budgets and early stopping for low-performing individuals.

## Core Concepts

-   **Genome Encoding:** Each brain is encoded as a genome specifying neuron types, connection topology (directed graph: source and target neurons), activation functions (expression trees with primitives such as +, -, \*, /, exp, sin, tanh, max, ReLU, conditional operations, temporal filters, noise), neuromodulator receptors (dopamine, serotonin, norepinephrine, acetylcholine, endorphins), internal state variables (e.g., previous output, calcium, fatigue), plasticity flags, innovation IDs, and resource budgets (limits on neurons, connections, compute cost).
-   **Evolutionary Search:** Use genetic algorithms to evolve both structure and function, with operators for adding/removing neurons and connections, mutating neuron models (expression trees and parameters), duplicating/pruning modules, subtree mutation/crossover, hoist/prune inactive branches, parameter mutation, and innovation tracking/speciation.
-   **Fitness Evaluation:** Multi-objective fitness combines task performance, resource usage, and robustness, using Pareto optimization or composite scoring (e.g., performance minus resource penalty plus novelty bonus). Fitness is measured across all tasks/environments, with penalties for resource usage and bonuses for novelty and robustness.
-   **Wake–Sleep Cycle:** Brains learn and adapt through a biologically inspired wake–sleep cycle, including a wake phase (input presentation, topological feedforward, local updates, performance collection) and a sleep phase (no external input, replay, noise injection, consolidation, pruning, and final fitness measurement).
-   **Environment Interface:** Standardized agent-environment API (e.g., process(inputs): outputs) to allow brains to be evaluated across diverse tasks, with environments providing input/output sizes, step functions, and fitness evaluation.

## Biological Inspiration

-   Support for self-activating (pacemaker) neurons (e.g., cholinergic neurons in basal forebrain, thalamic relay cells, spinal cord interneurons), neuromodulatory broadcast neurons (dopaminergic, serotonergic, noradrenergic, cholinergic, opioidergic), diverse activation functions (linear, threshold, bursting, sigmoidal, rectified, adapting/fatiguing, non-monotonic), inhibitory neurons (basket cells, chandelier cells, Martinotti cells), context-modulated neurons (prefrontal cortex, anterior cingulate), mirror neurons (premotor cortex, parietal areas), time cells, place cells, grid cells (hippocampus), and neurons for surprise/prediction error (dopamine system, sensory cortex).
-   Emphasis on computation by diversity, modularity, and local learning rules, reflecting modern neuroscience insights into cognition, learning, and adaptation.

## Additional Context (2024-04-27)

-   For initial experiments, input/output channels may be hardcoded to validate the system. The long-term goal is for I/O specialization to emerge through evolution.
-   Memory, learning cycles (e.g., wake–sleep), and other advanced features are not requirements; they should emerge if beneficial for the task and are not imposed by design.
-   Resource efficiency is encouraged but not enforced at the expense of performance. Fitness will balance task success and resource use, allowing for trade-offs and "bang for the buck."
-   The environment is external and generic; brains must evolve to discover and exploit available channels.
-   **Learning and adaptability are critical:** The environment will be dynamic, noisy, or change over time, making innate solutions less effective. The evolutionary process should reward not only the best final performers, but also those brains that show the greatest improvement (learning) across varied conditions. This ensures the emergence of flexible, robust, and general intelligence, not just narrow optimization for a fixed scenario.
-   **Offline cycles:** The evaluation protocol will include "offline" cycles, where the environment's input channels are turned off for a fixed number of steps. During these cycles, only self-activating neurons or internal network dynamics can drive activity. This tests for memory, internal modeling, and the ability to function without immediate sensory input.

---

# Implementation Plan

## Project Setup & Tooling

-   [ ] Initialize project structure (Kotlin, Gradle, src/main, src/test folders)
-   [ ] Set up build tool (Gradle) and dependency management
-   [ ] Choose and configure testing framework (JUnit, Kotest, etc.)
-   [ ] Add a sample failing test to verify test setup
-   [ ] Set up code formatting/linting (optional but recommended)
-   [ ] (Optional) Set up basic CI (GitHub Actions, etc.)

## Planning & Foundations

-   [ ] Formalize primitive grammar and AST depth limit for neuron activation functions
-   [ ] Define minimal genome structure (neurons, connections, budgets, innovation IDs)
-   [ ] Design initial SQLite schema for genome, innovation history, and experiment data
-   [ ] Decide on modular project structure (GA engine, simulation core, environments, persistence)

## Core Implementation

-   [x] Implement genome data classes and serialization (Kotlin)
-   [ ] Implement SQLite persistence layer (using Exposed or JDBC)
-   [ ] Build phenotype builder (genome → network instance)
-   [ ] Implement simulation core: topological feedforward, local plasticity, neuromodulation
-   [ ] Integrate Jenetics for GA and GP support
-   [ ] Implement genetic operators (add/remove/mutate neuron, connection, expression tree, module duplication/pruning)
-   [ ] Implement innovation tracking and speciation

## Wake–Sleep Learning Cycle

-   [ ] Implement wake phase: input presentation, feedforward, local updates
-   [ ] Implement sleep phase: replay, noise, consolidation, pruning

## Environments & Fitness

-   [ ] Implement standard agent API (process(inputs): outputs)
-   [ ] Build first environment: temporal prediction
-   [ ] Implement multi-objective fitness evaluation (performance + resource penalty)
-   [ ] Implement Pareto selection, elitism, and speciation

## Performance & Extensibility

-   [ ] Add parallel evaluation (Kotlin coroutines/streams)
-   [ ] Tune JVM/GC settings for batch runs
-   [ ] Add debug/sampled logging and lightweight fitness reporting
-   [ ] Document how to add new neuron primitives, tasks, or operators

## Expansion & Iteration

-   [ ] Add more environments (spatial navigation, associative learning, pattern classification)
-   [ ] Analyze results, iterate on genome and operators
-   [ ] Refine persistence, reporting, and modularity
