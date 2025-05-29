# Project ???: Evolving Adaptive Neural Networks

## Objective

To discover and evolve minimal, computationally efficient neural networks ("brains") that exhibit local synaptic plasticity and neuromodulation, enabling them to solve a suite of cognitive tasks with high performance. The project emphasizes biological plausibility and emergent adaptation through evolutionary processes running locally on CPU.

## Core Philosophy: Emergence & Iterative Value

-   **Emergence over Explicit Programming:** Complex adaptive behaviors (learning, memory, plasticity) should emerge from the evolution of simpler, local rules and network structures, rather than being explicitly pre-programmed.
-   **Iterative Value & Practicality:** Prioritize demonstrating core viability through a Minimal Viable Product (MVP) and incrementally add complexity. Each phase should deliver tangible insights or capabilities.
-   **Local First, CPU First:** Initial development and validation will focus on local CPU execution to ensure a solid foundation before considering distributed computing or hardware acceleration (GPU). Ideas for future scaling will be documented but deferred.
-   **Knowledge Capture:** This document (`tasks.md`) serves as the single source of truth for project goals, context, research, and implementation plans, enabling continuity for any contributor.

## Key Desired Characteristics of Evolved Brains

-   **Resource Efficiency:** Minimize neuron count, connection count, and computational cost (evaluation time).
-   **Adaptive Plasticity:** Networks must demonstrate within-lifetime adaptation (learning) through evolved local synaptic plasticity rules and neuromodulation. No backpropagation.
-   **Biological Inspiration:**
    -   Neuron types: Pacemaker (self-activating), inhibitory, various neuromodulatory types (e.g., dopaminergic, serotonergic), and functional types like bursting, threshold, sigmoidal, adapting/fatiguing, etc. (as emergent properties of evolved activation functions or explicit types as defined in `Genome.kt`).
    -   Mechanisms: Local learning rules, neuromodulatory influences on plasticity.
-   **Dynamic Task Solving:** Ability to perform well in environments where conditions change, requiring adaptation.
-   **Robustness:** Resilience to noise and generalization across task variations.

## Foundational Technical Concepts (Informed by Research)

-   **Genome Encoding:** A flexible representation specifying neuron types, connection topology (dynamic graph), parameters for local plasticity rules, neuromodulatory sensitivities, and evolvable activation functions (via Genetic Programming - GP), as partially implemented in `Genome.kt` and `ActivationFunction.kt`.
-   **Evolutionary Algorithm (EA):** Genetic Algorithm or Genetic Programming as the core search mechanism, with a basic version implemented in `SimpleEvolution.kt`.
    -   Operators: Mutations for topology (add/remove neuron/connection), plasticity parameters, activation function trees (if GP is used). Crossover where appropriate.
    -   Selection: Multi-objective approach (e.g., NSGA-II) balancing performance, novelty, and resource cost.
    -   Speciation: To maintain diversity, potentially behavior-based.
-   **Activation Functions:** Currently a rich set of primitives defined in `ActivationFunction.kt`. Phase 2 will focus on GP-based evolution of these.
-   **Sparse Data Structures (JVM):** CSR-like format (primitive arrays for weights, targets, offsets) for cache efficiency and enabling JVM auto-vectorization (Java Vector API). Memory pooling and careful GC management are key (Future Phase).
-   **Task Design for Plasticity:** Environments must be non-stationary (e.g., reward shifts, sensor changes, rule changes within an evaluation lifetime) to select for true adaptation. Fitness will explicitly reward improvement due to plasticity. A basic version is in `SequencePredictionTask.kt`.
-   **JVM & Kotlin:** Leverage the JVM's JIT, multi-threading, and Kotlin's expressiveness for a productive and performant local-first implementation.

## Prototyping & Experimentation Strategy

To facilitate both rapid exploration of novel concepts and robust implementation of core functionalities, the project will utilize a primary Kotlin codebase alongside a dedicated prototyping environment.

## `prototypes` Directory (Python Prototyping)

-   **Location:** A `prototypes/` directory within the project root.
-   **Purpose:** Quick prototyping and validation of complex or high-risk ideas, such as new plasticity rules, neurogenesis mechanisms, neuromodulation effects, advanced evolutionary operators, or alternative genome encodings.
-   **Technology:** Python, leveraging libraries like DEAP, NEAT-Python, NumPy, SciPy, and other relevant machine learning/scientific computing packages.
-   **Workflow:** Experiments conducted in this directory aim to quickly prove out the viability or characteristics of a concept.
-   **Documentation:** Findings, key parameters, and successful patterns from prototypes must be documented. Summaries and conclusions should be integrated into `tasks.md` as context or as refined tasks for the main Kotlin implementation.

