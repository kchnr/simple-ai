USER STORY: Generic Input Interface
As a Developer, I want a generic, task-agnostic input interface so that any environment can connect inputs/outputs to the brain uniformly without manual wiring.

- Design a simple protocol (e.g., flat vector of floats) for brain I/O
- Implement an abstraction layer that maps task-specific data into this I/O format
- Ensure extendability for new sensors/actuators with minimal code changes 