package ua.notky.silfy.usecase.profile

import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.repository.prefs.AppDataStorePreferences
import ua.notky.silfy.util.AppLocale
import javax.inject.Inject

/**
 * Single place to switch the active profile: stores its id and switches the app language to the
 * profile language. Use it instead of writing the profile id to [AppDataStorePreferences] directly.
 */
class ActiveProfileUseCase @Inject constructor(
    private val dataStore: AppDataStorePreferences
) {

    suspend fun set(profile: Profile) {
        val id = profile.id ?: throw IllegalStateException("Profile is not saved [$profile]")

        dataStore.setProfileId(id)
        AppLocale.apply(profile.language)
    }

    suspend fun clear() {
        dataStore.removeProfileId()
        AppLocale.reset()
    }
}
