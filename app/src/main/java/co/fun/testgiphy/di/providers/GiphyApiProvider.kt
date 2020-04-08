package co.`fun`.testgiphy.di.providers

import co.`fun`.testgiphy.GiphyApi
import javax.inject.Inject
import javax.inject.Provider
import retrofit2.Retrofit

class GiphyApiProvider @Inject constructor(
    private val retrofit: Retrofit
) : Provider<GiphyApi> {

    override fun get(): GiphyApi = retrofit.create(
        GiphyApi::class.java)
}
