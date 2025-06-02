# Biological Neuron and Synapse State Dynamics

Below is a detailed overview of how "state" is stored and reset in biological neurons versus synapses. We'll first examine the neuron's own internal state (membrane potential, ion-channel dynamics, refractory mechanisms), then turn to synaptic state (short-term and long-term plasticity), and finally summarize how both types of state evolve and reset over time.

---

## 1. Neuronal State: Membrane Potential and Reset Mechanisms

### 1.1. What Is the Neuron's "State"?

At its core, a neuron's state is characterized by its (V_m) and the status of its voltage-gated ion channels. Concretely:

*   **Resting Membrane Potential ($\approx -65$ mV):**
    Under quiescent conditions, most neurons sit at a negative potential relative to the outside. This is maintained by the Na⁺/K⁺ ATPase pump and differential ionic permeabilities (primarily K⁺ leak channels).

*   **Synaptic Inputs as Transient Currents:**
    When presynaptic neurons fire, they release neurotransmitters into the synaptic cleft. These neurotransmitters bind to postsynaptic receptors (e.g., AMPA, NMDA, GABA$_A$), causing ion channels to open or close. That produces small, transient changes in (V_m):

    *   **Excitatory postsynaptic potentials (EPSPs):** usually mediated by Na⁺ or Ca²⁺ influx, depolarizing the membrane.
    *   **Inhibitory postsynaptic potentials (IPSPs):** usually mediated by Cl⁻ influx or K⁺ efflux, hyperpolarizing the membrane.

*   **Integration of Inputs:**
    A neuron's dendrites and soma integrate (i.e., sum) all incoming EPSPs and IPSPs in both space (different synapses) and time (different arrival times). This integration changes (V_m) on the order of milliseconds.

*   **Threshold and Action Potential Generation:**
    If the summed depolarization pushes (V_m) above a certain threshold (often around $-50$ mV), a rapid cascade of voltage-gated Na⁺ channels opens, causing a regenerative **action potential** (spike). This is a +100 mV excursion that propagates down the axon.

At any given moment, then, the neuron's "state" can be thought of as:

1.  **Instantaneous Membrane Potential** (V_m(t)).
2.  **Gating States of Ion Channels:** e.g., how many Na⁺ channels are open versus inactivated, how many K⁺ channels are open, etc.
3.  **Short-Term Intracellular Conditions:** levels of intracellular Ca²⁺ (which can matter for plasticity) and local second-messenger concentrations.

All of these factors combine to determine whether the neuron is resting, depolarizing, repolarizing, or in its refractory period.

---

### 1.2. Membrane Dynamics and Reset After a Spike

When a neuron fires, several important events happen that "reset" or reshape its internal state:

1.  **Rapid Depolarization (Upstroke):**

    *   Voltage-gated Na⁺ channels open en masse, driving (V_m) toward the Na⁺ reversal potential ($\approx +50$ mV).
    *   This is the steep rising phase of the action potential.

2.  **Repolarization (Downstroke):**

    *   Almost immediately, Na⁺ channels begin to inactivate (their inactivation gates close), and voltage-gated K⁺ channels open, allowing K⁺ efflux.
    *   (V_m) quickly returns toward the K⁺ reversal potential ($\approx -90$ mV).

3.  **Afterhyperpolarization (AHP):**

    *   Many neurons exhibit a brief period where (V_m) is even more negative than the resting potential (e.g., $-75$ mV) because K⁺ conductance remains elevated.
    *   This AHP helps enforce the "absolute refractory period" (no new spike possible).

4.  **Refractory Periods:**

    *   **Absolute Refractory Period:** Until enough voltage-gated Na⁺ channels have returned from their inactivated state to a closed–but-activatable state (often tens to a couple of hundred microseconds), the neuron cannot fire again no matter how strong the input.
    *   **Relative Refractory Period:** After the absolute window closes, the neuron remains hyperpolarized; it can fire only if a larger-than-normal depolarizing input arrives. This period may last a few milliseconds.

5.  **Return to Resting Potential:**

    *   Ion pumps (e.g., Na⁺/K⁺ ATPase) and leak channels slowly restore the original ionic gradients and bring (V_m) back to its resting value.
    *   By the end of the refractory window, the neuron is again ready to integrate new inputs from scratch.

Hence, **each spike triggers a sequence of state changes** (ion-channel kinetics and membrane-potential trajectory) that lasts on the order of 1–5 ms (depending on the neuron type). Once that transient passes, (V_m) is effectively "reset" to rest and channels are ready to respond anew.

