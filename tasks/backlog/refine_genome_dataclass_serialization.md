USER STORY: Refine Genome Data Class
As a developer, I want to review and refine the `Genome` data class, so that it is simplified for MVP and ready for robust serialization.

Raw Notes:
- Review and refine `Genome` data class (`neurons`, `connections`, `neuromodulators`, `modules`, `resourceBudgets`, `innovationHistory`, `generation`, `parentIds`).
- Simplify if possible for MVP, deferring complex parts like `modules` if not strictly needed.
- Implement robust serialization/deserialization for `Genome` (e.g., to JSON using kotlinx.serialization, as already partially set up) for saving/loading populations and for debugging.
  - Add tests for genome serialization and deserialization. 