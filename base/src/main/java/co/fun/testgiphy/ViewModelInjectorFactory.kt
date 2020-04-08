package co.`fun`.testgiphy

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import co.`fun`.testgiphy.Scope.APPLICATION
import toothpick.Toothpick
import toothpick.config.Module

/**
 * this class exist solely to inject proper viewmodels in fragments via `injectViewModel()`
 * As manually create viewmodels with proper factories is unbearable
 */
class ViewModelInjectorFactory(
    val moduleProvider: (Module) -> Unit = {}
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        Toothpick
            .openScope(APPLICATION)
            .apply {
                installModules(object : Module() {
                    init {
                        moduleProvider(this)
                    }
                })
            }
            .getInstance(modelClass) as T

}

inline fun <reified T : ViewModel> Fragment.injectViewModel(
    noinline moduleProvider: (Module) -> Unit = {}
): T =
    ViewModelProviders
        .of(this.requireActivity(),
            ViewModelInjectorFactory(moduleProvider)
        )
        .get(T::class.java)