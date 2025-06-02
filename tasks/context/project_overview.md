# Project Overview: Evolving Adaptive Neural Networks

### Objectives

To discover and evolve minimal, computationally efficient neural networks ("brains") that exhibit local synaptic plasticity and neuromodulation, enabling them to solve a suite of cognitive tasks with high performance. The project emphasizes biological plausibility and emergent adaptation through evolutionary processes running locally on CPU.

### Requirements

_(Placeholder for explicit project requirements - to be defined)_

### Background

#### Core Philosophy: Emergence & Iterative Value

-   **Emergence over Explicit Programming:** Complex adaptive behaviors (learning, memory, plasticity) should emerge from the evolution of simpler, local rules and network structures, rather than being explicitly pre-programmed.
-   **Iterative Value & Practicality:** Prioritize demonstrating core viability through a Minimal Viable Product (MVP) and incrementally add complexity. Each phase should deliver tangible insights or capabilities.
-   **Local First, CPU First:** Initial development and validation will focus on local CPU execution to ensure a solid foundation before considering distributed computing or hardware acceleration (GPU). Ideas for future scaling will be documented but deferred.
-   **Knowledge Capture:** This document (`tasks.md`) serves as the single source of truth for project goals, context, research, and implementation plans, enabling continuity for any contributor.

#### Key Desired Characteristics of Evolved Brains

-   **Resource Efficiency:** Minimize neuron count, connection count, and computational cost (evaluation time).
-   **Adaptive Plasticity:** Networks must demonstrate within-lifetime adaptation (learning) through evolved local synaptic plasticity rules and neuromodulation. No backpropagation.
-   **Biological Inspiration:**
    -   Neuron types: Pacemaker (self-activating), inhibitory, various neuromodulatory types (e.g., dopaminergic, serotonergic), and functional types like bursting, threshold, sigmoidal, adapting/fatiguing, etc. (as emergent properties of evolved activation functions or explicit types as defined in `Genome.kt`).
    -   Mechanisms: Local learning rules, neuromodulatory influences on plasticity.
    -   Emergent State Dynamics: Networks should demonstrate behaviors analogous to biological neuronal state (membrane potential integration, refractory periods) and synaptic state (short-term and long-term plasticity effects) as a result of evolved mechanisms, not explicit programming of these phenomena.
-   **Dynamic Task Solving:** Ability to perform well in environments where conditions change, requiring adaptation.
-   **Robustness:** Resilience to noise and generalization across task variations.

#### Foundational Technical Concepts (Informed by Research)

-   **Genome Encoding:** A flexible representation specifying neuron types, connection topology (dynamic graph), parameters for local plasticity rules, neuromodulatory sensitivities, and evolvable activation functions (via Genetic Programming - GP), as partially implemented in `Genome.kt` and `ActivationFunction.kt`.
-   **Evolutionary Algorithm (EA):** Genetic Algorithm or Genetic Programming as the core search mechanism, with a basic version implemented in `SimpleEvolution.kt`.
    -   Operators: Mutations for topology (add/remove neuron/connection), plasticity parameters, activation function trees (if GP is used). Crossover where appropriate.
    -   Selection: Multi-objective approach (e.g., NSGA-II) balancing performance, novelty, and resource cost.
    -   Speciation: To maintain diversity, potentially behavior-based.
-   **Activation Functions:** Currently a rich set of primitives defined in `ActivationFunction.kt`. Phase 2 will focus on GP-based evolution of these.
-   **Sparse Data Structures (JVM):** CSR-like format (primitive arrays for weights, targets, offsets) for cache efficiency and enabling JVM auto-vectorization (Java Vector API). Memory pooling and careful GC management are key (Future Phase).
-   **Task Design for Plasticity:** Environments must be non-stationary (e.g., reward shifts, sensor changes, rule changes within an evaluation lifetime) to select for true adaptation. Fitness will explicitly reward improvement due to plasticity. A basic version is in `SequencePredictionTask.kt`.
-   **JVM & Kotlin:** Leverage the JVM's JIT, multi-threading, and Kotlin's expressiveness for a productive and performant local-first implementation. 