package com.ppdm.app_math_trainner.util

import kotlin.math.pow
import kotlin.random.Random

object MathGenerator {

    fun generateQuestion(): Pair<String, Double> {
        val numOperands = (2..3).random()  // 2 o 3 operandos

        // Generar operadores permitiendo como máximo una sola potencia
        val operators = mutableListOf<String>()
        var powerUsed = false

        repeat(numOperands - 1) {
            val availableOps = if (powerUsed) listOf("+", "-", "×", "/") else listOf("+", "-", "×", "/", "^")
            val op = availableOps.random()
            if (op == "^") powerUsed = true
            operators.add(op)
        }

        // Generar números respetando la potencia
        val numbers = mutableListOf<Double>()
        for (i in 0 until numOperands) {
            if (i > 0 && operators[i - 1] == "^") {
                numbers.add((2..3).random().toDouble())  // Exponentes de 2 a 3
            } else {
                numbers.add((1..10).random().toDouble())
            }
        }

        // Formar la expresión como texto
        val expression = buildString {
            append(numbers[0].toInt())
            for (i in operators.indices) {
                append(" ${operators[i]} ${numbers[i + 1].toInt()}")
            }
        }

        // Evaluar la expresión y redondear
        val result = evaluate(numbers, operators)
        val rounded = String.format("%.2f", result).toDouble()

        return expression to rounded
    }

    private fun evaluate(nums: List<Double>, ops: List<String>): Double {
        val values = nums.toMutableList()
        val operators = ops.toMutableList()
        val precedence = listOf("^", "×", "/", "+", "-")

        for (op in precedence) {
            var i = 0
            while (i < operators.size) {
                if (operators[i] == op) {
                    val a = values[i]
                    val b = values[i + 1]
                    val result = when (op) {
                        "^" -> a.pow(b)
                        "×" -> a * b
                        "/" -> a / b
                        "+" -> a + b
                        "-" -> a - b
                        else -> 0.0
                    }
                    values[i] = result
                    values.removeAt(i + 1)
                    operators.removeAt(i)
                } else {
                    i++
                }
            }
        }

        return values.first()
    }
}
