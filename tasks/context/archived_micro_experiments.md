# Archived Micro-Experiment Tasks

These micro-experiment user stories have been removed from the backlog but are retained here for reference.

---

## Emergent STP Effects

USER STORY: Emergent STP Effects
As a researcher, I want to conduct and document Experiment 1.2 (Emergence of Short-Term Plasticity-like Effects), so that we can explore if simple local rules can produce complex temporal dynamics.

Raw Notes:
- Goal: Can evolving parameters for a single Hebbian-like rule result in behaviors resembling STP (facilitation/depression)?
- Setup: Small network. Task: Present input spikes at varying frequencies. Fitness rewards networks showing different response magnitudes to later spikes vs. first.
- Evolve: Connection weights, plasticity rule parameters, neuron biases/thresholds.
- Metrics: Synaptic weight changes and neuron output dynamics over short timescales.
- Relevance: Addresses Open Research Question on emergent STP. Informs `refine_connection_dataclass.md`.
- Documentation: Use `experiments/experiment_readme_template.md`.

---

## Emergent Refractory Period

USER STORY: Emergent Refractory Period
As a researcher, I want to conduct and document Experiment 1.3 (Evolving "Refractory Period" like Behavior), so that we can investigate emergent temporal dynamics in single neurons.

Raw Notes:
- Goal: Can a neuron's evolved activation function (or simple state parameters) lead to a refractory-like period?
- Setup: Single neuron or small circuit. Task: Stimulate with high-frequency input. Reward if neuron shows a max firing rate or skips pulses.
- Evolve: Activation function (GP primitives) or parameters of a neuron model with simple state decay/recovery.
- Metrics: Inter-spike intervals under sustained input.
- Relevance: Addresses Open Research Question on refractory periods. Informs `refine_neuron_dataclass.md`.
- Documentation: Use `experiments/experiment_readme_template.md`.

---

## Activation Function Primitives Impact

USER STORY: Activation Function Primitives Impact
As a researcher, I want to conduct and document Experiment 2.1 (Impact of Activation Function Primitives), so that we understand their influence on evolvability for non-linear tasks.

Raw Notes:
- Goal: Test the impact of different subsets of activation function primitives on evolvability.
- Setup: Simple GP setup (e.g., DEAP). Task: XOR or simple sequence memorization.
- Compare evolution with subsets: a) linear + sigmoid, b) +Tanh, ReLU, c) +Sin, Exp, d) +If.
- Metrics: Speed of convergence, complexity of evolved functions, best fitness.
- Relevance: Informs Phase 2 "Advanced Genome & Activation Function Evolution" and GP framework selection.
- Documentation: Use `experiments/experiment_readme_template.md`.

---

## Minimal Neuromodulation Effect

USER STORY: Minimal Neuromodulation Effect
As a researcher, I want to conduct and document Experiment 2.2 (Minimal Viable Neuromodulation Effect), so that we can validate basic mechanisms for modulatory influences.

Raw Notes:
- Goal: Can a simple "modulatory" input effectively gate plasticity or alter network excitability in an evolvable way?
- Setup: Small network with Hebbian plasticity, add a "modulatory input" signal.
- Evolve: How this modulatory input affects learning rate (`eta`) or activation thresholds.
- Task: Learn an association only when the modulatory signal is ON.
- Metrics: Difference in learning speed/behavior with modulator ON vs. OFF.
- Relevance: Early validation for Phase 2 "Enhanced Plasticity & Basic Neuromodulation."
- Documentation: Use `experiments/experiment_readme_template.md`.

---

## Validate Adaptation Fitness Function

USER STORY: Validate Adaptation Fitness Function
As a researcher, I want to conduct and document Experiment 3.1 (Validating the "Adaptation Bonus" Fitness Function), so that we can confirm its effectiveness in selecting for adaptive agents.

Raw Notes:
- Goal: Test the proposed fitness function: `Fitness = (Accuracy in 2nd phase) - (Accuracy in 1st phase) + bonus for overall accuracy`. Does it effectively select for agents that adapt?
- Setup: Simple task (e.g., predict a constant, then it changes). Simple evolving agent.
- Compare with a simpler fitness function (e.g., just average accuracy across both phases).
- Metrics: Percentage of evolved agents that successfully adapt vs. those that find a compromise or only learn one phase.
- Relevance: Directly informs `refine_fitness_function_for_adaptation.md`.
- Documentation: Use `experiments/experiment_readme_template.md`.

---

## Minimal Task for Structural Change

USER STORY: Minimal Task for Structural Change
As a researcher, I want to conduct and document Experiment 3.2 (Minimal Task for Structural Change Benefit), so that we can explore task designs that could drive structural evolution.

Raw Notes:
- Goal: Design a minimal task where adding/removing a connection *during an extended evaluation period* would be clearly beneficial.
- Setup: Task - e.g., input A predicts output X; later, input B also becomes relevant, and a new connection from B would be useful.
- Evolve: Rules/probabilities for adding connections based on (simulated) activity or error signals.
- Metrics: Do networks that successfully add the beneficial connection outperform those that don't?
- Relevance: Informs Phase 3 "Emergent Structural Dynamics."
- Documentation: Use `experiments/experiment_readme_template.md`.

---

## Synaptic Pruning Threshold Experiment

USER STORY: Synaptic Pruning Threshold Experiment
As a researcher, I want to evaluate the effect of various synaptic pruning thresholds on network performance so that I can determine optimal pruning strategies for different task complexities.

Raw Notes:
- Pruning thresholds to test: 0.01, 0.05, 0.1, 0.2, 0.5
- Tasks to use: SINGLE_PAIR, MULTI_PAIR, BINARY_CLASS
- Metrics: training accuracy, validation accuracy, weight statistics (zero count, magnitude distribution)
- Procedure: initial training → prune → retrain → evaluate → analyze statistics
- Use the modular `src/` framework for automation and logging.

---

## Neurogenesis Integration Experiment

USER STORY: Neurogenesis Integration Experiment
As a researcher, I want to simulate adult neurogenesis by adding new neurons during training and measure their integration success so that I can understand how new neurons contribute to learning performance and pruning dynamics.

Raw Notes:
- At mid-training, add N new neurons (e.g., 2, 4) to the output layer
- Initialize new weights randomly and mark them active in the mask
- Continue training and measure training and validation accuracy
- Analyze weight distribution growth for new vs. existing neurons
- Compare to baseline run without neurogenesis
- Leverage existing `src/` pipeline for reproducibility and logging.

---

## Genetic Algorithm vs Hebbian Plasticity Comparison Experiment

USER STORY: Genetic Algorithm vs Hebbian Plasticity Comparison Experiment
As a researcher, I want to compare a genetic algorithm–driven network optimization to Hebbian learning–based adaptation so that I can evaluate which approach yields better performance, convergence speed, and pruning resilience.

Raw Notes:
- Implement GA evolving network mask and weights (mutation: flip mask bits, perturb weights)
- Use tasks: SINGLE_PAIR, MULTI_PAIR, BINARY_CLASS under identical conditions
- Evaluate metrics: convergence steps, final training and validation accuracy, pruning tolerance
- Integrate GA into current `src/` framework (e.g., use DEAP or custom implementation)
- Compare results side by side with pure Hebbian plasticity experiments. 