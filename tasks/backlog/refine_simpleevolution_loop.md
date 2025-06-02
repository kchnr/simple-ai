USER STORY: Refine SimpleEvolution Loop
As a developer, I want to review and refine the `SimpleEvolution.kt` Genetic Algorithm loop, including mutation operators and population management, so that the EA is robust for MVP.

Raw Notes:
- Review and refine the `SimpleEvolution.kt` Genetic Algorithm loop:
  - Population initialization (current random genome creation is a good start).
  - Fitness evaluation (delegates to `SequencePredictionTask.kt`).
  - Selection (current tournament selection is acceptable).
  - Refine mutation operators:
    - Mutate connection weights.
    - Mutate plasticity parameters (e.g., `learningRate` on Neurons).
    - Mutate neuron bias and threshold.
    - Add/remove connection (ensure innovation IDs are handled if NEAT-like concepts are planned for later).
    - Add/remove neuron (ensure connections are handled, and innovation IDs).
  - Defer crossover for initial MVP to keep it simple, or ensure the current basic crossover in `SimpleEvolution.kt` is sufficiently robust for initial tests.
- Review and improve basic population management in `SimpleEvolution.kt`.
- Add tests for key EA components like mutation and selection. 