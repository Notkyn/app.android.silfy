package ua.notky.silfy.models.states

import ua.notky.silfy.R

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
enum class SortState(val image: Int) {
    UNKNOWN(R.drawable.ic_sort_state_unknown),
    EXCELLENT(R.drawable.ic_sort_state_excellent),
    DISABLE(R.drawable.ic_sort_state_disable)
}