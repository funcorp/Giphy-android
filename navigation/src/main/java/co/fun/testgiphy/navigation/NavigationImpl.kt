@file:Suppress("SimpleRedundantLet")

package co.`fun`.testgiphy.navigation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import co.`fun`.testgiphy.Navigation
import java.lang.ref.WeakReference
import javax.inject.Inject

class NavigationImpl @Inject constructor() : Navigation {

    private val enterOptions = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    private val exitOptions = navOptions {
        anim {
            enter = R.anim.slide_in_left
            exit = R.anim.slide_out_right
            popEnter = R.anim.slide_in_right
            popExit = R.anim.slide_out_left
        }
    }

    override fun goToSearchFragmentFrom(fragment: Fragment) {
        val weakFragment = WeakReference(fragment)
        weakFragment.get()?.let { f ->
            f.findNavController()
                .navigate(R.id.action_fragmentTrending_to_fragmentSearch, null, enterOptions)
        }
        weakFragment.clear()
    }

    override fun goBackToTrending(fragment: Fragment) =
        fragment.findNavController()
            .navigate(R.id.action_fragmentSearch_pop, null, exitOptions)

}