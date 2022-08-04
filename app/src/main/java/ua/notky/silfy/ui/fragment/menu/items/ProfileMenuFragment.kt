package ua.notky.silfy.ui.fragment.menu.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import dagger.hilt.android.AndroidEntryPoint
import ua.notky.base.extension.*
import ua.notky.base.ui.adapter.extensions.doOnActionDelete
import ua.notky.base.ui.adapter.extensions.doOnRootClick
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.R
import ua.notky.silfy.databinding.FragmentMenuProfileBinding
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.models.observable.ProfileMenuItemModel
import ua.notky.silfy.models.states.DeleteProfileUiState
import ua.notky.silfy.ui.activity.AuthActivity
import ua.notky.silfy.ui.adapter.MenuProfileAdapter
import ua.notky.silfy.ui.adapter.decorators.AddSpaceFirstItemDecorator
import ua.notky.silfy.ui.adapter.decorators.AddSpaceLastItemDecorator
import ua.notky.silfy.extension.showAlert
import ua.notky.silfy.viewmodel.StateViewModel
import ua.notky.silfy.viewmodel.menu.MenuProfileViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 25.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@AndroidEntryPoint
class ProfileMenuFragment : BaseBindingFragment<FragmentMenuProfileBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMenuProfileBinding
        get() = FragmentMenuProfileBinding::inflate

    private val profileMenuViewModel by viewModels<MenuProfileViewModel>()
    private val stateViewModel by viewModels<StateViewModel>()

    private val adapter: MenuProfileAdapter by lazy { return@lazy MenuProfileAdapter() }

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(profileMenuViewModel)
            .build()
    }

    override fun initializeViews() {
        binding.state = stateViewModel.state
        initializeAdapter()
    }

    override fun initializeListeners() {
        binding.header.handleBackClick { openSafePopBackstackScreen() }
    }

    override fun initializeViewModels() {
        profileMenuViewModel.start()

        observe(profileMenuViewModel.profiles, ::renderProfiles)
        observe(profileMenuViewModel.deleteState, ::renderDeleteState)
    }

    private fun initializeAdapter() {
        binding.recycler.adapter = adapter
        binding.recycler.itemAnimator = DefaultItemAnimator()
        binding.recycler.addItemDecoration(AddSpaceFirstItemDecorator(TOP_MARGIN_FOR_ITEM))
        binding.recycler.addItemDecoration(AddSpaceLastItemDecorator(BOTTOM_MARGIN_FOR_ITEM))

        adapter.doOnActionDelete { showDeleteProfileAlert(it.profile) }
        adapter.doOnRootClick { showSwitchProfileAlert(it.profile) }
    }

    private fun renderProfiles(profiles: List<ProfileMenuItemModel>?) {
        profiles?.let { adapter.clearAndAddAll(it) }
    }

    private fun renderDeleteState(state: DeleteProfileUiState?) {
        stateViewModel.setLoading(state == DeleteProfileUiState.Deleting)

        when (state) {
            DeleteProfileUiState.LogOut -> onNextAuth()
            DeleteProfileUiState.Failure -> toast(R.string.alert_error_delete_profile)
            else -> {}
        }
    }

    private fun showSwitchProfileAlert(profile: Profile) {
        val name = "${profile.firstName} ${profile.lastName}"
        val title = getString(R.string.alert_title_switch_profile_to)
            .format(name)
            .setBoldSpan(name)

        showAlert(
            title = title,
            onSuccess = { profileMenuViewModel.switch(profile) }
        )
    }

    private fun showDeleteProfileAlert(profile: Profile) {
        val name = "${profile.firstName} ${profile.lastName}"
        val title = getString(R.string.alert_title_profile_delete)
            .format(name)
            .setBoldSpan(name)

        showAlert(
            title = title,
            onSuccess = { profileMenuViewModel.delete(profile) }
        )
    }

    private fun onNextAuth() {
        activity?.startActivity<AuthActivity>()
        activity?.finishAffinity()
    }

    companion object {
        private const val TOP_MARGIN_FOR_ITEM = 14
        private const val BOTTOM_MARGIN_FOR_ITEM = 120
    }
}