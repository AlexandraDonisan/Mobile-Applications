package en.ubb.trippie_kotlin

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface WordDao{
    @Query("Select * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): LiveData<List<Word>>

    @Query("Select COUNT(*) FROM word_table")
    fun getSize(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("Select * FROM word_table WHERE localId = :id")
    fun getSightseeing(id: Int): Word

    @Query("Select * FROM word_table WHERE word = :name")
    fun getSightseeingByName(name: String): Word

    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteElement(word: Word)

//    @Query("UPDATE word_table SET word = newWord WHERE word = :word")
//    suspend fun incrementUserAge(word: Word, newWord : String)

    @Update
    suspend fun updateName(word: Word)
}

//DAOs must be either interfaces or abstract classes
//suspend fun insert(word: Word) : Declares a suspend function to insert one word.
//onConflict = OnConflictStrategy.IGNORE: The conflict strategy ignores a new word
//              if it's exactly the same as one already in the list