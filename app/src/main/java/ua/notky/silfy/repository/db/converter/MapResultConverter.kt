package ua.notky.silfy.repository.db.converter

import androidx.room.TypeConverter
import ua.notky.base.util.fromJsonToMap
import ua.notky.base.util.toJsonString
import ua.notky.silfy.models.model.ResultTraining
import ua.notky.silfy.repository.db.adapter.ResultTrainingAdapter

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 25.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class MapResultConverter {

    @TypeConverter
    fun toJson(value: Map<Int, ResultTraining>): String {
        return value.toJsonString(ResultTrainingAdapter())
    }

    @TypeConverter
    fun fromJson(value: String): Map<Int, ResultTraining> {
        return value.fromJsonToMap(ResultTrainingAdapter()) ?: HashMap()
    }
}