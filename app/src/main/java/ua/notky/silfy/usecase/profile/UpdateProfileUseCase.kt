package ua.notky.silfy.usecase.profile

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.notky.silfy.repository.db.dao.ProfileDao
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.tools.image.deleteByUriWithFileScheme
import ua.notky.silfy.tools.image.getScaledImage
import ua.notky.silfy.util.saveToFile
import java.util.UUID
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/**
 * 5b Edit profile: name, avatar color and photo of the active profile. A new photo is scaled and copied
 * to the app files; the old photo file is deleted only after the profile is saved.
 */
class UpdateProfileUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val profileDao: ProfileDao,
    private val dataStore: AppDataStorePreferences
) {
    suspend fun update(params: Params): Result<Unit> {
        return try {
            val userId =
                dataStore.getProfileId() ?: throw IllegalStateException("Profile Id is Missing")
            val profile =
                profileDao.getById(userId) ?: throw IllegalStateException("Profile is Missing")

            val name = params.name.trim()
            if (name.isEmpty()) throw IllegalArgumentException("Name is empty")

            val photo = when (val change = params.photo) {
                PhotoChange.Keep -> profile.photo
                PhotoChange.Remove -> null
                is PhotoChange.Set -> savePhoto(userId, change.uri)
            }

            profileDao.update(profile.copy(name = name, avatarColor = params.avatarColor, photo = photo))
            if (photo != profile.photo) profile.photo.deleteByUriWithFileScheme()

            Result.success(Unit)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    /** @return `file://` uri of the scaled copy */
    private suspend fun savePhoto(userId: Int, uri: String): String = withContext(Dispatchers.IO) {
        val scaledImage = uri.toUri().getScaledImage(context)

        val dirName = "${context.filesDir}$DIR_PART_PATH"
        val fileName = "$FILE_PREFIX_PATH${userId}_${UUID.randomUUID()}$FILE_EXTENSION"

        Uri.fromFile(scaledImage.saveToFile(dirName, fileName)).toString()
    }

    sealed class PhotoChange {
        object Keep : PhotoChange()
        object Remove : PhotoChange()

        /** @param uri picked image (`content://`) */
        data class Set(val uri: String) : PhotoChange()
    }

    data class Params(
        val name: String,
        val avatarColor: Int,
        val photo: PhotoChange = PhotoChange.Keep
    )

    private companion object {
        const val DIR_PART_PATH = "/avatars"
        const val FILE_PREFIX_PATH = "profile_avatar_"
        const val FILE_EXTENSION = ".jpg"
    }
}
