USER STORY: Emergent STP Effects
As a researcher, I want to conduct and document Experiment 1.2 (Emergence of Short-Term Plasticity-like Effects), so that we can explore if simple local rules can produce complex temporal dynamics.

Raw Notes:
- Goal: Can evolving parameters for a single Hebbian-like rule result in behaviors resembling STP (facilitation/depression)?
- Setup: Small network. Task: Present input spikes at varying frequencies. Fitness rewards networks showing different response magnitudes to later spikes vs. first.
- Evolve: Connection weights, plasticity rule parameters, neuron biases/thresholds.
- Metrics: Synaptic weight changes and neuron output dynamics over short timescales.
- Relevance: Addresses Open Research Question on emergent STP. Informs `refine_connection_dataclass.md`.
- Documentation: Use `experiments/experiment_readme_template.md`. 