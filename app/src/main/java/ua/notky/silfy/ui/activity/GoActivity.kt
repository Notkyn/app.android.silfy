package ua.notky.silfy.ui.activity

import android.view.LayoutInflater
import ua.notky.base.ui.activity.BaseBindingActivity
import ua.notky.base.ui.dialog.exstensions.doOnConfirm
import ua.notky.silfy.databinding.ActivityGoBinding
import ua.notky.silfy.ui.dialog.go.CancelTrainingBottomsheet

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 08.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class GoActivity : BaseBindingActivity<ActivityGoBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityGoBinding
        get() = ActivityGoBinding::inflate

    override fun setNavController(): Int {
        return binding.navHostFragment.id
    }

    override fun onBackPressed() {
        showCancelTrainingDialog()
    }

    private fun showCancelTrainingDialog() {
        val dialog = CancelTrainingBottomsheet()

        dialog.doOnConfirm { finish() }

        dialog.show(supportFragmentManager, dialog::class.java.simpleName)
    }
}