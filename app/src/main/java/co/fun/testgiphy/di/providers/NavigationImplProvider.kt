package co.`fun`.testgiphy.di.providers

import co.`fun`.testgiphy.navigation.NavigationImpl
import javax.inject.Inject
import javax.inject.Provider

class NavigationImplProvider @Inject constructor() : Provider<NavigationImpl> {

    override fun get(): NavigationImpl =
        NavigationImpl()
}
