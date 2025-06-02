# Generic Python Experiments Project Template

## User Story
As a researcher, I want a standardized Python project template for experiments so that I can quickly start new experiments with consistent structure, tooling, and reproducible workflows.

## Context
This template will standardize experiment setup within the `experiments/` directory, replacing ad-hoc scaffolds (e.g., old `prototypes/`). It consolidates directory layout, tooling, and documentation for reproducible, modular experiments.

## Acceptance Criteria

### Project Structure
- Create a standardized directory layout in the `experiments/` directory:
  - `src/`: Source code for experiments

### Development Environment
- Include `requirements.txt` and `requirements.lock` for dependency management
- Provide instructions for creating and activating a virtual environment using `uv`
- Set up CI configuration (GitHub Actions) for:
  - Linting
  - Testing
  - Environment setup

### Core Functionality
- Provide a sample `main.py` entrypoint with:
  - CLI argument parsing using `argparse`
  - Logging configuration boilerplate
  - Seed management via CLI flags or config files
- Support modular experiment structure:
  - Allow adding new experiments as modules under `src/`
  - Each experiment should be self-contained and reproducible

### Documentation
- Include template documentation:
  - `README.md`: Project overview and setup instructions
  - `CONTRIBUTING.md`: Guidelines for adding new experiments
- Document reproducibility requirements:
  - Seed management
  - Configuration handling
  - Output directory structure 