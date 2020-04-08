package co.`fun`.testgiphy

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface GiphyApi {

    @GET("gifs/trending")
    fun getTrending(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<GiphyResponse>

    @GET("gifs/search")
    fun getSearch(
        @Query("q") query: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("rating") rating: String,
        @Query("lang") language: String = "en"
    ): Call<GiphyResponse>

}