---

### 1.3. Subthreshold Dynamics and Short-Term Memory

Even when a neuron does **not** fire a spike, its membrane potential still transiently holds memory of recent inputs:

*   If multiple EPSPs arrive in quick succession, they can **summate** to push (V_m) closer to threshold—even if no spike ultimately occurs.
*   This "subthreshold" integration decays over 10–30 ms (depending on membrane time constant (\tau_m)), so the neuron retains a short "memory" of recent synaptic events.
*   Likewise, phenomena such as **voltage-gated NMDA receptor activity** or **dendritic plateau potentials** can prolong depolarization in certain neuron types, giving them a longer timescale of integration before spike generation.

However, this subthreshold state is **ephemeral**—if no spike is generated, the membrane potential passively returns to rest on its own time constant. No permanent "activation" is stored in the neuron unless plasticity mechanisms intervene.

---

## 2. Synaptic State: Short-Term and Long-Term Plasticity

Whereas the neuron's membrane potential is a fast-changing, transient state, the synapse has its own state variables that govern how strongly a presynaptic spike influences the postsynaptic neuron. Synaptic state can be subdivided into:

*   **Short-Term Plasticity (STP):** changes that happen on the order of milliseconds to seconds (facilitation, depression).
*   **Long-Term Plasticity (LTP/LTD):** changes that happen on the order of minutes to hours (or longer), which effectively "store" learned information.

### 2.1. Short-Term Plasticity (STP)

STP refers to rapid, transient changes in synaptic efficacy that depend on the recent history of presynaptic firing. Two primary forms:

1.  **Short-Term Facilitation (STF):**

    *   If presynaptic action potentials arrive in rapid succession, residual Ca²⁺ in the presynaptic terminal can cause **increased neurotransmitter release probability** on the next spike.
    *   In other words, (P_{\text{release}}) temporarily goes up, so the postsynaptic current for the second or third spike is larger than for the first.

2.  **Short-Term Depression (STD):**

    *   Conversely, repeated presynaptic firing can **deplete readily releasable vesicle pools**. After a high-frequency burst, fewer vesicles remain docked and released, so the amplitude of subsequent EPSCs/IPSCs shrinks.
    *   Recovery from depression takes tens to hundreds of milliseconds as vesicles are recycled and refilled.

These STP mechanisms define a synapse's **dynamic gain control** on the timescale of tens to hundreds of milliseconds. Importantly:

*   STP does **not** permanently "set" the synaptic strength. Once the presynaptic firing slows or stops, residual Ca²⁺ clears and vesicle pools refill; the synapse "resets" back to its baseline efficacy.
*   From the point of view of "state," a synapse maintains variables like:

    *   (u(t)): the utilization factor (probability of release) which decays toward a baseline over (\tau_{\text{facil}}).
    *   (R(t)): the fraction of releasable vesicles; high-frequency firing pushes (R) down, which recovers on (\tau_{\text{rec}}).
*   Mathematically, classic STP models (e.g., Tsodyks–Markram) describe:

    \[
    \begin{cases}
      \Delta u = U(1 - u) \quad \text{on each spike}, \\
      \Delta R = -u R \quad \text{on each spike}, \\
      \frac{du}{dt} = -\frac{u}{\tau_{\text{facil}}}, \quad
      \frac{dR}{dt} = \frac{1 - R}{\tau_{\text{rec}}}.
    \end{cases}
    \]

    Here (U) is a baseline utilization parameter; (\tau_{\text{facil}}) and (\tau_{\text{rec}}) are the time constants for facilitation decay and vesicle recovery, respectively.

Thus, STP gives synapses a **millisecond-to-second "memory"** of recent activity that modulates how each new presynaptic spike translates into a postsynaptic current. But once activity slows, these state variables return to baseline, fully "resetting" the synapse for future events.

---

### 2.2. Long-Term Plasticity (LTP and LTD)

Long-term plasticity refers to persistent changes in synaptic strength that last from many minutes to a lifetime. These changes are the biological substrate of learning and memory. Two prototypical forms:

1.  **Long-Term Potentiation (LTP):**

    *   Typically induced by a **high-frequency ("tetanic") stimulation** of the presynaptic inputs, or by precise **spike-timing correlations** (pre before post within \~10 ms).
    *   Mechanistically, a strong depolarization of the postsynaptic neuron relieves the Mg²⁺ block from NMDA receptors; Ca²⁺ influx then triggers kinase cascades (e.g., CaMKII) that lead to:

        *   **Insertion of AMPA receptors** into the postsynaptic density (increasing postsynaptic sensitivity).
        *   **Morphological changes** (e.g., growth of dendritic spines, enlargement of active zones).
    *   Once established, LTP can last hours, days, or longer (memories are thought to rely on stable LTP).

