package ua.notky.silfy.repository.db.dao.word

import androidx.room.*
import ua.notky.silfy.models.local.WordLocal
import ua.notky.silfy.models.local.cross.WordWithCategories

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
@Dao
interface WordDao {

    @Query("SELECT * FROM word WHERE user_id = :userId")
    suspend fun getAll(userId: Int): List<WordLocal>

    @Query("SELECT * FROM word WHERE user_id = :userId AND favourite = :isFavourite")
    suspend fun getAllFavourites(userId: Int, isFavourite: Boolean = true): List<WordLocal>

    @Query("SELECT * FROM word WHERE user_id = :userId AND black = :isBlack")
    suspend fun getAllBlacks(userId: Int, isBlack: Boolean = true): List<WordLocal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: List<WordLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: WordLocal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(word: WordLocal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAll(words: List<WordLocal>)

    @Query("SELECT * FROM word WHERE en = :en AND user_id = :userId")
    suspend fun getOne(en: String, userId: Int): WordLocal?

    @Query("DELETE FROM word WHERE user_id = :userId")
    suspend fun clearAll(userId: Int)

    @Query("DELETE FROM word WHERE word_id = :wordId AND user_id = :userId")
    suspend fun remove(wordId: Int, userId: Int)

    @Transaction
    suspend fun replaceAll(userId: Int, words: List<WordLocal>) {
        clearAll(userId)
        insertAll(words)
    }

    @Transaction
    @Query("SELECT * FROM word WHERE word_id = :id AND user_id = :userId")
    suspend fun getOneWithCategories(id: Int, userId: Int): WordWithCategories?

    @Transaction
    @Query("SELECT * FROM word WHERE en = :en AND user_id = :userId")
    suspend fun getOneWithCategories(en: String, userId: Int): WordWithCategories?
}