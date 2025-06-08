package com.ppdm.app_math_trainner.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_results")
data class GameResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dateTime: Long,
    val score: Int,
    val correctAnswers: Int,
    val totalQuestions: Int
)
