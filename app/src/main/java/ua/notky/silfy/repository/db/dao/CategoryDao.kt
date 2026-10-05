package ua.notky.silfy.repository.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ua.notky.silfy.models.local.CategoryLocal
import ua.notky.silfy.models.model.CategorySummary

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

    @Query("DELETE FROM category WHERE title = :title AND user_id = :userId")
    suspend fun remove(title: String, userId: Int)

    @Transaction
    suspend fun replaceAll(userId: Int, categories: List<CategoryLocal>) {
        clearAll(userId)
        insertAll(categories)
    }

    @Query("SELECT * FROM category WHERE category_id = :id AND user_id = :userId")
    suspend fun getById(id: Int, userId: Int): CategoryLocal?

    /** 3b Category header: null once the category is deleted */
    @Query("SELECT * FROM category WHERE category_id = :id AND user_id = :userId")
    fun getLiveDataById(id: Int, userId: Int): LiveData<CategoryLocal?>

    /** In the order of creation, as everywhere categories are listed */
    @Query("SELECT * FROM category WHERE user_id = :userId ORDER BY category_id")
    suspend fun getAll(userId: Int): List<CategoryLocal>

    /** 3a Categories grid: categories with the number of their words, in the order of creation */
    @Query(
        "SELECT c.category_id AS id, c.title AS title, COUNT(x.word_id) AS wordCount " +
                "FROM category c LEFT JOIN word_category_cross x ON x.category_id = c.category_id " +
                "WHERE c.user_id = :userId " +
                "GROUP BY c.category_id ORDER BY c.category_id"
    )
    fun getSummaries(userId: Int): LiveData<List<CategorySummary>>
}