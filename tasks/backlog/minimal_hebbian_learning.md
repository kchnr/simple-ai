USER STORY: Minimal Hebbian Learning
As a researcher, I want to conduct and document Experiment 1.1 (Minimal Hebbian Learning for Pattern Association), so that we can validate basic plasticity mechanisms and inform the Kotlin implementation.

Context:
This task involves implementing and documenting a minimal Hebbian learning experiment to validate basic plasticity mechanisms. The experiment will inform the Kotlin implementation of neural plasticity.
The goal is to validate that a simple Hebbian rule can enable a minimal network to learn a simple association.
This task is relevant to `implement_hebbian_plasticity.md`.
The implementation will be documented using `experiments/experiment_readme_template.md`.

Acceptance Criteria:
1. The network should learn to output pattern B when presented with input pattern A after training.
2. After learning the association, presenting pattern A multiple times should consistently yield pattern B as output.
3. Examination of network weights after learning should show changes reflecting the learned A-B association.
4. The speed of learning should vary with different learning rate configurations, but the final learned association should be similar.

