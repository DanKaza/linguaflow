package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PracticeHistoryDao {
    @Query("SELECT * FROM practice_history ORDER BY timestamp DESC")
    fun getAllHistoryFlow(): Flow<List<PracticeHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: PracticeHistory)

    @Query("DELETE FROM practice_history")
    suspend fun clearHistory()
}
