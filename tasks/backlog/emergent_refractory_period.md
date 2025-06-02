USER STORY: Emergent Refractory Period
As a researcher, I want to conduct and document Experiment 1.3 (Evolving "Refractory Period" like Behavior), so that we can investigate emergent temporal dynamics in single neurons.

Raw Notes:
- Goal: Can a neuron's evolved activation function (or simple state parameters) lead to a refractory-like period?
- Setup: Single neuron or small circuit. Task: Stimulate with high-frequency input. Reward if neuron shows a max firing rate or skips pulses.
- Evolve: Activation function (GP primitives) or parameters of a neuron model with simple state decay/recovery.
- Metrics: Inter-spike intervals under sustained input.
- Relevance: Addresses Open Research Question on refractory periods. Informs `refine_neuron_dataclass.md`.
- Documentation: Use `experiments/experiment_readme_template.md`. 