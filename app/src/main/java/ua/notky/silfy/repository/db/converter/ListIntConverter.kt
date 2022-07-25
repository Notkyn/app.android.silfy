package ua.notky.silfy.repository.db.converter

import androidx.room.TypeConverter
import ua.notky.base.util.fromJsonToObjects
import ua.notky.base.util.toJsonString

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 25.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class ListIntConverter {

    @TypeConverter
    fun toJson(values: List<Int>): String {
        return values.toJsonString()
    }

    @TypeConverter
    fun fromJson(value: String): List<Int> {
        return value.fromJsonToObjects() ?: listOf()
    }
}