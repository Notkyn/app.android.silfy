package ua.notky.silfy.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import ua.notky.base.ui.activity.BaseBindingActivity
import ua.notky.base.ui.dialog.exstensions.doOnConfirm
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.config.ACTION_NEXT_GO
import ua.notky.silfy.databinding.ActivityGoBinding
import ua.notky.silfy.ui.dialog.go.CancelTrainingBottomsheet
import ua.notky.silfy.viewmodel.menu.TrainingSettingsViewModel

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 08.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class GoActivity : BaseBindingActivity<ActivityGoBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityGoBinding
        get() = ActivityGoBinding::inflate

    private val trainingSettingsViewModel by viewModels<TrainingSettingsViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(trainingSettingsViewModel)
            .build()
    }

    override fun setNavController(): Int {
        return binding.navHostFragment.id
    }

    override fun initialize(savedInstanceState: Bundle?) {
        super.initialize(savedInstanceState)
        trainingSettingsViewModel.setGoMode()
    }

    override fun handleActionVM(type: Int) {
        when(type) {
            ACTION_NEXT_GO -> onNextGo()
        }
    }

    override fun onBackPressed() {
        showCancelTrainingDialog()
    }

    private fun showCancelTrainingDialog() {
        val dialog = CancelTrainingBottomsheet()

        dialog.doOnConfirm { finish() }

        dialog.show(supportFragmentManager, dialog::class.java.simpleName)
    }

    private fun onNextGo() {
        mNavController.navigate(GoActivityDirections.toFragmentGo())
    }
}