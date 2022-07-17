package ua.notky.silfy.usecase.profile

import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.tools.image.deleteByUriWithFileScheme
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class DeleteProfileUseCase @Inject constructor(
    private val profileDao: ProfileDao
) {

    suspend fun delete(params: Params): Result<Unit> {
        return try {
            if (params.profile.id == null) throw IllegalStateException("Profile Id is empty")

            params.profile.avatar.deleteByUriWithFileScheme()

            profileDao.deleteById(params.profile.id)

            Result.success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    data class Params(
        val profile: Profile
    )
}