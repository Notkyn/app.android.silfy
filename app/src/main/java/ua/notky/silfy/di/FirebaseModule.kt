package ua.notky.silfy.di

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.notky.silfy.BuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    private const val INTERVAL_FETCHING_MINIMAL = 10800L // 3 hours
    const val KEY_VERSION_CODE = "version_code"

    @Singleton
    @Provides
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(INTERVAL_FETCHING_MINIMAL)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(getDefaultsValue())
        return remoteConfig
    }

    private fun getDefaultsValue(): Map<String, Any> {
        return hashMapOf(
            KEY_VERSION_CODE to BuildConfig.VERSION_CODE
        )
    }
}