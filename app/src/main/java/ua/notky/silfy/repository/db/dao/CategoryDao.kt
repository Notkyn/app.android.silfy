package ua.notky.silfy.repository.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ua.notky.silfy.models.local.CategoryLocal
import ua.notky.silfy.models.local.cross.CategoryWithWords

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(categories: List<CategoryLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: CategoryLocal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(category: CategoryLocal)

    @Query("DELETE FROM category WHERE user_id = :userId")
    suspend fun clearAll(userId: Int)

    @Query("DELETE FROM category WHERE category_id = :categoryId AND user_id = :userId")
    suspend fun remove(categoryId: Int, userId: Int)

    @Transaction
    suspend fun replaceAll(userId: Int, categories: List<CategoryLocal>) {
        clearAll(userId)
        insertAll(categories)
    }

    @Transaction
    @Query("SELECT * FROM category WHERE category_id = :id AND user_id = :userId")
    fun getCategoryWithWords(id: Int, userId: Int?): LiveData<CategoryWithWords?>

    @Query("SELECT * FROM category WHERE user_id = :userId")
    suspend fun getAll(userId: Int): List<CategoryLocal>

    @Transaction
    @Query("SELECT * FROM category WHERE user_id = :userId AND category_id NOT IN (:filter)")
    fun getCategoriesWithWordsByLiveData(
        userId: Int?,
        filter: List<Int>
    ): LiveData<List<CategoryWithWords>>
}