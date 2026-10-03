package ua.notky.silfy.models.observable

import androidx.databinding.ObservableField

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class ProfileModel(
    val name: ObservableField<String> = ObservableField(""),
    val language: ObservableField<String> = ObservableField(""),
    val avatar: ObservableField<String> = ObservableField(""),
    val createTime: ObservableField<Long> = ObservableField(0)
)