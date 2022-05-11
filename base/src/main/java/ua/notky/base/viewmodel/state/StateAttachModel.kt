package ua.notky.base.viewmodel.state

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 11.05.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
interface StateAttachModel<T> {
    fun setState(state: T)
}