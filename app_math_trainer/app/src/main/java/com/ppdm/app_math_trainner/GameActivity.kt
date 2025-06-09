package com.ppdm.app_math_trainner

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ppdm.app_math_trainner.db.AppDatabase
import com.ppdm.app_math_trainner.db.GameResult
import com.ppdm.app_math_trainner.util.MathGenerator
import kotlinx.coroutines.launch
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    private lateinit var tvQuestion: TextView
    private lateinit var tvTimer: TextView
    private lateinit var tvScore: TextView
    private lateinit var buttons: List<Button>

    private var correctAnswer = 0.0
    private var score = 0
    private var correctCount = 0
    private var totalCount = 0

    private val totalTime = 60 * 1000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        tvQuestion = findViewById(R.id.tvQuestion)
        tvTimer = findViewById(R.id.tvTimer)
        tvScore = findViewById(R.id.tvScore)
        buttons = listOf(
            findViewById(R.id.btnOption1),
            findViewById(R.id.btnOption2),
            findViewById(R.id.btnOption3),
            findViewById(R.id.btnOption4)
        )

        startGame()
    }

    private fun startGame() {
        object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvTimer.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                saveResult()
                finish() // o mostrar un diálogo
            }
        }.start()

        loadNewQuestion()
    }

    private fun loadNewQuestion() {
        val (question, answer) = MathGenerator.generateQuestion()
        correctAnswer = answer
        tvQuestion.text = "$question = ?"

        val options = mutableSetOf<Double>()
        options.add(answer)
        while (options.size < 4) {
            val opcion = answer + Random.nextDouble(-200.0, 200.0)
            options.add(String.format("%.2f", opcion).toDouble())
        }

        val shuffled = options.shuffled()
        buttons.forEachIndexed { index, button ->
            button.text = shuffled[index].toString()
            button.setOnClickListener {
                totalCount++
                if (shuffled[index] == correctAnswer) {
                    correctCount++
                    score++
                    tvScore.text = "Puntos: $score"
                }
                loadNewQuestion()
            }
        }
    }

    private fun saveResult() {
        val db = AppDatabase.getInstance(this)
        lifecycleScope.launch {
            db.gameDao().insert(
                GameResult(
                    dateTime = System.currentTimeMillis(),
                    score = score,
                    correctAnswers = correctCount,
                    totalQuestions = totalCount
                )
            )
        }
    }
}
