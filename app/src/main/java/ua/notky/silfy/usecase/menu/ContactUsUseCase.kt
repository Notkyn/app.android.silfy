package ua.notky.silfy.usecase.menu

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import ua.notky.silfy.BuildConfig
import ua.notky.silfy.R
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 12.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

/**
 * 6a Contact us: an email draft with the app version. Texts come from [context] (an activity),
 * so they are in the profile language.
 */
class ContactUsUseCase @Inject constructor() {

    fun send(context: Context) {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(URI_MAILTO)
            putExtra(Intent.EXTRA_EMAIL, arrayOf(EMAIL_CONTACT))
            putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.menu_contact_subject))
            putExtra(Intent.EXTRA_TEXT, createBodyText(context))
        }

        try {
            context.startActivity(emailIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(context, R.string.menu_contact_no_app, Toast.LENGTH_SHORT).show()
        }
    }

    private fun createBodyText(context: Context): String {
        return "\n\n\n\n\n" + context.getString(R.string.menu_contact_version, BuildConfig.VERSION_NAME) + "\n"
    }

    private companion object {
        const val URI_MAILTO = "mailto:"
        const val EMAIL_CONTACT = "sylfy.app@gmail.com"
    }
}
