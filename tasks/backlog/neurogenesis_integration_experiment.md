USER STORY: Neurogenesis Integration Experiment
As a researcher, I want to simulate adult neurogenesis by adding new neurons during training and measure their integration success so that I can understand how new neurons contribute to learning performance and pruning dynamics.

Raw Notes:
- At mid-training, add N new neurons (e.g., 2, 4) to the output layer
- Initialize new weights randomly and mark them active in the mask
- Continue training and measure training and validation accuracy
- Analyze weight distribution growth for new vs. existing neurons
- Compare to baseline run without neurogenesis
- Leverage existing `src/` pipeline for reproducibility and logging 