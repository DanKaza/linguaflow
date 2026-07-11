package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val kanji: String,
    val reading: String,
    val translationIndonesian: String,
    val translationEnglish: String,
    val category: String, // e.g. "Kata Kerja", "Kata Benda", "Kata Sifat"
    val jlptLevel: String, // e.g. "N5", "N4", "N3"
    val bookmarked: Boolean = false,
    val learned: Boolean = false,
    val examplesJson: String, // Stored as "SentenceJp_SentenceId\nSentenceJp2_SentenceId2"
    val formsJson: String // Stored as plain text description
)
