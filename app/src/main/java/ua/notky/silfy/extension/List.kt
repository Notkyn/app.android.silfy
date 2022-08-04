package ua.notky.silfy.extension

/**
 * @project Silfy
 * @author Evgeniy Zarechnyi on 07.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

fun <T> List<T>?.compareNullable(value: List<T>?): Boolean {
    return when {
        !this.isNullOrEmpty() && value.isNullOrEmpty() -> false
        this.isNullOrEmpty() && !value.isNullOrEmpty() -> false
        !this.isNullOrEmpty() && !value.isNullOrEmpty() -> this.compare(value)
        else -> true
    }
}

fun <T> List<T>.compare(value: List<T>): Boolean {
    if (this.size != value.size) return false

    for (i in this.indices) {
        if (this[i] != value[i]) return false
    }

    return true
}