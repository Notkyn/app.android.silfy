package ua.notky.silfy.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.ui.activity.BaseBindingActivity
import ua.notky.base.ui.dialog.exstensions.doOnConfirm
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.config.ACTION_EMPTY_WORDS
import ua.notky.silfy.config.ACTION_MAX_ERRORS
import ua.notky.silfy.config.ACTION_NEXT_GO
import ua.notky.silfy.config.ACTION_TIME_LEFT
import ua.notky.silfy.databinding.ActivityGoBinding
import ua.notky.silfy.models.enums.GoStatsType
import ua.notky.silfy.ui.dialog.go.GoStatsBottomsheet
import ua.notky.silfy.extension.showAlert
import ua.notky.silfy.extension.showSimpleAlert
import ua.notky.silfy.viewmodel.go.GoViewModel
import ua.notky.silfy.viewmodel.menu.TrainingSettingsViewModel

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 08.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@AndroidEntryPoint
class GoActivity : BaseBindingActivity<ActivityGoBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityGoBinding
        get() = ActivityGoBinding::inflate

    private val trainingSettingsViewModel by viewModels<TrainingSettingsViewModel>()
    private val goViewModel by viewModels<GoViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(trainingSettingsViewModel)
            .addViewModel(goViewModel)
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
        when (type) {
            ACTION_NEXT_GO -> onNextGo()
            ACTION_TIME_LEFT -> showResultDialog(GoStatsType.TIME)
            ACTION_MAX_ERRORS -> showResultDialog(GoStatsType.ERROR)
            ACTION_EMPTY_WORDS -> showEmptyWordsAlertMessage()
        }
    }

    private fun showEmptyWordsAlertMessage() {
        showSimpleAlert(getString(R.string.alert_title_select_words_for_training_is_empty)) {
            onNavFinish()
        }
    }

    override fun onBackPressed() {
        if (goViewModel.isStarted) {
            showCancelTrainingAlert()
        } else {
            onNavFinish()
        }
    }

    private fun showCancelTrainingAlert() {
        this.showAlert(
            title = getString(R.string.alert_title_cancel_training),
            onSuccess = {
                if (goViewModel.isStarted) {
                    showResultDialog(GoStatsType.OTHER)
                } else {
                    onNavFinish()
                }
            }
        )
    }

    private fun showResultDialog(type: GoStatsType) {
        goViewModel.onFinishTrainingSession(type)

        val dialog = GoStatsBottomsheet(type)

        dialog.doOnConfirm { onNavFinish() }

        dialog.show(supportFragmentManager, dialog::class.java.simpleName)
    }

    private fun onNavFinish() {
        finish()
        overridePendingTransition(android.R.anim.fade_in, ua.notky.base.R.anim.slide_out_bottom)
    }

    private fun onNextGo() {
        mNavController.navigate(R.id.to_fragmentGo)
    }
}