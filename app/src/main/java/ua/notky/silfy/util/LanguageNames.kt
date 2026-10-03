package ua.notky.silfy.util

import android.content.Context
import androidx.core.os.ConfigurationCompat
import ua.notky.silfy.models.enums.AppLanguage
import java.util.Locale

/** Locale of the current UI (the active profile language, or the system language before a profile exists) */
fun Context.uiLocale(): Locale {
    return ConfigurationCompat.getLocales(resources.configuration)[0] ?: Locale.ENGLISH
}

/** "English" in the UI language: Angielski, Englisch, Англійська… */
fun Context.englishLanguageName(): String {
    return Locale.ENGLISH.displayLanguageIn(uiLocale())
}

/** Name of [language] in the UI language: "Ukrainian" in English UI, "ukraiński" → "Ukraiński" in Polish UI */
fun Context.languageNameInUi(language: AppLanguage): String {
    return language.locale.displayLanguageIn(uiLocale())
}

private fun Locale.displayLanguageIn(ui: Locale): String {
    return getDisplayLanguage(ui).replaceFirstChar { it.titlecase(ui) }
}
