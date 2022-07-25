package ua.notky.silfy.ui.fragment.go

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.extension.observe
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentGoBinding
import ua.notky.silfy.models.model.Word
import ua.notky.silfy.models.observable.answer.WordAnswerSelectModel
import ua.notky.silfy.models.states.GoUiState
import ua.notky.silfy.viewmodel.StateViewModel
import ua.notky.silfy.viewmodel.go.GoViewModel
import ua.notky.silfy.viewmodel.menu.TrainingSettingsViewModel

/**
 * @project Silfy
 * @company 4K-Soft
 * @author Evgeniy Zarechnyi on 08.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class GoFragment : BaseBindingFragment<FragmentGoBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGoBinding
        get() = FragmentGoBinding::inflate

    private val goViewModel by activityViewModels<GoViewModel>()
    private val settingsViewModel by activityViewModels<TrainingSettingsViewModel>()
    private val stateViewModel by activityViewModels<StateViewModel>()

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(goViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.model = goViewModel.model
        binding.state = stateViewModel.state
        binding.answerWriteModel = goViewModel.writeAnswerModel
        binding.answerSymbolModel = goViewModel.symbolAnswerModel
    }

    override fun initializeViewModels() {
        goViewModel.initializeDifficult(settingsViewModel.model.difficult.get())
        goViewModel.initializeErrors(
            settingsViewModel.model.enableErrors.get(),
            settingsViewModel.model.countErrors.get()
        )
        goViewModel.initializeWords(
            settingsViewModel.model.selectWords.get(),
            settingsViewModel.model.enableUseBlackList.get(),
            settingsViewModel.categories.value
        )

        observe(goViewModel.answerWords, ::renderAnswerWords)
        observe(goViewModel.answerSymbolWord, ::renderAnswerSymbolWord)
        observe(goViewModel.uiState, ::renderUiState)
    }

    override fun initializeListeners() {
        binding.header.handleCancelClick { activity?.onBackPressed() }
        binding.footer.handleNextClick { goViewModel.onNext() }

        binding.answerSelectLayout.handleClick { goViewModel.onCheckResult(it) }
        binding.answerWriteLayout.handleAnswer { goViewModel.onCheckResult() }

        binding.answerSymbolLayout.handleAnswer { goViewModel.onCheckResult() }
        binding.answerSymbolLayout.handleAddSymbolClick { goViewModel.onAddSymbolAnswer(it) }
        binding.answerSymbolLayout.handleDeleteSymbolClick { goViewModel.onDeleteSymbolAnswer(it) }
    }

    private fun renderAnswerWords(words: List<WordAnswerSelectModel>?) {
        words?.let { binding.answerSelectLayout.setWords(it) }
    }

    private fun renderAnswerSymbolWord(word: Word?) {
        word?.let { binding.answerSymbolLayout.setWord(it) }
    }

    private fun renderUiState(state: GoUiState?) {
        stateViewModel.setLoading(state == GoUiState.Loading)

        when (state) {
            GoUiState.Loaded -> {
                goViewModel.isStarted = true
                goViewModel.initializeTimer(settingsViewModel.model.duration.get()?.seconds)
                goViewModel.start()
                goViewModel.clearState()
            }
            GoUiState.Failure -> {
                activity?.onBackPressed()
                goViewModel.clearState()
            }
            else -> {}
        }
    }
}