USER STORY: Activation Function Primitives Impact
As a researcher, I want to conduct and document Experiment 2.1 (Impact of Activation Function Primitives), so that we understand their influence on evolvability for non-linear tasks.

Raw Notes:
- Goal: Test the impact of different subsets of activation function primitives on evolvability.
- Setup: Simple GP setup (e.g., DEAP). Task: XOR or simple sequence memorization.
- Compare evolution with subsets: a) linear + sigmoid, b) +Tanh, ReLU, c) +Sin, Exp, d) +If.
- Metrics: Speed of convergence, complexity of evolved functions, best fitness.
- Relevance: Informs Phase 2 "Advanced Genome & Activation Function Evolution" and GP framework selection.
- Documentation: Use `experiments/experiment_readme_template.md`. 