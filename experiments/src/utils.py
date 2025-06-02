import matplotlib.pyplot as plt
import numpy as np
from typing import List, Dict, Any


def plot_accuracy_vs_prune(results: List[Dict[str, Any]]):
    """Plot accuracy vs pruning percentage from results list."""
    prune_perc = [r['pruned_percentage'] for r in results]
    accuracies = [r['accuracy'] for r in results]
    plt.figure()
    plt.plot(prune_perc, accuracies, 'o-')
    plt.xlabel('Pruned %')
    plt.ylabel('Accuracy')
    plt.grid(True)
    plt.show()


def plot_weight_matrix(weights: np.ndarray):
    """Display a weight matrix as a heatmap."""
    plt.figure()
    plt.imshow(weights, cmap='RdBu', aspect='auto')
    plt.colorbar()
    plt.xlabel('Input neuron')
    plt.ylabel('Output neuron')
    plt.show() 