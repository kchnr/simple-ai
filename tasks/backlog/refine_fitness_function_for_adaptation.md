USER STORY: Refine Fitness Function for Adaptation
As a developer, I want to refine the fitness function in `SequencePredictionTask.kt` to strongly reward adaptation, so that evolution selects for networks that can change their behavior in dynamic environments.

Raw Notes:
- Refine the fitness function in `SequencePredictionTask.kt` to strongly reward adaptation:
  - Evaluate performance in the first phase (before change) and second phase (after change).
  - Fitness = (Accuracy in 2nd phase) - (Accuracy in 1st phase) + bonus for overall accuracy.
  - Alternative: Fitness = Accuracy in 2nd phase _if_ 1st phase was also solved to a minimum degree. 