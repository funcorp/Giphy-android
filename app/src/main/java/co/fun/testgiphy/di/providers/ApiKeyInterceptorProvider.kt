package co.`fun`.testgiphy.di.providers

import co.`fun`.testgiphy.di.ApiKey
import javax.inject.Inject
import javax.inject.Provider
import okhttp3.Interceptor

class ApiKeyInterceptorProvider @Inject constructor(
    @ApiKey private val apiKey: String
) : Provider<Interceptor> {

    override fun get() = Interceptor { chain ->
        chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()
            .let { url ->
                chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
            }
            .run(chain::proceed)
    }
}
