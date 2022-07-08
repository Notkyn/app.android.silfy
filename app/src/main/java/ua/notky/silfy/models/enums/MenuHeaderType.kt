package ua.notky.silfy.models.enums

import ua.notky.silfy.R

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 26.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
enum class MenuHeaderType(val title: Int) {
    PROFILE(R.string.button_profile),
    DICTIONARY(R.string.button_dictionary),
    TRAINING(R.string.button_training),
    GO(R.string.text_settings)
}