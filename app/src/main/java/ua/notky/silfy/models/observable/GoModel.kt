package ua.notky.silfy.models.observable

import androidx.databinding.ObservableField

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 08.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class GoModel(
    var maxTime: Long = 0,
    val currentTime: ObservableField<Long> = ObservableField(0),
    val maxError: ObservableField<Int> = ObservableField(0),
    val currentError: ObservableField<Int> = ObservableField(0)
)