package ua.notky.silfy.models.model

import ua.notky.silfy.models.enums.GoStatsType

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 24.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class TrainingSession(
    val id: String,
    val startTime: Long,
    val updateTime: Long,
    val finishReasonType: GoStatsType,
    val categoryIds: List<Int>,
    val wordIds: List<Int>,
    val resultMap: MutableMap<Int, ResultTraining>
) {
    fun putSuccess(wordId: Int) {
        val result = resultMap[wordId] ?: ResultTraining()
        result.successUp()
        resultMap[wordId] = result
    }

    fun putFailure(wordId: Int) {
        val result = resultMap[wordId] ?: ResultTraining()
        result.failureUp()
        resultMap[wordId] = result
    }
}

data class ResultTraining(
    var success: Int = 0,
    var failure: Int = 0
) {
    fun successUp() {
        success.inc()
    }

    fun failureUp() {
        success.inc()
    }
}
