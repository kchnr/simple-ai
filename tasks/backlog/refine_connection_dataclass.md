USER STORY: Refine Connection Data Class
As a developer, I want to review and refine the `Connection` data class in `Genome.kt`, so that it accurately models synaptic properties for the MVP.

Raw Notes:
- Review and refine `Connection` data class in `Genome.kt` (source neuron ID, target neuron ID, weight, delay, plasticity flags, plasticity type, innovation ID).
- Confirm the Hebbian-like rule (`delta_w = eta * pre_activation * post_activation`) is the primary target for initial plasticity, where `eta` is analogous to `learningRate` on the `Neuron` or a property of the `Connection`.
- Clarify how `eta` is represented and evolved.
- Consider how short-term plasticity dynamics might emerge or be approximated through evolved parameters or rules. 