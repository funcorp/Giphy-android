package co.`fun`.testgiphy.di.modules

import co.`fun`.testgiphy.Navigation
import co.`fun`.testgiphy.di.providers.NavigationImplProvider
import toothpick.config.Module

class NavigationModule : Module() {
    init {
        bind(Navigation::class.java)
            .toProvider(NavigationImplProvider::class.java)
            .providesSingleton()
    }
}
