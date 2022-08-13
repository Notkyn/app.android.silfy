package ua.notky.silfy.models.model

import ua.notky.silfy.models.states.WordState

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 13.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class DictionaryByStateInfo(
    val state: WordState,
    val count: Int
)