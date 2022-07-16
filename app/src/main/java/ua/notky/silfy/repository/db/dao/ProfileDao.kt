package ua.notky.silfy.repository.db.dao

import androidx.room.*
import ua.notky.silfy.models.model.Profile

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(profile: Profile)

    @Query("DELETE FROM profile WHERE _id = :id")
    suspend fun deleteById(id: String?)

    @Query("SELECT * FROM profile WHERE _id = :id")
    suspend fun getById(id: String): Profile?

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(profile: Profile)

    @Query("SELECT * FROM profile ORDER BY create_time DESC")
    suspend fun getAll(): List<Profile>

    @Query("SELECT count(*) FROM profile WHERE _id = :id")
    suspend fun countById(id: String): Int?

    @Query("SELECT * FROM profile WHERE email = :email")
    suspend fun getByEmail(email: String): Profile?
}