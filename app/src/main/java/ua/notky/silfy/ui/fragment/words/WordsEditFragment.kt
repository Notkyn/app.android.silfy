package ua.notky.silfy.ui.fragment.words

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.databinding.FragmentWordsEditBinding
import ua.notky.silfy.viewmodel.StateViewModel
import ua.notky.silfy.viewmodel.words.WordsEditViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 23.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class WordsEditFragment : BaseBindingFragment<FragmentWordsEditBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWordsEditBinding
        get() = FragmentWordsEditBinding::inflate

    private val stateViewModel by activityViewModels<StateViewModel>()
    private val wordsEditViewModel by activityViewModels<WordsEditViewModel>()

    override fun init() {}

    override fun buildViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(wordsEditViewModel)
            .build()
    }

    override fun initViews() {
        binding.state = stateViewModel.stateModel
        binding.model = wordsEditViewModel.model

        binding.editWord.setTargetForCleanFocus(binding.inputWord)
        binding.editWord.setNextTargetView(binding.editTranslate)
        binding.editTranslate.setTargetForCleanFocus(binding.inputTranslate)
    }

    override fun initListeners() {
        binding.includeHeader.buttonBack.setOnClickListener { goToBack() }

        binding.buttonWordState.setOnClickListener {
            wordsEditViewModel.onChangeWordState()
        }

        binding.textWordState.setOnClickListener {
            wordsEditViewModel.onChangeWordState()
        }

        binding.buttonSave.setOnClickListener { goToBack() }
    }

    override fun initViewModels() {
        stateViewModel.updateEditable(wordsEditViewModel.isNewWord())
    }

    private fun goToBack() {
        findNavController().popBackStack()
    }
}