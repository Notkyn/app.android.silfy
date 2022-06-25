package ua.notky.silfy.ui.fragment.menu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import ua.notky.base.extension.observe
import ua.notky.base.extension.openSafePopBackstackScreen
import ua.notky.base.extension.startActivity
import ua.notky.base.ui.adapter.extensions.doOnActionDelete
import ua.notky.base.ui.fragment.BaseBindingFragment
import ua.notky.base.viewmodel.ViewModelSet
import ua.notky.silfy.config.ACTION_LOGOUT
import ua.notky.silfy.databinding.FragmentMenuProfileBinding
import ua.notky.silfy.models.model.Profile
import ua.notky.silfy.ui.activity.AuthActivity
import ua.notky.silfy.ui.adapter.MenuProfileAdapter
import ua.notky.silfy.ui.adapter.decorators.AddSpaceFirstItemDecorator
import ua.notky.silfy.ui.adapter.decorators.AddSpaceLastItemDecorator
import ua.notky.silfy.viewmodel.menu.MenuProfileViewModel

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 25.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */
class ProfileMenuFragment : BaseBindingFragment<FragmentMenuProfileBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMenuProfileBinding
        get() = FragmentMenuProfileBinding::inflate

    private val profileMenuViewModel by viewModels<MenuProfileViewModel>()

    private val adapter: MenuProfileAdapter by lazy { return@lazy MenuProfileAdapter() }

    override fun injectViewModels(): ViewModelSet {
        return ViewModelSet.Builder()
            .addViewModel(profileMenuViewModel)
            .build()
    }

    override fun initializeViews() {
        initializeAdapter()
    }

    override fun initializeListeners() {
        binding.header.handleBackClick { openSafePopBackstackScreen() }
    }

    override fun initializeViewModels() {
        profileMenuViewModel.fetchData()

        observe(profileMenuViewModel.profiles, ::renderProfiles)
    }

    private fun initializeAdapter() {
        binding.recycler.adapter = adapter
        binding.recycler.itemAnimator = DefaultItemAnimator()
        binding.recycler.addItemDecoration(AddSpaceFirstItemDecorator(TOP_MARGIN_FOR_ITEM))
        binding.recycler.addItemDecoration(AddSpaceLastItemDecorator(BOTTOM_MARGIN_FOR_ITEM))

        adapter.doOnActionDelete { profileMenuViewModel.delete(it) }
    }

    private fun renderProfiles(profiles: List<Profile>?) {
        profiles?.let { adapter.clearAndAddAll(it) }
    }

    override fun handleActionVM(type: Int) {
        if(type == ACTION_LOGOUT) {
            onNextAuth()
        }
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