## Main Application (Kotlin/JVM in `app/`)

-   **Location:** The standard `app/src/main/kotlin/` directory for the primary codebase.
-   **Purpose:** The official, robust implementation of the BrainScale project.
-   **Technology:** Kotlin on the JVM, using Gradle for builds. Libraries such as Jenetics or ECJ may be considered for enhancing the evolutionary algorithm framework.
-   **Characteristics:** Strongly typed, modular, optimized for CPU performance (initially), and thoroughly tested using Kotest.

## Workflow: Prototypes to Main Application

1.  Hypothesize and explore new mechanisms or approaches within the `prototypes/` Python environment.
2.  Document findings and successful patterns thoroughly.
3.  If an idea proves effective and aligns with project goals, design and implement it robustly within the main Kotlin system in `app/`. This typically involves creating new tasks or refining existing ones in `tasks.md`.

## Future Considerations & Advanced Research (Post-Local CPU Validation)

**Goal:** Document ambitious long-term ideas that go beyond initial local CPU validation. These are not immediate tasks but are preserved for future exploration if the project shows significant promise.

## Scalability & Distributed Computing

-   [ ] Distributed Evolution (Island Model):
    -   Migration strategies (e.g., via S3/DynamoDB checkpoints for spot instance resilience).
    -   Frameworks: Spark, Ray, or AWS Batch/ECS depending on evaluation granularity.
-   [ ] GPU Acceleration:
    -   Offload large batch evaluations or specific computations (e.g., using CSR mapping to cuSparse if CSR is implemented).

## Highly Complex Emergent Phenomena

-   [ ] True runtime neurogenesis and structural plasticity (continuous modification of neuron count and connections during task execution based on evolved rules).
-   [ ] Evolution of complex modularity and hierarchical structures (building on `NetworkModule` concepts).
-   [ ] Advanced multi-task learning and transfer learning across diverse domains.

## Advanced Tooling & Experimentation

-   [ ] GraalVM native-image for specific components or smaller, GC-less experiments.
-   [ ] Comprehensive CI/CD pipelines for experimentation.
-   [ ] Public-facing results, datasets, and evolved model repositories.

## Open Research Questions & Ongoing Investigations

_(This section retains key questions that guide ongoing research and exploration, often addressed initially in the `prototypes/` environment.)_

-   [ ] **Evolutionary Algorithm Design:** What are the most effective operators for combined topology/parameter/GP evolution? How best to balance exploration/exploitation? What are the most effective speciation mechanisms for these complex phenotypes?
-   [ ] **Network Architecture & Evaluation (JVM Specific):** If dynamic sparse graph representations (like CSR) are needed, what is the most efficient design for the JVM? What are the best cache-friendly layouts for evolving structures? What are the actual benefits of vectorization (Java Vector API) vs. its implementation complexity?
-   [ ] **Biological Plausibility & Learning:** Which local plasticity rules and activation function primitives yield the richest emergent learning behaviors? How can neuromodulation be modeled effectively yet remain computationally tractable? Are there benefits to distinct wake/sleep/developmental phases for structural plasticity?
-   [ ] **Performance & Scalability (Local & Beyond):** What are the practical limits of JVM tuning for this application? What are the most effective parallelization patterns for evaluation and evolution? How do we identify and resolve bottlenecks as network and task complexity grows?
-   [ ] **Emergent Plasticity Mechanisms:** How can the potential for various forms of plasticity be best encoded in the genome without over-constraining evolution? What environmental pressures are key for selecting different plasticity types (synaptic, structural, neurogenesis)? What is the metabolic/computational cost/benefit of plasticity, and can it be an evolutionary objective? How can we develop robust metrics for "true" or "meaningful" learning?
-   [ ] **Advanced Concepts:** How can meta-learning strategies be effectively evolved and represented in the genome? What are the minimal conditions and representations for the emergence of useful neurogenesis or complex structural adaptation based on evolved rules?
-   [ ] **Intermittent Task Engagement & "Sleep" Cycles:** Explore models where brains are not continuously engaged with a task but might have offline periods (simulating sleep or consolidation) where internal processes (e.g., advanced plasticity, structural refinement based on evolved rules) might occur before re-engaging with the task. How would this affect learning and stability, and how could task evaluation accommodate such cycles (e.g., brain attaches to task, "runs" detached simulating sleep/internal processing, then re-attaches, repeating until improvement plateaus or degrades)?

