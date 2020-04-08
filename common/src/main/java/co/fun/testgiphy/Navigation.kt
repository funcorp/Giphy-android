package co.`fun`.testgiphy

import androidx.fragment.app.Fragment

interface Navigation {
    fun goToSearchFragmentFrom(fragment: Fragment)
    fun goBackToTrending(fragment: Fragment)
}