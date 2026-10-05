package ua.notky.silfy.repository.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ua.notky.silfy.models.model.Profile

/**
 * Silfy 2.0 data model.
 *
 * - profile: email / first_name / last_name / avatar → name, language, avatar_color, photo.
 *   Existing (1.x) profiles: name = first_name, or the e-mail part before "@"; language = "uk".
 * - word: column `ua` → `translation` (the translation in the profile language);
 *   the first letter of `en` becomes capital.
 * - category: default "Спорт/Sport"-style titles → Ukrainian title only ("Спорт"),
 *   unless the user already has a category with that title.
 *
 * SQLite on minSdk 24 has no RENAME COLUMN / DROP COLUMN, so profile and word are recreated.
 */
object Migration1To2 : Migration(1, 2) {

    override fun migrate(db: SupportSQLiteDatabase) {
        migrateProfile(db)
        migrateWord(db)
        migrateDefaultCategories(db)
    }

    private fun migrateProfile(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `profile_new` (" +
                    "`_id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "`name` TEXT NOT NULL, " +
                    "`language` TEXT NOT NULL, " +
                    "`avatar_color` INTEGER NOT NULL, " +
                    "`photo` TEXT, " +
                    "`create_time` INTEGER NOT NULL)"
        )
        db.execSQL(
            "INSERT INTO `profile_new` (`_id`, `name`, `language`, `avatar_color`, `photo`, `create_time`) " +
                    "SELECT `_id`, " +
                    "CASE " +
                    "WHEN TRIM(COALESCE(`first_name`, '')) != '' THEN TRIM(`first_name`) " +
                    "WHEN INSTR(COALESCE(`email`, ''), '@') > 1 THEN SUBSTR(`email`, 1, INSTR(`email`, '@') - 1) " +
                    "WHEN TRIM(COALESCE(`email`, '')) != '' THEN TRIM(`email`) " +
                    "ELSE 'Profile' END, " +
                    "'uk', " +
                    "(ABS(`_id`) - 1) % ${Profile.AVATAR_COLORS_COUNT}, " +
                    "NULLIF(`avatar`, ''), " +
                    "COALESCE(`create_time`, CAST(strftime('%s', 'now') AS INTEGER) * 1000) " +
                    "FROM `profile`"
        )
        db.execSQL("DROP TABLE `profile`")
        db.execSQL("ALTER TABLE `profile_new` RENAME TO `profile`")
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_profile__id` ON `profile` (`_id`)")
    }

    private fun migrateWord(db: SupportSQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS `word_new` (" +
                    "`word_id` INTEGER, " +
                    "`en` TEXT NOT NULL, " +
                    "`translation` TEXT NOT NULL, " +
                    "`state` INTEGER NOT NULL, " +
                    "`min_count_state` INTEGER NOT NULL, " +
                    "`favourite` INTEGER NOT NULL, " +
                    "`black` INTEGER NOT NULL, " +
                    "`user_id` INTEGER NOT NULL, " +
                    "PRIMARY KEY(`word_id`))"
        )
        db.execSQL(
            "INSERT INTO `word_new` (`word_id`, `en`, `translation`, `state`, `min_count_state`, " +
                    "`favourite`, `black`, `user_id`) " +
                    "SELECT `word_id`, `en`, `ua`, `state`, `min_count_state`, `favourite`, `black`, `user_id` " +
                    "FROM `word`"
        )
        db.execSQL("DROP TABLE `word`")
        db.execSQL("ALTER TABLE `word_new` RENAME TO `word`")
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_word_word_id` ON `word` (`word_id`)")
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_word_en_user_id` ON `word` (`en`, `user_id`)")
        capitalizeWords(db)
    }

    /**
     * "apple" → "Apple", as Silfy 2.0 saves every word. A word stays as it is when the profile
     * already has the capitalized one (1.x allowed both "apple" and "Apple"): nothing is lost.
     */
    private fun capitalizeWords(db: SupportSQLiteDatabase) {
        val capitalized = "UPPER(SUBSTR(`word`.`en`, 1, 1)) || SUBSTR(`word`.`en`, 2)"
        db.execSQL(
            "UPDATE `word` SET `en` = $capitalized " +
                    "WHERE `en` != $capitalized AND NOT EXISTS (" +
                    "SELECT 1 FROM `word` AS `other` " +
                    "WHERE `other`.`user_id` = `word`.`user_id` AND `other`.`en` = $capitalized)"
        )
    }

    private fun migrateDefaultCategories(db: SupportSQLiteDatabase) {
        DEFAULT_CATEGORIES_V1.forEach { (oldTitle, newTitle) ->
            db.execSQL(
                "UPDATE `category` SET `title` = ? " +
                        "WHERE `title` = ? AND NOT EXISTS (" +
                        "SELECT 1 FROM `category` AS `other` " +
                        "WHERE `other`.`user_id` = `category`.`user_id` AND `other`.`title` = ?)",
                arrayOf(newTitle, oldTitle, newTitle)
            )
        }
    }

    private val DEFAULT_CATEGORIES_V1 = mapOf(
        "Спорт/Sport" to "Спорт",
        "Місто/City" to "Місто",
        "Тварини/Animals" to "Тварини",
        "Робота/Job" to "Робота",
        "Квартира/Flat" to "Квартира",
        "Їжа/Food" to "Їжа"
    )
}