---

## Implementation Plan

### Phase 1: Core Proof-of-Concept (MVP) - Local CPU Focus

**Goal:** Demonstrate the basic viability of evolving a neural network (using existing `Genome.kt` structures as a baseline) with local plasticity to solve a dynamic task (like `SequencePredictionTask.kt`), all running locally. Prove that adaptation can be evolved and observed through the mechanisms in `NeuralNetwork.kt` and `SimpleEvolution.kt`.

#### Project Setup, Code Consolidation & Basic Tooling (MVP)

-   [ ] Verify project structure (Kotlin, Gradle) is optimal and all configurations are correct.
-   [x] Verify testing framework (Kotest) is fully set up and basic tests for existing components pass (`AppTest.kt`, `ActivationFunctionTest.kt`).
    -   [ ] Write initial unit tests for `Genome.kt` data classes and their core properties.
    -   [ ] Write initial unit tests for `NeuralNetwork.kt` basic instantiation and step function with a fixed genome.
    -   [ ] Write initial unit tests for `SimpleEvolution.kt` population initialization and a single generation step.
    -   [ ] Write initial unit tests for `SequencePredictionTask.kt` sequence generation and basic evaluation.
-   [ ] Review and enhance basic logging (`ConsoleLogger.kt`) for clarity and utility during evolution runs.
-   [x] Verify version control practices (Git, `.gitignore`, `.gitattributes`) are sound.
-   [x] Remove the `Neuron.kt`, `Connection.kt`, and `ActivationFunctionType.kt` files from the `app/src/main/kotlin/gen/ai/core/network/` directory.
-   [x] Ensure all project code exclusively uses the data structures defined in `gen.ai/Genome.kt` (e.g., `gen.ai.Neuron`, `gen.ai.Connection`) and `gen.ai/ActivationFunction.kt`.
-   [x] Update any affected imports or logic, particularly if `EvolutionDemo.kt` or tests were referencing the `core.network` versions.

#### Refine Foundational Data Structures & Genome (MVP)

-   [ ] Review and refine `Neuron` data class in `Genome.kt` (ID, type, activation function, initial state, plasticity flags, learning rate, threshold, bias, time constant, neuromodulator sensitivity, innovation ID). Ensure all fields are justified for MVP or clearly marked for future phases.
-   [ ] Review and refine `Connection` data class in `Genome.kt` (source neuron ID, target neuron ID, weight, delay, plasticity flags, plasticity type, innovation ID).
    -   [ ] Confirm the Hebbian-like rule (`delta_w = eta * pre_activation * post_activation`) is the primary target for initial plasticity, where `eta` is analogous to `learningRate` on the `Neuron` or a property of the `Connection`. Clarify how `eta` is represented and evolved.
-   [ ] Review and refine `Genome` data class (`neurons`, `connections`, `neuromodulators`, `modules`, `resourceBudgets`, `innovationHistory`, `generation`, `parentIds`). Simplify if possible for MVP, deferring complex parts like `modules` if not strictly needed.
-   [ ] Implement robust serialization/deserialization for `Genome` (e.g., to JSON using kotlinx.serialization, as already partially set up) for saving/loading populations and for debugging.
    -   [ ] Add tests for genome serialization and deserialization.

#### Refine Network Execution & Simulation Core (MVP)

-   [ ] Review and refine `NeuralNetwork` class based on the `Genome` structure.
    -   [ ] Ensure efficient data structures are used internally for neuron states, outputs, and connection weights (current `MutableMap` usage is acceptable for MVP).
-   [x] Verify network activation propagation in `NeuralNetwork.kt` (`step` function, topological processing).
    -   [x] **P0: CRITICAL FIX** - Address "Warning: Some neurons could not be processed" from `runEvolutionDemo`. Implement a more robust neuron update order in `NeuralNetwork.step()` to handle various network topologies correctly.
    -   [x] Test with feed-forward, recurrent, and self-connections.
-   [x] Implement and test the chosen fixed Hebbian-like plasticity rule within `NeuralNetwork.kt`'s `updatePlasticity` method.
    -   [x] Ensure connection weights are updated based on their `learningRate` (or equivalent `eta`) and pre/post synaptic activity.
    -   [x] Add specific tests for plasticity updates.

