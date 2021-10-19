package ua.notky.silfy.model.enums

import ua.notky.silfy.R

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */
enum class SortType(val image: Int) {
    FAVOURITE(R.drawable.ic_sort_favourites),
    BLACKLIST(R.drawable.ic_sort_blacklist),
    DISABLE(R.drawable.ic_sort_state_disable)
}