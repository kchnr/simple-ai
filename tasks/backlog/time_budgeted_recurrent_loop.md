USER STORY: Time-Budgeted Recurrent Loop Task
As a Developer, I want to implement a time-budgeted recurrent loop evaluation so that brains can trade compute time for solution refinement, forcing selection for internal looping and memory.

- Introduce a cycle budget per evaluation
- Allow the brain to run multiple internal update cycles before producing an output
- Score based on solution quality minus a penalty for cycles used 