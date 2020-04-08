package co.`fun`.testgiphy.di.modules

import co.`fun`.testgiphy.data.GiphyRepository
import co.`fun`.testgiphy.BasePersistence
import co.`fun`.testgiphy.GiphyDb
import co.`fun`.testgiphy.GiphyRepositoryImpl
import toothpick.config.Module

class DataModule : Module() {
    init {
        bind(BasePersistence::class.java)
            .withName(GiphyDb::class.java)
            .toInstance(BasePersistence("giphyDb"))
        bind(GiphyRepository::class.java).to(GiphyRepositoryImpl::class.java)
    }
}
