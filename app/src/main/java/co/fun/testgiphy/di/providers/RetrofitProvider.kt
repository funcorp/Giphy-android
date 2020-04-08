package co.`fun`.testgiphy.di.providers

import co.`fun`.testgiphy.di.ApiKeyInterceptor
import co.`fun`.testgiphy.di.BaseUrl
import javax.inject.Inject
import javax.inject.Provider
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitProvider @Inject constructor(
    @BaseUrl private val baseUr: String,
    @ApiKeyInterceptor private val apiKeyInterceptor: Interceptor,
    private val loggingInterceptor: HttpLoggingInterceptor
) : Provider<Retrofit> {

    override fun get(): Retrofit =
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addNetworkInterceptor(loggingInterceptor)
            .build()
            .let { okHttpClient ->
                Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .baseUrl(baseUr)
                    .build()
            }
}
