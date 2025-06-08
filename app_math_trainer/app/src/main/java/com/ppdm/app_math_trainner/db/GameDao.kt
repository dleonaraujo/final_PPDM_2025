package com.ppdm.app_math_trainner.db

import androidx.room.*

@Dao
interface GameDao {
    @Insert
    suspend fun insert(result: GameResult)

    @Query("SELECT * FROM game_results ORDER BY dateTime DESC")
    suspend fun getAll(): List<GameResult>

    @Query("SELECT AVG(score) FROM game_results")
    suspend fun getAverageScore(): Double

    @Query("SELECT COUNT(*) FROM game_results")
    suspend fun getTotalGames(): Int
}
