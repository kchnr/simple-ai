# Future Considerations & Advanced Research (Post-Local CPU Validation)

**Goal:** Document ambitious long-term ideas that go beyond initial local CPU validation. These are not immediate tasks but are preserved for future exploration if the project shows significant promise.

## Scalability & Distributed Computing

-   [ ] Distributed Evolution (Island Model):
    -   Migration strategies (e.g., via S3/DynamoDB checkpoints for spot instance resilience).
    -   Frameworks: Spark, Ray, or AWS Batch/ECS depending on evaluation granularity.
-   [ ] GPU Acceleration:
    -   Offload large batch evaluations or specific computations (e.g., using CSR mapping to cuSparse if CSR is implemented).

## Highly Complex Emergent Phenomena

-   [ ] True runtime neurogenesis and structural plasticity (continuous modification of neuron count and connections during task execution based on evolved rules).
-   [ ] Evolution of complex modularity and hierarchical structures (building on `NetworkModule` concepts).
-   [ ] Advanced multi-task learning and transfer learning across diverse domains.

## Advanced Tooling & Experimentation

-   [ ] GraalVM native-image for specific components or smaller, GC-less experiments.
-   [ ] Comprehensive CI/CD pipelines for experimentation.
-   [ ] Public-facing results, datasets, and evolved model repositories. 