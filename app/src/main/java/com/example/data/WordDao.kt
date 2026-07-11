package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM words ORDER BY kanji ASC")
    fun getAllWordsFlow(): Flow<List<Word>>

    @Query("SELECT * FROM words WHERE id = :id")
    suspend fun getWordById(id: Int): Word?

    @Query("SELECT * FROM words WHERE jlptLevel = :level ORDER BY kanji ASC")
    fun getWordsByLevel(level: String): Flow<List<Word>>

    @Query("SELECT * FROM words WHERE bookmarked = 1 ORDER BY kanji ASC")
    fun getBookmarkedWords(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: List<Word>)

    @Update
    suspend fun updateWord(word: Word)

    @Query("UPDATE words SET bookmarked = :bookmarked WHERE id = :id")
    suspend fun updateBookmark(id: Int, bookmarked: Boolean)

    @Query("UPDATE words SET learned = :learned WHERE id = :id")
    suspend fun updateLearned(id: Int, learned: Boolean)

    @Query("SELECT COUNT(*) FROM words")
    suspend fun getWordCount(): Int
}
