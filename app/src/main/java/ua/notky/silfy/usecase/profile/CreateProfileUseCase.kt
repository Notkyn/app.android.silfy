package ua.notky.silfy.usecase.profile

import ua.notky.base.model.ResultState
import ua.notky.silfy.models.enums.AppLanguage
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.repository.db.dao.ProfileDao
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class CreateProfileUseCase @Inject constructor(
    private val profileDao: ProfileDao
) {

    suspend fun create(params: Params): ResultState<Profile> {
        return try {
            val name = params.name?.trim()
            if (name.isNullOrEmpty()) return ResultState.failureMissing("name")

            val newProfile = Profile(
                id = null,
                name = name,
                language = params.language,
                avatarColor = profileDao.count() % Profile.AVATAR_COLORS_COUNT,
                photo = null,
                createTime = System.currentTimeMillis()
            )

            val id = profileDao.save(newProfile)
            val profile = profileDao.getById(id.toInt())

            if (profile != null) {
                ResultState.successResult(profile)
            } else {
                ResultState.successEmpty()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            ResultState.Failure(ex)
        }
    }

    data class Params(
        val name: String?,
        val language: AppLanguage
    )
}
