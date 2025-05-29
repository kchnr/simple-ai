/*
 * ActivationFunctionTest.kt
 *
 * Purpose:
 *   Tests the ActivationFunction AST defined in ActivationFunction.kt.
 *   Ensures correct depth calculation and structure for various activation function trees.
 *
 * Related tasks.md items:
 *   - "Formalize primitive grammar and AST depth limit for neuron activation functions"
 *
 * See tasks.md for full project context and requirements.
 */
package gen.ai

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ActivationFunctionTest : StringSpec({
    "Const and Variable depth should be 1" {
        val c = ActivationFunction.Const(1.0)
        val v = ActivationFunction.Variable("input0")
        c.depth shouldBe 1
        v.depth shouldBe 1
    }

    "Simple binary operation depth should be 2" {
        val add = ActivationFunction.Add(
            ActivationFunction.Const(1.0),
            ActivationFunction.Variable("input0")
        )
        add.depth shouldBe 2
    }

    "Nested operations depth calculation" {
        val expr = ActivationFunction.Mul(
            ActivationFunction.Add(
                ActivationFunction.Const(1.0),
                ActivationFunction.Variable("input0")
            ),
            ActivationFunction.Exp(
                ActivationFunction.Variable("input1")
            )
        )
        // Mul( Add(1, input0), Exp(input1) )
        // Add and Exp are both depth 2, so Mul is 1 + max(2,2) = 3
        expr.depth shouldBe 3
    }

    "If operation depth calculation" {
        val expr = ActivationFunction.If(
            condition = ActivationFunction.Variable("cond"),
            ifTrue = ActivationFunction.Const(1.0),
            ifFalse = ActivationFunction.Add(
                ActivationFunction.Const(2.0),
                ActivationFunction.Variable("input0")
            )
        )
        // ifFalse is Add (depth 2), others are depth 1, so If is 1 + max(1,1,2) = 3
        expr.depth shouldBe 3
    }
}) 