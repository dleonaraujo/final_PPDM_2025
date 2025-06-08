package com.ppdm.app_math_trainner.util

object MathGenerator {
    fun generateQuestion(): Pair<String, Int> {
        val a = (1..10).random()
        val b = (1..10).random()
        val op = listOf("+", "-", "×").random()
        val question = "$a $op $b"
        val answer = when (op) {
            "+" -> a + b
            "-" -> a - b
            "×" -> a * b
            else -> 0
        }
        return question to answer
    }
}
