package ua.notky.silfy.usecase.profile

import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class UpdateProfileUseCase @Inject constructor(
    private val profileDao: ProfileDao,
    private val dataStore: AppDataStorePreferences
) {
    suspend fun update(params: Params): Result<Unit> {
        return try {
            val userId =
                dataStore.getProfileId() ?: throw IllegalStateException("Profile Id is Missing")
            val profile =
                profileDao.getById(userId) ?: throw IllegalStateException("Profile is Missing")

            val name = params.name?.trim()
            if (name.isNullOrEmpty()) throw IllegalArgumentException("Name is empty")

            val newProfile = profile.copy(
                name = name,
                avatarColor = params.avatarColor ?: profile.avatarColor
            )

            profileDao.update(newProfile)

            Result.success(Unit)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    data class Params(
        val name: String?,
        val avatarColor: Int? = null
    )
}