package com.example.data

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "speech_history")
data class SpeechHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dateText: String,
    val timestamp: Long,
    val durationText: String,
    val targetSentence: String,
    val romaji: String,
    val overallScore: Int,
    val clarityScore: Int,
    val intonationScore: Int,
    val fluencyScore: Int,
    val feedback: String,
    val wordsEvaluations: String, // e.g. "私:2,は:2,毎日:1"
    val audioPath: String? = null
)

@Dao
interface SpeechHistoryDao {
    @Query("SELECT * FROM speech_history ORDER BY timestamp DESC")
    fun getAllHistory(): Flow<List<SpeechHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: SpeechHistory)

    @Query("DELETE FROM speech_history WHERE id = :id")
    suspend fun deleteHistory(id: Int)

    @Query("DELETE FROM speech_history")
    suspend fun deleteAllHistory()
}
