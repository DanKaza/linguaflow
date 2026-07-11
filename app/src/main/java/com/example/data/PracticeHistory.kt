package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "practice_history")
data class PracticeHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val score: Int,
    val category: String,
    val timestamp: Long = System.currentTimeMillis()
)
