package co.`fun`.testgiphy

import android.app.Application
import arrow.core.andThen
import co.`fun`.testgiphy.Scope.APPLICATION
import co.`fun`.testgiphy.di.modules.ApiModule
import co.`fun`.testgiphy.di.modules.AppModule
import co.`fun`.testgiphy.di.modules.DataModule
import co.`fun`.testgiphy.di.modules.NavigationModule
import io.paperdb.Paper
import timber.log.Timber
import toothpick.Toothpick
import toothpick.configuration.Configuration

class GiphyApp : Application() {

    private val initDi: (Application) -> Application = { application ->
        when (BuildConfig.DEBUG) {
            true -> Toothpick.setConfiguration(Configuration.forDevelopment())
            false -> Toothpick.setConfiguration(Configuration.forProduction())
        }
        Toothpick.openScope(APPLICATION)
            .run {
                installModules(
                    AppModule(application),
                    ApiModule(),
                    DataModule(),
                    NavigationModule()
                )
            }
            .run { inject(application) }
        application
    }

    private val initPaper: (Application) -> Application = { application ->
        Paper.init(application)
        application
    }

    private val initTimber: (Application) -> Application = { application ->
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        application
    }

    private val configureApplication: (Application) -> Application =
        initPaper andThen initTimber andThen initDi

    override fun onCreate() {
        super.onCreate()
        configureApplication(this@GiphyApp)
    }
}
