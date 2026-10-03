package ua.notky.silfy.repository.db.converter

import androidx.room.TypeConverter
import ua.notky.silfy.models.enums.AppLanguage

/** Stores [AppLanguage] as its ISO code ("uk", "pl", …) */
class AppLanguageConverter {

    @TypeConverter
    fun toCode(language: AppLanguage): String {
        return language.code
    }

    @TypeConverter
    fun fromCode(code: String): AppLanguage {
        return AppLanguage.fromCode(code) ?: AppLanguage.UK
    }
}
