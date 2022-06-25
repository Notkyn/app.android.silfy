package ua.notky.silfy.ui.adapter.diffutil

import ua.notky.base.ui.adapter.diffutils.BaseDiffUtilCallback
import ua.notky.silfy.models.model.Profile

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 26.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class ProfileDiffUtil : BaseDiffUtilCallback<Profile>() {
    override fun areItemsTheSame(oldItem: Profile, newItem: Profile): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Profile, newItem: Profile): Boolean {
        return oldItem.id == newItem.id
                && oldItem.avatar == newItem.avatar
                && oldItem.email == newItem.email
                && oldItem.firstName == newItem.firstName
                && oldItem.lastName == newItem.lastName
                && oldItem.createTime == newItem.createTime
    }
}