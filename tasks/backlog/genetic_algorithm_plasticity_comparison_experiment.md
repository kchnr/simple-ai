USER STORY: Genetic Algorithm vs Hebbian Plasticity Comparison Experiment
As a researcher, I want to compare a genetic algorithm–driven network optimization to Hebbian learning–based adaptation so that I can evaluate which approach yields better performance, convergence speed, and pruning resilience.

Raw Notes:
- Implement GA evolving network mask and weights (mutation: flip mask bits, perturb weights)
- Use tasks: SINGLE_PAIR, MULTI_PAIR, BINARY_CLASS under identical conditions
- Evaluate metrics: convergence steps, final training and validation accuracy, pruning tolerance
- Integrate GA into current `src/` framework (e.g., use DEAP or custom implementation)
- Compare results side by side with pure Hebbian plasticity experiments 