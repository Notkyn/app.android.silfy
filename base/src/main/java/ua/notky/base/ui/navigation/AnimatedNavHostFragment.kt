package ua.notky.base.ui.navigation

import android.os.Bundle
import android.view.View
import androidx.annotation.NavigationRes
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import timber.log.Timber

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 16.10.2021
 * @email evgeniy.zarechnyi@4k.com.ua
 */

class AnimatedNavHostFragment : NavHostFragment() {
    private var mViewParent: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mViewParent = view.parent as? View

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateNavController(navController: NavController) {
        super.onCreateNavController(navController)

        navController.navigatorProvider.addNavigator(
            // this replaces FragmentNavigator
            AnimatedFragmentNavigator(requireContext(), childFragmentManager, id)
        )
    }

    // Workaround for crash on activity destroy because of null nav controller
    override fun onDestroyView() {
        try {
            if (mViewParent != null) {
                Navigation.setViewNavController(mViewParent!!, navController)
            }
            super.onDestroyView()
        } catch (e: Exception) {
            Timber.w(e)
        }
        mViewParent = null
    }

    companion object {

        private const val KEY_GRAPH_ID = "android-support-nav:fragment:graphId"
        private const val KEY_START_DESTINATION_ARGS =
            "android-support-nav:fragment:startDestinationArgs"

        fun create(
            @NavigationRes graphResId: Int,
            startDestinationArgs: Bundle?
        ): AnimatedNavHostFragment {
            var b: Bundle? = null
            if (graphResId != 0) {
                b = Bundle()
                b.putInt(KEY_GRAPH_ID, graphResId)
            }
            if (startDestinationArgs != null) {
                if (b == null) {
                    b = Bundle()
                }
                b.putBundle(KEY_START_DESTINATION_ARGS, startDestinationArgs)
            }

            val result = AnimatedNavHostFragment()
            if (b != null) {
                result.arguments = b
            }
            return result
        }
    }
}
