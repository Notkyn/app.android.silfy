package ua.notky.silfy.usecase.menu

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import ua.notky.silfy.BuildConfig
import ua.notky.silfy.R
import ua.notky.silfy.config.ResourceProvider
import javax.inject.Inject

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 12.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class ContactUsUseCase @Inject constructor(
    private val resourceProvider: ResourceProvider
) {

    fun send(context: Context) {
        val subject = resourceProvider.getString(R.string.email_subject)
        val errorMsg = resourceProvider.getString(R.string.error_no_email_client)

        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse(URI_MAILTO)
            putExtra(Intent.EXTRA_EMAIL, arrayOf(EMAIL_CONTACT))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, createBodyText())
        }

        try {
            context.startActivity(emailIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun createBodyText(): String {
        val appVersionText = resourceProvider.getString(R.string.email_app_version)

        return StringBuilder()
            .append("\n\n\n\n\n")
            .append("$appVersionText ${BuildConfig.VERSION_NAME}\n")
            .toString()
    }

    companion object {
        private const val URI_MAILTO = "mailto:"
        private const val EMAIL_CONTACT = "sylfy.app@gmail.com"
    }
}