# AI Workflow Process

## Folder Structure
- Create a folder named `tasks` in the project root
- Within `tasks`, create the subfolders: `backlog`, `in-progress`, `done`, `context`

## Task Lifecycle
1. **Prioritization**:
   * The `Task Priority Queue` section below lists backlog tasks in their intended order of execution.
   * The task at the top of the queue is the next candidate for work.
   * This queue should be reviewed and can be reordered as project needs evolve or new dependencies are identified.

2. When a new feature or user story arises:
   - Create a file under `tasks/backlog` named `<brief-title>.md`
   - Begin the file with the line:
     USER STORY: <Brief Title>
   - On the next line, write:
     As a <role>, I want <feature> so that <benefit>
   - Add any raw notes or links to specifications below the user-story line
   - Add the new task to the `Task Priority Queue` section below, with an assigned priority.

3. When the **top task from the `Task Priority Queue`** is ready for work:
   * Remove its entry from the `Task Priority Queue`.
   * Add its entry to the `In-Progress Tasks` list.
   * Move `<brief-title>.md` from `tasks/backlog` to `tasks/in-progress`
   - At the very top of `<brief-title>.md`, add the following sections:
     Context:
     Acceptance Criteria: (Plain language, business-facing success conditions)
     *(The USER STORY, As a..., Context, and Acceptance Criteria are the primary content of this file)*
   - Optionally, within the story file, add a `Sub-Tasks:` section to list and track smaller work items.
   - In the `tasks/in-progress` directory, create two files:

4. When implementation and tests all pass (all acceptance and unit tests are passing):
   - Commit changes locally with an imperative commit message such as "Add <feature>"
   - Move `<brief-title>.md` from `tasks/in-progress` to `tasks/done`
   - Remove its entry from the `In-Progress Tasks` list.
   - Add its entry to the `Recently Completed Tasks` list.
   - At the end of the `tasks/done/<brief-title>.md` file, add:
     Completed: <username>

5. After moving to `tasks/done`:
   - Delete or archive files in `tasks/backlog`, `tasks/in-progress`, and `tasks/done` as desired to reset the tasks folder.
   - If tasks are archived/deleted from `tasks/done`, update the `Recently Completed Tasks` list accordingly.
   - Always keep `tasks.md` and files under `tasks/context` for future reference.

---

## Task Priority Queue
*(The tasks are listed in priority order; remove the top item when starting work; manual renumbering is no longer required.)*

- `initial_simple_tasks_suite.md`
- `generic_input_interface.md`
- `research_task_suite_taxonomy.md`
- `research_sensor_actuator_taxonomy.md`
- `research_evolving_senses_and_sensor_masks.md`
- `research_evolving_actuators_and_actuator_masks.md`
- `research_specialist_vs_generalist_tradeoff.md`
- `research_cost_model.md`
- `research_budgeted_internal_computation.md`
- `research_curriculum_rule.md`
- `draft_genome_schema_with_sensor_actuator_masks.md`
- `curriculum_task_difficulty_framework.md`
- `time_budgeted_recurrent_loop.md`
- `damage_resilience_challenge.md`
- `fix_network_activation_propagation.md`
- `verify_project_structure.md`
- `verify_testing_framework.md`
- `verify_version_control.md`
- `migrate_logging_to_slf4j.md`
- `remove_old_network_files.md`
- `audit_domain_class_placement.md`
- `ensure_genome_activationfunction_use.md`
- `refine_neuron_dataclass.md`
- `refine_connection_dataclass.md`
- `refine_genome_dataclass_serialization.md`
- `implement_hebbian_plasticity.md`
- `implement_simplepipelinerunner.md`
- `refine_neuralnetwork_class.md`
- `refine_evaluation_loop_and_task.md`
- `refine_simpleevolution_loop.md`
- `refine_evolutiondemo_logging.md`
- `develop_genome_inspection_tools.md`
- `explore_meta_encoding.md`
- `performance_profiling_benchmarking.md`
- `containerization_deployment_planning.md`
- `parallel_distributed_evaluation_exploration.md`

---

## In-Progress Tasks
*(List of tasks currently in `tasks/in-progress/<brief-title>.md`. Tasks are moved here from the Priority Queue.)*
*(This list should be updated when a task's status changes.)*

*(No tasks currently in progress)*

---

## Recently Completed Tasks
*(List of tasks moved to `tasks/done/<brief-title>.md`. This list can be periodically cleared or tasks archived from `tasks/done`.)*
*(This list should be updated when a task's status changes or when completed tasks are archived/deleted.)*

- `generic_experiments_project_template.md`

---
