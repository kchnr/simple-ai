USER STORY: Refine Evaluation Loop and Task
As a developer, I want to ensure the evaluation loop correctly processes genomes and refine `SequencePredictionTask.kt`, so that network adaptation can be effectively evaluated.

Raw Notes:
- Ensure the evaluation loop in `SimpleEvolution.kt` correctly instantiates the `NeuralNetwork` (brain) from a `Genome` (genes), evaluates it using the `SequencePredictionTask` (environment), allows for within-lifetime plasticity, and correctly calculates fitness.
- Review and refine the `SequencePredictionTask.kt` environment:
  - Task: Agent has 1 input (value at time `t`), 1 output (predicted value at `t+1`).
  - Dynamic aspect: The underlying sequence type can change, or a parameter within a sequence type can shift, requiring adaptation.
  - Example: Initially, predict a sine wave. After N steps (within one evaluation), the sine wave's frequency or amplitude changes. The network must adapt its predictions.
  - **Document Future Task: Advanced Task/Environment Design:** Note for future phases: The design of tasks and environments is critical for driving the evolution of complex adaptive behaviors. Future work will involve more diverse, dynamic, and potentially open-ended environments. 