USER STORY: Implement SimplePipelineRunner
As a developer, I want to implement `SimplePipelineRunner` to wire existing EA components into a cohesive pipeline, so that evolution can be run end-to-end.

Raw Notes:
- Implement `SimplePipelineRunner` wiring existing `SimpleEvolution`, `NeuralNetwork`, and `SequencePredictionTask` into a cohesive pipeline.
- Write an integration test ensuring a trivial genome goes through the pipeline and returns a new population. 