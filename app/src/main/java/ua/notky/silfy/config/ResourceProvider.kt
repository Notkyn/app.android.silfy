package ua.notky.silfy.config

import android.content.Context
import android.content.res.Resources
import androidx.annotation.StringRes

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 12.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class ResourceProvider(
    private val context: Context,
) {

    fun getString(@StringRes resId: Int) = context.resources.getString(resId)

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String =
        context.resources.getString(resId, *formatArgs)

    fun getResources(): Resources = context.resources
}