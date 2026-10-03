package ua.notky.silfy.models.enums

import java.util.Locale

/**
 * Translation language of a profile. Chosen once when the profile is created and can't be changed.
 * It is also the UI language of the app while the profile is active.
 *
 * @param code ISO 639-1 code: stored in the DB, key in assets JSON, app locale tag.
 * @param badge short label shown in the UI (UA, PL, …).
 */
enum class AppLanguage(
    val code: String,
    val badge: String,
    val nativeName: String,
    val englishName: String
) {
    UK("uk", "UA", "Українська", "Ukrainian"),
    PL("pl", "PL", "Polski", "Polish"),
    ES("es", "ES", "Español", "Spanish"),
    DE("de", "DE", "Deutsch", "German"),
    IT("it", "IT", "Italiano", "Italian"),
    PT("pt", "PT", "Português", "Portuguese"),
    FR("fr", "FR", "Français", "French");

    val locale: Locale get() = Locale(code)

    companion object {
        fun fromCode(code: String?): AppLanguage? = values().firstOrNull { it.code == code }
    }
}
