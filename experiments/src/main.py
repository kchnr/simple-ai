from tasks import TaskType, generate_task
from network import HebbianNetwork
import logging
from typing import List, Dict, Any

logging.basicConfig(level=logging.INFO)

def run_experiment(task_type: TaskType, input_size: int, output_size: int, 
                  learning_rate: float, pruning_thresholds: List[float]) -> List[Dict[str, Any]]:
    """Run a single experiment with given parameters."""
    logging.info(f"\nStarting task: {task_type.value}")
    
    # Generate task data
    task = generate_task(task_type, input_size, output_size)
    network = HebbianNetwork(input_size, output_size, learning_rate)

    # Initial training
    train_acc = network.train(task.train_inputs, task.train_targets, steps=1000)
    val_acc = network.evaluate(task.val_inputs, task.val_targets)
    logging.info(f"Initial training accuracy: {train_acc:.3f}")
    logging.info(f"Initial validation accuracy: {val_acc:.3f}")

    # Analyze initial weights
    initial_stats = network.analyze()
    logging.info("Initial weight statistics:")
    for k, v in initial_stats.items():
        logging.info(f"  {k}: {v:.3f}")

    # Prune and evaluate
    results = []
    for thresh in pruning_thresholds:
        # Prune weights
        pruned_pct = network.prune(thresh)
        
        # Retrain after pruning
        train_acc = network.train(task.train_inputs, task.train_targets, steps=500)
        val_acc = network.evaluate(task.val_inputs, task.val_targets)
        
        # Get weight statistics
        stats = network.analyze()
        
        results.append({
            'threshold': thresh,
            'pruned_percentage': pruned_pct,
            'train_accuracy': train_acc,
            'val_accuracy': val_acc,
            'weight_stats': stats
        })
        
        logging.info(f"\nThreshold {thresh}:")
        logging.info(f"  Pruned: {pruned_pct:.1%}")
        logging.info(f"  Train accuracy: {train_acc:.3f}")
        logging.info(f"  Val accuracy: {val_acc:.3f}")
        logging.info("  Weight statistics:")
        for k, v in stats.items():
            logging.info(f"    {k}: {v:.3f}")

    return results

def main():
    # Configuration
    input_size = 8
    output_size = 8
    learning_rate = 0.1
    pruning_thresholds = [0.01, 0.05, 0.1, 0.2]
    task_types = [TaskType.SINGLE_PAIR, TaskType.MULTI_PAIR, TaskType.BINARY_CLASS]

    # Run experiments for each task type
    all_results = {}
    for task_type in task_types:
        results = run_experiment(task_type, input_size, output_size, 
                               learning_rate, pruning_thresholds)
        all_results[task_type.value] = results
        
        # Plotting skipped; results stored in all_results

if __name__ == '__main__':
    main() 