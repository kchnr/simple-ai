USER STORY: Synaptic Pruning Threshold Experiment
As a researcher, I want to evaluate the effect of various synaptic pruning thresholds on network performance so that I can determine optimal pruning strategies for different task complexities.

Raw Notes:
- Pruning thresholds to test: 0.01, 0.05, 0.1, 0.2, 0.5
- Tasks to use: SINGLE_PAIR, MULTI_PAIR, BINARY_CLASS
- Metrics: training accuracy, validation accuracy, weight statistics (zero count, magnitude distribution)
- Procedure: initial training → prune → retrain → evaluate → analyze statistics
- Use the modular `src/` framework for automation and logging 