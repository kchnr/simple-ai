USER STORY: Validate Adaptation Fitness Function
As a researcher, I want to conduct and document Experiment 3.1 (Validating the "Adaptation Bonus" Fitness Function), so that we can confirm its effectiveness in selecting for adaptive agents.

Raw Notes:
- Goal: Test the proposed fitness function: `Fitness = (Accuracy in 2nd phase) - (Accuracy in 1st phase) + bonus for overall accuracy`. Does it effectively select for agents that adapt?
- Setup: Simple task (e.g., predict a constant, then it changes). Simple evolving agent.
- Compare with a simpler fitness function (e.g., just average accuracy across both phases).
- Metrics: Percentage of evolved agents that successfully adapt vs. those that find a compromise or only learn one phase.
- Relevance: Directly informs `refine_fitness_function_for_adaptation.md`.
- Documentation: Use `experiments/experiment_readme_template.md`. 