2.  **Long-Term Depression (LTD):**

    *   Often induced by **low-frequency stimulation** (e.g., 1 Hz for many minutes), or by "anti-Hebbian" spike timing (post before pre).
    *   Lower levels of postsynaptic Ca²⁺ activate phosphatases (e.g., calcineurin), which remove AMPA receptors from the synapse or reduce presynaptic release probability.
    *   As a result, the same presynaptic spike elicits a smaller postsynaptic response. LTD can persist for a long time, balancing network excitability.

Key points about LTP/LTD as synaptic state:

*   **Slow Timescale:** Induction of LTP/LTD takes seconds to minutes, and the resulting changes can last anywhere from an hour to years.
*   **Persistent Change:** Unlike STP, which decays quickly, LTP/LTD alters the "baseline" synaptic weight (W_{ij}) by inserting/removing receptors, reorganizing scaffolding proteins, or changing vesicle release machinery.
*   **Locality and Specificity:** Each individual synapse—on each dendritic spine—can potentiate or depress independently based on its precise activity. Thus, different synapses on the same postsynaptic neuron can have very different strengths simultaneously.
*   **Resetting LTP/LTD:** In principle, LTP/LTD can be **reversed** (e.g., depotentiation, de-depression) if activity patterns change. However, these reversals also typically take minutes or more, and may involve distinct signaling cascades.

In artificial neural network terms, **LTP/LTD correspond to changes in the "weights"** (the real-valued synaptic strength parameters) rather than to the fast "activations." And whereas an ANN's weights are updated once per minibatch via backpropagation, biological synapses only update **under specific biologically "authorized" conditions** (e.g., a strong postsynaptic depolarization paired with presynaptic activity).

---

## 3. Putting It All Together: How States Reset Over Time

Below is a concise summary of the different state variables at the level of neurons and synapses, and how/when they reset.

| **State Variable**                            | **Location**         | **Timescale**           | **Reset Mechanism**                                                                                       |
| --------------------------------------------- | -------------------- | ----------------------- | --------------------------------------------------------------------------------------------------------- |
| **Membrane potential (V_m)**                  | Neuron soma          | Milliseconds            | Passive leak currents (decay back to rest), action potential repolarization, refractory gating            |
| **Voltage-gated channel gating (m, h, n)**    | Neuron membrane      | Micro- to milliseconds  | Channel inactivation/recovery kinetics (absolute/relative refractory periods)                             |
| **Subthreshold integration of EPSPs/IPSPs**   | Neuron dendrites     | Milliseconds            | Passive membrane time constant ((\tau_m)); decays toward resting potential                                |
| **Calcium concentration ([Ca^{2+}])**         | Neuron cytosol       | Milliseconds to seconds | Buffering, pumps (PMCA), exchangers (NCX), endoplasmic reticulum sequestration                            |
| **Short-Term Facilitation (utilization (u))** | Presynaptic terminal | Tens to hundreds of ms  | Ca²⁺ clearance (decay of facilitation), vesicle release, "use-dependent" recovery                         |
| **Short-Term Depression (R: vesicle pool)**   | Presynaptic terminal | Tens to hundreds of ms  | Vesicle recycling and refilling, endocytosis, replenishment of readily releasable pool                    |
| **Long-Term Potentiation (LTP strength)**     | Synaptic cleft/PSD   | Minutes to Lifelong     | Kinase activation (CaMKII, PKC), gene transcription, spine morphology changes; reversed by depotentiation |
| **Long-Term Depression (LTD strength)**       | Synaptic cleft/PSD   | Minutes to Lifelong     | Phosphatase activation (calcineurin), AMPAR endocytosis, structural spine shrinkage; reversed by LTP      |

### 3.1. Neuronal Reset (Membrane Potential)

1.  **Between Spikes (Subthreshold):**

    *   If EPSPs/IPSPs fail to push (V_m) above threshold, (V_m) passively decays back to rest with time constant (\tau_m).
    *   Ion channels that were briefly open close, and the Na⁺/K⁺ pump plus leak channels restore ionic gradients slowly.

2.  **After an Action Potential:**

    *   **Absolute refractory:** no new spike can occur until Na⁺ channels recover from inactivation.
    *   **Relative refractory:** the neuron is hyperpolarized (AHP) and requires a larger input to fire again.
    *   After \~1–5 ms, the neuron returns to baseline and can once again accumulate inputs.

