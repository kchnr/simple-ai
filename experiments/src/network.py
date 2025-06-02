import numpy as np
import logging

class HebbianNetwork:
    """A Hebbian network that can train, prune, and evaluate patterns."""
    def __init__(self, input_size: int, output_size: int, learning_rate: float):
        """Initialize network parameters and weights."""
        self.input_size = input_size
        self.output_size = output_size
        self.learning_rate = learning_rate
        self.weights = np.random.uniform(-0.1, 0.1, (output_size, input_size))
        self.mask = np.ones_like(self.weights)

    def train(self, inputs: np.ndarray, targets: np.ndarray, steps: int, early_stop: bool = True) -> float:
        """Train on given inputs/targets for a number of steps, return final accuracy."""
        best_accuracy = 0.0
        no_improvement_count = 0
        max_no_improvement = 50  # Early stopping patience

        for step in range(steps):
            # Forward pass
            outputs = np.dot(inputs, self.weights.T)
            predictions = (outputs > 0.5).astype(int)
            
            # Calculate accuracy
            accuracy = np.mean(predictions == targets)
            
            # Early stopping check
            if early_stop:
                if accuracy > best_accuracy:
                    best_accuracy = accuracy
                    no_improvement_count = 0
                else:
                    no_improvement_count += 1
                
                if no_improvement_count >= max_no_improvement:
                    logging.info(f"Early stopping at step {step} with accuracy {accuracy:.3f}")
                    break
            
            # Hebbian learning rule: Δw = η * (y * x^T)
            # where η is learning rate, y is target, x is input
            weight_updates = np.zeros_like(self.weights)
            for i in range(len(inputs)):
                weight_updates += self.learning_rate * np.outer(targets[i], inputs[i])
            self.weights += weight_updates * self.mask  # Apply mask to maintain pruning

            if step % 100 == 0:
                logging.info(f"Step {step}: accuracy = {accuracy:.3f}")

        return accuracy

    def prune(self, threshold: float) -> float:
        """Prune weights below threshold; return fraction pruned."""
        # Count weights below threshold before pruning
        below_threshold = np.abs(self.weights) < threshold
        pruned_count = np.sum(below_threshold)
        total_weights = self.weights.size
        
        # Update mask to zero out pruned weights
        self.mask[below_threshold] = 0
        
        # Zero out pruned weights
        self.weights[below_threshold] = 0
        
        pruned_fraction = pruned_count / total_weights
        logging.info(f"Pruned {pruned_count}/{total_weights} weights ({pruned_fraction:.1%})")
        
        return pruned_fraction

    def evaluate(self, inputs: np.ndarray, targets: np.ndarray) -> float:
        """Evaluate accuracy for given inputs and targets."""
        outputs = np.dot(inputs, self.weights.T)
        predictions = (outputs > 0.5).astype(int)
        accuracy = np.mean(predictions == targets)
        return accuracy

    def analyze(self) -> dict:
        """Return statistics on weights (zero count, magnitude distribution)."""
        active_weights = self.weights[self.mask == 1]
        zero_count = np.sum(self.mask == 0)
        total_weights = self.weights.size
        
        stats = {
            'zero_count': zero_count,
            'zero_percentage': zero_count / total_weights,
            'mean_magnitude': np.mean(np.abs(active_weights)) if len(active_weights) > 0 else 0,
            'std_magnitude': np.std(active_weights) if len(active_weights) > 0 else 0,
            'min_magnitude': np.min(np.abs(active_weights)) if len(active_weights) > 0 else 0,
            'max_magnitude': np.max(np.abs(active_weights)) if len(active_weights) > 0 else 0
        }
        
        return stats 