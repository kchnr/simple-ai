# Prototyping & Experimentation Strategy

To facilitate both rapid exploration of novel concepts and robust implementation of core functionalities, the project will utilize a primary Kotlin codebase alongside a dedicated prototyping environment.

## `experiments` Directory (Small experiments/experiments)

-   **Location:** An `experiments/` directory within the project root.
-   **Purpose:** Quick prototyping and validation of complex or high-risk ideas, such as new plasticity rules, neurogenesis mechanisms, neuromodulation effects, advanced evolutionary operators, or alternative genome encodings.
-   **Technology:** Any that helps with faster prototyping, for example leveraging libraries like: DEAP, NEAT-Python, NumPy, SciPy, and other relevant machine learning/scientific computing packages.
-   **Workflow:** Experiments conducted in this directory aim to quickly prove out the viability or characteristics of a concept.
-   **Documentation:** Findings, key parameters, and successful patterns from experiments must be documented. Summaries and conclusions should be integrated into the main project documentation (e.g., relevant context files or new backlog items) for the main Kotlin implementation.

## Main Application (Kotlin/JVM in `app/`)

-   **Location:** The standard `app/src/main/kotlin/` directory for the primary codebase.
-   **Purpose:** The official, robust implementation of the BrainScale project.
-   **Technology:** Kotlin on the JVM, using Gradle for builds. Libraries such as Jenetics or ECJ may be considered for enhancing the evolutionary algorithm framework.
-   **Characteristics:** Strongly typed, modular, optimized for CPU performance (initially), and thoroughly tested using Kotest.

## Workflow: Experiments to Main Application

1.  Hypothesize and explore new mechanisms or approaches within the `experiments/` Python environment.
2.  Document findings and successful patterns thoroughly.
3.  If an idea proves effective and aligns with project goals, design and implement it robustly within the main Kotlin system in `app/`. This typically involves creating new tasks or refining existing ones in `tasks/backlog/`. 