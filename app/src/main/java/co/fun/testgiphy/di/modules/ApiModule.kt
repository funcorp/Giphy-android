package co.`fun`.testgiphy.di.modules

import co.`fun`.testgiphy.BuildConfig
import co.`fun`.testgiphy.GiphyApi
import co.`fun`.testgiphy.di.ApiKey
import co.`fun`.testgiphy.di.ApiKeyInterceptor
import co.`fun`.testgiphy.di.BaseUrl
import co.`fun`.testgiphy.di.providers.ApiKeyInterceptorProvider
import co.`fun`.testgiphy.di.providers.GiphyApiProvider
import co.`fun`.testgiphy.di.providers.RetrofitProvider
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import toothpick.config.Module

class ApiModule : Module() {
    init {

        bind(String::class.java)
            .withName(BaseUrl::class.java)
            .toInstance(BuildConfig.BASE_URL)

        bind(String::class.java)
            .withName(ApiKey::class.java)
            .toInstance(BuildConfig.API_KEY)

        bind(Interceptor::class.java)
            .withName(ApiKeyInterceptor::class.java)
            .toProvider(ApiKeyInterceptorProvider::class.java)
            .providesSingleton()

        bind(Retrofit::class.java)
            .toProvider(RetrofitProvider::class.java)
            .providesSingleton()

        bind(GiphyApi::class.java)
            .toProvider(GiphyApiProvider::class.java)
            .providesSingleton()

        val interceptor by lazy {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        bind(HttpLoggingInterceptor::class.java).toInstance(interceptor)
    }
}
