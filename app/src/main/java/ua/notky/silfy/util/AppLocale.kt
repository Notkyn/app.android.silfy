package ua.notky.silfy.util

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import ua.notky.silfy.models.enums.AppLanguage

/**
 * UI language of the app = language of the active profile.
 * Without a profile the app follows the system language (falls back to the default resources).
 * Changing it recreates the running activities.
 */
object AppLocale {

    fun apply(language: AppLanguage) {
        if (AppCompatDelegate.getApplicationLocales().toLanguageTags() != language.code) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language.code))
        }
    }

    fun reset() {
        if (!AppCompatDelegate.getApplicationLocales().isEmpty) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.getEmptyLocaleList())
        }
    }

    fun isSet(): Boolean = !AppCompatDelegate.getApplicationLocales().isEmpty
}