### 3.2. Synaptic Reset

1.  **Short-Term Plasticity:**

    *   Each time a presynaptic spike arrives, vesicle release and residual Ca²⁺ modify (u) and (R).
    *   If presynaptic firing stops, (u) decays exponentially back to baseline on (\tau_{\text{facil}}).
    *   Vesicle pool (R) replenishes with time constant (\tau_{\text{rec}}).
    *   After \~100–500 ms, STP variables return to baseline (synapse is "reset" for fresh short-term dynamics).

2.  **Long-Term Plasticity:**

    *   When induction protocols (e.g., high-frequency tetanus or precise spike timing) occur, biochemical cascades permanently alter receptor numbers or presynaptic machinery.
    *   If induction falls below threshold or another opposing protocol arrives, LTP can be reversed (depotentiation) or LTD reversed (reinforcement).
    *   Those opposals also take minutes to hours, so synaptic weights remain largely stable during normal ongoing activity.

---

## 4. Analogies to Artificial Neural Networks

To tie this back to your original interest in artificial networks:

*   **Neuron Activation State (ANN):**

    *   In an ANN, each artificial neuron computes an output (a = \sigma(w\cdot x + b)). Those outputs are stored during the forward pass so that gradients can be computed in backpropagation.
    *   Once backpropagation is done, you discard the activations (they're not used for future inputs); the network is "reset" except for the updated weights.

*   **Synaptic Weight State (ANN):**

    *   In an ANN, the "synaptic strength" is the weight matrix (W). During training, gradients accumulate to update (W).
    *   Once training (or a training iteration) finishes, (W) is permanently changed (until the next update). There's no "short-term" synaptic dynamic unless you explicitly code something like dropout, batch normalization, or a recurrent network's hidden state.

*   **Key Differences:**

    1.  Biological neurons have a **fast, transient state** (membrane potential) that decays within milliseconds. ANNs only implicitly store activations during the forward pass, then immediately discard them once gradients are computed.
    2.  Biological synapses have **two tiers of plasticity**:

        *   STP for milliseconds–seconds (which ANNs typically ignore), and
        *   LTP/LTD for minutes–lifelong (analogous to the ANN's weight updates, but governed by local timing rules instead of global backprop).
    3.  In biological networks, you can have synapses that are temporarily "depressed" or "facilitated" for the next few spikes, even if the neuron itself does not fire. In most feedforward ANNs, synapses do not have dynamic short-term gains—each forward call always multiplies by the same weight, unless you code a custom STP layer.

Understanding these differences highlights why biological learning is both more complex (multiple time scales, local learning rules) and more robust in real environments, whereas artificial learning (gradient descent) is simpler to engineer but typically lacks fast, context-dependent synaptic dynamics.

---

## 5. Summary

1.  **Neuron State**

    *   **Membrane Potential ((V_m))** integrates incoming EPSPs/IPSPs over a few milliseconds.
    *   **Ion-Channel Gating** determines when spikes fire and enforces refractory periods (absolute/relative).
    *   **Reset:** After each action potential, (V_m) repolarizes and then hyperpolarizes, before decaying back to rest. If no spike fires, (\,V_m) passively decays on (\tau_m).

2.  **Synapse State**

    *   **Short-Term Plasticity (ms–s):** Facilitatory or depressive changes in release probability and vesicle availability. These variables recover to baseline once presynaptic firing slows.
    *   **Long-Term Plasticity (min–lifelong):** Permanent changes in receptor density or presynaptic machinery (LTP/LTD), which adjust the baseline synaptic weight. These changes require special induction protocols and can sometimes be reversed by opposing protocols.

3.  **Biological vs. Artificial Networks**

    *   Biological neurons and synapses carry both a fast, transient state (membrane potential; STP) and a slow, persistent state (synaptic weight/plasticity).
    *   ANN neurons only keep a transient "activation" during training (needed for gradient), which is immediately dropped afterward. ANN synapses (weights) only change during the backprop/optimizer update step; they do not have built-in short-term facilitation or depression unless explicitly modeled.

For a holistic understanding of "state" in real brains, always distinguish between the **fast electrical/dendritic state** (which resets in milliseconds) and the **slow biochemical/synaptic state** (which resets only under special conditions—or never, if expressed as a long-term memory). That dual time-scale architecture is one reason biological networks can both process incoming information rapidly and store learned patterns over much longer periods. 