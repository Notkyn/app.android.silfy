package ua.notky.silfy.mapper

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 20.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
interface Mapper<T> {
    fun map(input: Any): T

    fun map(input: List<Any>): List<T> {
        return input.map { this.map(it) }
    }
}