package ua.notky.silfy.ui.adapter.diffutil

import ua.notky.base.ui.adapter.diffutils.BaseDiffUtilCallback
import ua.notky.silfy.models.observable.ProfileMenuItemModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 02.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class ProfileMenuDiffUtil : BaseDiffUtilCallback<ProfileMenuItemModel>() {
    override fun areItemsTheSame(
        oldItem: ProfileMenuItemModel,
        newItem: ProfileMenuItemModel
    ): Boolean {
        return oldItem.profile.id == newItem.profile.id
    }

    override fun areContentsTheSame(
        oldItem: ProfileMenuItemModel,
        newItem: ProfileMenuItemModel
    ): Boolean {
        return oldItem.profile.id == newItem.profile.id
                && oldItem.profile.avatar == newItem.profile.avatar
                && oldItem.profile.email == newItem.profile.email
                && oldItem.profile.firstName == newItem.profile.firstName
                && oldItem.profile.lastName == newItem.profile.lastName
                && oldItem.profile.createTime == newItem.profile.createTime
                && oldItem.currentUserId == newItem.currentUserId
    }
}