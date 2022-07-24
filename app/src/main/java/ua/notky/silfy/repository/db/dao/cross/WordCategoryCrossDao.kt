package ua.notky.silfy.repository.db.dao.cross

import androidx.room.*
import ua.notky.silfy.models.local.cross.WordCategoryCrossRef

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Dao
interface WordCategoryCrossDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cross: WordCategoryCrossRef)

    @Query("DELETE FROM word_category_cross WHERE word_id = :wordId AND user_id = :userId")
    suspend fun deleteByWord(wordId: Int, userId: Int)

    @Query("DELETE FROM word_category_cross WHERE category_id = :categoryId AND user_id = :userId")
    suspend fun deleteByCategory(categoryId: Int, userId: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(cross: List<WordCategoryCrossRef>)

    @Query("DELETE FROM word_category_cross WHERE user_id = :userId")
    suspend fun clearAll(userId: Int)

    @Transaction
    suspend fun replaceAll(userId: Int, cross: List<WordCategoryCrossRef>) {
        clearAll(userId)
        insertAll(cross)
    }
}