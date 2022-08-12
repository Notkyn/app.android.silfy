package ua.notky.silfy.ui.dialog.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ua.notky.base.extension.observe
import ua.notky.base.extension.setBoldSpan
import ua.notky.base.ui.adapter.extensions.doOnRootClick
import ua.notky.base.ui.dialog.bottomsheet.BaseBindingBottomSheetDialogFragment
import ua.notky.silfy.R
import ua.notky.silfy.databinding.BottomsheetMoreProfilesBinding
import ua.notky.silfy.extension.showAlert
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.ui.adapter.MoreProfileAdapter
import ua.notky.silfy.ui.adapter.decorators.AddSpaceFirstItemDecorator
import ua.notky.silfy.ui.adapter.decorators.AddSpaceLastItemDecorator
import ua.notky.silfy.viewmodel.StateViewModel
import ua.notky.silfy.viewmodel.auth.AuthViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 12.08.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class MoreProfileBottomsheet :
    BaseBindingBottomSheetDialogFragment<BottomsheetMoreProfilesBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomsheetMoreProfilesBinding
        get() = BottomsheetMoreProfilesBinding::inflate

    private val authViewModel by activityViewModels<AuthViewModel>()
    private val stateViewModel by activityViewModels<StateViewModel>()
    private val adapter by lazy { return@lazy MoreProfileAdapter() }

    override fun initializeViews() {
        binding.state = stateViewModel.state
        initializeAdapter()
    }

    private fun initializeAdapter() {
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addItemDecoration(AddSpaceFirstItemDecorator(20))
        binding.recyclerView.addItemDecoration(AddSpaceLastItemDecorator(20))

        adapter.doOnRootClick { showAuthProfileAlert(it) }
    }

    override fun initializeViewModels() {
        observe(authViewModel.profiles, ::renderProfiles)
    }

    private fun renderProfiles(profiles: List<Profile>?) {
        profiles?.let { adapter.clearAndAddAll(it) }
    }

    private fun showAuthProfileAlert(profile: Profile) {
        val title = getString(R.string.alert_title_auth_selected_profile)
            .format(profile.email)
            .setBoldSpan(profile.email)

        showAlert(
            title = title,
            onSuccess = { authViewModel.onContinue(profile) }
        )
    }
}