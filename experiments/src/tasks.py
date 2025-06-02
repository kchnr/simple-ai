from typing import List, Tuple
from enum import Enum
from dataclasses import dataclass
import numpy as np

class TaskType(Enum):
    SINGLE_PAIR = "single_pair"
    MULTI_PAIR = "multi_pair"
    BINARY_CLASS = "binary_class"

@dataclass
class Task:
    type: TaskType
    train_inputs: np.ndarray
    train_targets: np.ndarray
    val_inputs: np.ndarray
    val_targets: np.ndarray

def generate_task(task_type: TaskType, input_size: int, output_size: int, num_pairs: int = 4) -> Task:
    """Generate training and validation data for a specific task type."""
    # Generate full dataset
    patterns: List[Tuple[np.ndarray, np.ndarray]]
    if task_type == TaskType.SINGLE_PAIR:
        # Create multiple copies of the same pattern for training
        base_pattern = (np.array([1, 0, 1, 0, 1, 0, 1, 0]), np.array([0, 1, 0, 1, 0, 1, 0, 1]))
        patterns = [base_pattern] * num_pairs  # Create num_pairs copies
    elif task_type == TaskType.MULTI_PAIR:
        patterns = []
        for _ in range(num_pairs):
            inp = np.random.choice([0,1], size=input_size)
            tgt = np.random.choice([0,1], size=output_size)
            patterns.append((inp, tgt))
    else:  # BINARY_CLASS
        # Generate two classes based on parity of ones
        patterns = []
        # Class 0: even parity
        while len(patterns) < num_pairs:
            p = np.random.choice([0,1], size=input_size)
            if p.sum() % 2 == 0:
                patterns.append((p, np.array([0])))
        # Class 1: odd parity
        count = 0
        while count < num_pairs:
            p = np.random.choice([0,1], size=input_size)
            if p.sum() % 2 == 1:
                patterns.append((p, np.array([1])))
                count += 1

    # Split into train/validation (80/20)
    split = int(0.8 * len(patterns))
    train = patterns[:split]
    val = patterns[split:]

    train_inputs = np.stack([t[0] for t in train])
    train_targets = np.stack([t[1] for t in train])
    val_inputs = np.stack([v[0] for v in val]) if val else train_inputs
    val_targets = np.stack([v[1] for v in val]) if val else train_targets

    return Task(type=task_type, train_inputs=train_inputs, train_targets=train_targets,
                val_inputs=val_inputs, val_targets=val_targets) 