#### Refine Evolutionary Algorithm Engine (MVP)

-   [ ] **Establish Core Evolutionary Pipeline Structure:**
    -   [x] Create `docs/pipeline.md` to diagram and describe the end-to-end dataflow:  
             • Genome sourcing (random or load from storage)  
             • Genotype→Phenotype mapping (building `NeuralNetwork` instances)  
             • Task evaluation (using `SequencePredictionTask`)  
             • Selection & reproduction operators (tournament, crossover, mutation)  
             • Population output and optional persistence
    -   [ ] Define Kotlin interfaces in `gen.ai.pipeline` for each pipeline stage (`GenomeSource`, `PhenotypeBuilder`, `Evaluator`, `Selector`, `Reproducer`, `PipelineRunner`).
    -   [ ] Implement `SimplePipelineRunner` wiring existing `SimpleEvolution`, `NeuralNetwork`, and `SequencePredictionTask` into a cohesive pipeline.
    -   [ ] Write an integration test ensuring a trivial genome goes through the pipeline and returns a new population.
-   [ ] Review and refine the `SimpleEvolution.kt` Genetic Algorithm loop:
    -   [ ] Population initialization (current random genome creation is a good start).
    -   [ ] Fitness evaluation (delegates to `SequencePredictionTask.kt`).
    -   [ ] Selection (current tournament selection is acceptable).
    -   [ ] Refine mutation operators:
        -   [ ] Mutate connection weights.
        -   [ ] Mutate plasticity parameters (e.g., `learningRate` on Neurons).
        -   [ ] Mutate neuron bias and threshold.
        -   [ ] Add/remove connection (ensure innovation IDs are handled if NEAT-like concepts are planned for later).
        -   [ ] Add/remove neuron (ensure connections are handled, and innovation IDs).
    -   [ ] Defer crossover for initial MVP to keep it simple, or ensure the current basic crossover in `SimpleEvolution.kt` is sufficiently robust for initial tests.
-   [ ] Review and improve basic population management in `SimpleEvolution.kt`.
-   [ ] Add tests for key EA components like mutation and selection.

#### Refine Environment & Fitness Evaluation (MVP)

-   [ ] Ensure the evaluation loop in `SimpleEvolution.kt` correctly instantiates the `NeuralNetwork` (brain) from a `Genome` (genes), evaluates it using the `SequencePredictionTask` (environment), allows for within-lifetime plasticity, and correctly calculates fitness.
-   [ ] Review and refine the `SequencePredictionTask.kt` environment:
    -   [ ] Task: Agent has 1 input (value at time `t`), 1 output (predicted value at `t+1`).
    -   [ ] Dynamic aspect: The underlying sequence type can change, or a parameter within a sequence type can shift, requiring adaptation.
    -   [ ] Example: Initially, predict a sine wave. After N steps (within one evaluation), the sine wave's frequency or amplitude changes. The network must adapt its predictions.
    -   [ ] **Document Future Task: Advanced Task/Environment Design:** Note for future phases: The design of tasks and environments is critical for driving the evolution of complex adaptive behaviors. Future work will involve more diverse, dynamic, and potentially open-ended environments.
-   [ ] Refine the fitness function in `SequencePredictionTask.kt` to strongly reward adaptation:
    -   [ ] Evaluate performance in the first phase (before change) and second phase (after change).
    -   [ ] Fitness = (Accuracy in 2nd phase) - (Accuracy in 1st phase) + bonus for overall accuracy.
    -   [ ] Alternative: Fitness = Accuracy in 2nd phase _if_ 1st phase was also solved to a minimum degree.

#### Local Execution, Validation & Demo (MVP)

-   [ ] Refine `EvolutionDemo.kt` (`runEvolutionDemo` function) to run the complete evolutionary loop.
-   [ ] Enhance logging in `EvolutionDemo.kt`: generation number, best fitness, average fitness, diversity metrics (e.g., average genome size, number of active plastic components).
-   [ ] Develop tools or methods to manually inspect evolved genomes/phenotypes that show adaptation (e.g., serialize best genome, visualize its structure or activity).
-   [ ] Develop analysis to detect and visualize emergent inhibitory autapses (self-connections) and quantify their effect on network timing and dynamics.
-   **Goal:** Observe if plasticity parameters evolve meaningfully and if networks demonstrably change their output correctly after a dynamic task switch, using the existing demo as a starting point.

