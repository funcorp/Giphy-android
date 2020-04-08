package co.`fun`.testgiphy.di.modules

import android.app.Application
import android.content.Context
import co.`fun`.testgiphy.ErrorHandlerDelegate
import co.`fun`.testgiphy.di.providers.ErrorHandlerProvider
import toothpick.config.Module

class AppModule(application: Application) : Module() {
    init {
        bind(Application::class.java).toInstance(application)
        bind(Context::class.java).toInstance(application)
        bind(ErrorHandlerDelegate::class.java)
            .toProvider(ErrorHandlerProvider::class.java)
            .providesSingleton()
    }
}
