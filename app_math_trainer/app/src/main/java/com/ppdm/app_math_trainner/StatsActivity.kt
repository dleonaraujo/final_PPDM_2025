package com.ppdm.app_math_trainner

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ppdm.app_math_trainner.db.AppDatabase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class StatsActivity : AppCompatActivity() {
    private lateinit var tvAverageScore: TextView
    private lateinit var tvTotalGames: TextView
    private lateinit var containerHistory: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        tvAverageScore = findViewById(R.id.tvAverageScore)
        tvTotalGames = findViewById(R.id.tvTotalGames)
        containerHistory = findViewById(R.id.containerHistory)

        loadStats()
    }

    private fun loadStats() {
        val db = AppDatabase.getInstance(this)
        lifecycleScope.launch {
            val avg = db.gameDao().getAverageScore()
            val total = db.gameDao().getTotalGames()
            val history = db.gameDao().getAll()

            runOnUiThread {
                tvAverageScore.text = "Promedio: ${"%.1f".format(avg)}"
                tvTotalGames.text = "Total de partidas: $total"

                val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                history.forEach {
                    val text = TextView(this@StatsActivity)
                    val date = formatter.format(Date(it.dateTime))
                    text.text = "[$date] Puntos: ${it.score}, Correctas: ${it.correctAnswers}/${it.totalQuestions}"
                    text.setTextColor(android.graphics.Color.WHITE)
                    containerHistory.addView(text)
                }
            }
        }
    }
}
