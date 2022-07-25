package ua.notky.silfy.repository.db.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import ua.notky.base.util.fromJsonToObject
import ua.notky.base.util.toJsonString
import ua.notky.silfy.models.model.ResultTraining

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 25.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class ResultTrainingAdapter {

    @ToJson
    fun toJson(value: ResultTraining): String {
        return value.toJsonString()
    }

    @FromJson
    fun fromJson(value: String): ResultTraining {
        return value.fromJsonToObject() ?: ResultTraining()
    }
}