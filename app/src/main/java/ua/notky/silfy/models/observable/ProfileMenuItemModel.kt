package ua.notky.silfy.models.observable

import ua.notky.silfy.models.model.Profile

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 01.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

data class ProfileMenuItemModel(
    val profile: Profile,
    val currentUserId: Int?
) {
    fun isActive() = currentUserId == profile.id
}