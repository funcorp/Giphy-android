package co.`fun`.testgiphy

import co.`fun`.testgiphy.GifObject
import co.`fun`.testgiphy.MetaResponse
import co.`fun`.testgiphy.PaginationResponse
import com.google.gson.annotations.SerializedName


data class GiphyResponse(
    @SerializedName("data")
    val data: List<GifObject>,
    @SerializedName("pagination")
    val pagination: PaginationResponse,
    @SerializedName("meta")
    val meta: MetaResponse
)