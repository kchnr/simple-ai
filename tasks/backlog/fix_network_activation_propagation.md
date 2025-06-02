USER STORY: Verify and Fix Network Activation Propagation
As a developer, I want to verify network activation propagation in `NeuralNetwork.kt` and fix the neuron update order, so that networks of various topologies are processed correctly.

Raw Notes:
- Verify network activation propagation in `NeuralNetwork.kt` (`step` function, topological processing).
  - **P0: CRITICAL FIX** - Address "Warning: Some neurons could not be processed" from `runEvolutionDemo`. Implement a more robust neuron update order in `NeuralNetwork.step()` to handle various network topologies correctly.
  - Test with feed-forward, recurrent, and self-connections. 