### Phase 2: Enhancing Core Capabilities & Robustness - Local CPU Focus

**Goal:** Build upon the MVP by introducing evolvable activation functions using the primitives in `ActivationFunction.kt`, more sophisticated plasticity/neuromodulation, richer environments, and a more robust EA. Improve JVM performance.

#### Advanced Genome & Activation Function Evolution

-   [ ] **Define and Implement Grammar for Activation Function Evolution:**
    -   [ ] Formalize a Backus-Naur Form (BNF) or similar grammar for constructing neuron activation functions trees using primitives from `ActivationFunction.kt` (`+`, `-`, `*`, `/` (protected), `sin`, `tanh`, `exp`, `log` (protected), `max`, `min`, `if`, `Variable` (input `x`, `prev_output`, etc.), `Const`).
    -   [ ] Ensure balanced recursion and enforce a maximum tree depth (e.g., 5-7 levels) to prevent bloat.
-   [ ] **Integrate Genetic Programming (GP) for Activation Functions:**
    -   [ ] Select and integrate a GP framework (e.g., Jenetics) or extend `SimpleEvolution.kt` with GP capabilities.
    -   [ ] _Investigate & Decide:_ Evaluate Jenetics vs. ECJ (or others) vs. custom GP implementation for ease of Kotlin integration and features needed for evolving `ActivationFunction` trees. Consider a lightweight Kotlin DSL wrapper if using a library.
-   [ ] Modify `Neuron` structure in `Genome.kt` so its `activation: ActivationFunction` field is the subject of GP evolution.
-   [ ] Implement GP operators for activation functions (e.g., subtree crossover, point mutation for constants within the tree, terminal/primitive replacement).
-   [ ] Implement constant-folding / algebraic simplification for evolved activation trees (optional optimization).

#### Enhanced Plasticity & Basic Neuromodulation

