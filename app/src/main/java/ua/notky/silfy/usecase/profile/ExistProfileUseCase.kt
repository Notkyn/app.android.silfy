package ua.notky.silfy.usecase.profile

import ua.notky.base.model.ResultState
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.repository.db.dao.ProfileDao
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class ExistProfileUseCase @Inject constructor(
    private val profileDao: ProfileDao
) {

    suspend fun check(params: Params): ResultState<Profile> {
        return try {
            if (params.email.isNullOrEmpty()) return ResultState.failureMissing("email")

            val profile = profileDao.getByEmail(params.email)

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
        val email: String?
    )
}