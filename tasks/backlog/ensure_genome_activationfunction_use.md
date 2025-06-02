USER STORY: Ensure Exclusive Use of Core Data Structures
As a developer, I want to ensure all project code exclusively uses the data structures defined in `gen.ai/Genome.kt` and `gen.ai/ActivationFunction.kt`, so that the project maintains a consistent and correct data model.

Raw Notes:
- Ensure all project code exclusively uses the data structures defined in `gen.ai/Genome.kt` (e.g., `gen.ai.Neuron`, `gen.ai.Connection`) and `gen.ai/ActivationFunction.kt`.
- Update any affected imports or logic, particularly if `EvolutionDemo.kt` or tests were referencing the `core.network` versions. 