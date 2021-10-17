package ua.notky.base.changeable

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

sealed class ActionMode {
    sealed class Navigate : ActionMode() {
        object ToMain : Navigate()
//        object ToAuth : Navigate()
    }
    sealed class Action : ActionMode() {
//        object IsSaved : Action()
    }
}