package ua.notky.silfy.usecase.profile

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.tools.image.deleteByUriWithFileScheme
import ua.notky.silfy.tools.image.getScaledImage
import ua.notky.silfy.tools.image.saveToFile
import java.util.*
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 17.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class UploadProfilePhotoUseCase @Inject constructor(
    @ApplicationContext val context: Context,
    private val datastore: AppDataStorePreferences,
    private val profileDao: ProfileDao
) {

    suspend fun upload(params: Params): Result<Unit> {
        return try {
            if (params.path == null) throw IllegalStateException("Uri must be not null")

            val profileId =
                datastore.getProfileId() ?: throw IllegalStateException("Profile is missing")
            val profile =
                profileDao.getById(profileId) ?: throw IllegalStateException("Profile is missing")

            profile.avatar.deleteByUriWithFileScheme()

            val scaledImage = params.path.toUri().getScaledImage(context)

            val dirName = "${context.filesDir}$DIR_PART_PATH"
            val fileName = "$FILE_PREFIX_PATH${profileId}_${UUID.randomUUID()}$FILE_EXTENSION"

            val file = scaledImage.saveToFile(dirName, fileName)

            updateProfile(profile, Uri.fromFile(file).toString())

            Result.success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    private suspend fun updateProfile(profile: Profile, photoPath: String) {
        val newProfile = profile.copy(
            avatar = photoPath
        )

        profileDao.update(newProfile)
    }

    data class Params(
        val path: String?
    )

    companion object {
        private const val DIR_PART_PATH = "/avatars"
        private const val FILE_PREFIX_PATH = "profile_avatar_"
        private const val FILE_EXTENSION = ".jpg"
    }
}