-   [ ] **Explore and Implement Diverse Local Plasticity Rules:**
    -   [ ] Investigate alternatives or additions to the initial Hebbian-like rule (e.g., BCM rule, Oja's rule, or rules with eligibility traces). Prototype promising candidates in the `prototypes/` directory.
    -   [ ] Allow evolution to select from a small set of predefined plasticity rule _types_ per connection (extending `PlasticityType` enum in `Genome.kt`), or evolve more parameters for a richer single rule.
-   [ ] **Introduce Basic Neuromodulation:**
    -   [ ] Refine `NeuromodulatorNeuron` types (e.g., `Dopaminergic` in `Genome.kt`) and their effects.
    -   [ ] Genome specifies if a neuron is modulatory.
    -   [ ] Modulatory neuron output (e.g., a scalar value) can influence parameters of other neurons or connections (e.g., scale the `learningRate` of connections on _target neurons_ it projects to, or directly affect activation thresholds).
    -   [ ] _Define & Implement:_ Specify how modulatory connections/projections are represented and how their influence is calculated and applied in `NeuralNetwork.kt`. Start with global or broad local influence.

#### Improved Data Structures & JVM Performance

-   [ ] **Implement CSR-like Sparse Data Structures (If Necessary):**
    -   [ ] Profile network execution. If `MutableMap` lookups for connections become a bottleneck for larger networks, implement CSR-like sparse data structures for network connections (primitive arrays for weights, targets, neuron offsets) to replace simpler Phase 1 list/map structures in `NeuralNetwork.kt`.
    -   [ ] _Detail Design (if CSR is pursued):_ Specify the CSR implementation for dynamic graphs (handling updates if topology changes during evolution, though typically networks are static during one evaluation).
-   [ ] Implement memory pooling for frequently allocated/deallocated objects (e.g., arrays, network state objects) if GC pressure becomes an issue.
-   [ ] **Profile and Optimize JVM Performance:**
    -   [ ] Use profiling tools (e.g., JFR, VisualVM, async-profiler) to identify bottlenecks in network simulation and evolution.
    -   [ ] Optimize hotspots: e.g., consider primitive collections (like `fastutil`) if standard collections are slow, audit for excessive autoboxing.
-   [ ] Consider array padding for SIMD alignment if Java Vector API is to be explored for network activation (likely Phase 3 or later).

#### Advanced Evolutionary Algorithm Features

-   [ ] **Implement Speciation:**
    -   [ ] Introduce speciation to protect novel structures and maintain population diversity. Options: genomic distance (e.g., NEAT-like compatibility based on innovation IDs and parameter differences) or simple behavioral clustering.
    -   [ ] _Investigate & Decide:_ Compare structural vs. simple behavioral speciation for this stage.
-   [ ] Implement more sophisticated mutation operators (e.g., hoist mutation for GP trees, mutations that add/remove neuromodulators or modules).
-   [ ] **Introduce Multi-objective Optimization:**
    -   [ ] Integrate NSGA-II (e.g., via Jenetics or a custom implementation if not using a full library).
    -   [ ] Objectives: 1. Task Performance (maximize), 2. Resource Cost (e.g., neuron/connection count, activation function complexity - minimize).
    -   [ ] Consider adding Novelty (behavioral distance from population mean/archive) as a third objective later in this phase.
    -   [ ] _Define Behavioral Descriptor:_ Specify a simple behavioral descriptor for novelty calculation (e.g., vector of outputs on a fixed set of probe inputs, or final network states after a task).

#### Richer Environments & Fitness

-   [ ] Design and implement more complex dynamic tasks.
    -   [ ] Example: Tasks requiring memory of several previous states, or more complex input-output mappings that change unpredictably.
    -   [ ] Consider a simple sequential task (e.g., agent must output A then B, signaled by cues, where the sequence or cues can change).
-   [ ] Refine fitness function to better capture adaptive capabilities and potentially use metrics from multi-phase evaluation (e.g., score for robustness to change vs. speed/degree of adaptation).
-   [ ] Implement curriculum learning: manually or semi-automatically increase task difficulty as population performance improves.

### Phase 3: Advanced Mechanisms & Exploration - Local CPU Focus

**Goal:** Explore more biologically inspired and complex mechanisms like advanced neuromodulation, rudimentary forms of structural plasticity/neurogenesis (as evolvable rates/rules), and meta-learning concepts.

#### Sophisticated Neuromodulation & Neuron Types

-   [ ] Evolve parameters for multiple neuromodulatory systems (e.g., "dopamine-like" for reward/plasticity gating, "serotonin-like" for mood/behavioral switching), leveraging the types in `Genome.kt`.
-   [ ] Allow activation functions evolved via GP to include temporal primitives (e.g., access to `prev_output`, or other state variables like `fatigue`, `membranePotential` from `NeuronState`) to more easily capture bursting, adapting, or pacemaker-like behaviors directly.
-   [ ] Ensure explicit inhibitory neuron types (if not already sufficiently emerging from activation functions or `Inhibitory` type in `Genome.kt`) play a clear role.

#### Emergent Structural Dynamics (Evolvable Rates/Rules)

-   [ ] **Define Genome Elements for Structural Change Rules:**
    -   [ ] Design genome elements to encode _rates_ or _conditions_ for structural changes (synapse formation/elimination) or neuron birth/death (neurogenesis) that could occur during a conceptual "developmental" or "inter-trial" phase.
    -   [ ] This is NOT runtime changes during a single task trial, but rather how the base network for the _next_ trial (or next generation's offspring) might be modified based on evolved rules and past activity/performance.
-   [ ] Implement a "post-evaluation/pre-next-evaluation" step where these evolved rules can modify the network structure based on activity, genetically specified conditions, or fitness signals.

#### Meta-Learning & Advanced Task Design

-   [ ] **Conceptualize and Implement Meta-Learning Mechanisms:**
    -   [ ] Investigate how "internal curricula" or learning strategies could be encoded in the genome and influence how an agent tackles a complex multi-part task or adapts its learning rules.
-   [ ] Design tasks that explicitly require meta-learning (e.g., learning how to learn faster in new environments, or adapting learning strategies based on task type).
-   [ ] Track actual weight/parameter changes during adaptation phases within an individual's lifetime to verify "honest" learning (i.e., genuine plasticity at work) and potentially reward it directly or use it as a behavioral descriptor.

#### Deeper Performance Optimization & Analysis

-   [ ] Explore Java Vector API for network activation if profiling shows significant benefit and suitable patterns exist.
-   [ ] Conduct advanced JVM GC tuning (G1, ZGC/Shenandoah as appropriate for heap sizes and pause time requirements).
-   [ ] Develop more sophisticated behavioral analysis and visualization tools (e.g., plotting network activity, weight changes over time, trajectory in behavioral space).

---
