package ua.notky.silfy.repository.prefs

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

private const val PREFERENCES_NAME = "silfy_datastore_prefs"
private val Context.prefsDataStore by preferencesDataStore(name = PREFERENCES_NAME)

class AppDataStorePreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = context.prefsDataStore

    suspend fun setProfileId(id: Int) {
        prefs.edit { it[KEY_PROFILE_ID] = id.toString() }
    }

    suspend fun getProfileId(): Int? {
        return prefs.data.map { it[KEY_PROFILE_ID] }.firstOrNull()?.toInt()
    }

    suspend fun removeProfileId() {
        prefs.edit { it.remove(KEY_PROFILE_ID) }
    }

    companion object {
        private val KEY_PROFILE_ID = stringPreferencesKey("profile_id")
    }
}