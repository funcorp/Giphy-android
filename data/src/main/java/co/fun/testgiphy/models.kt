package co.`fun`.testgiphy

import com.google.gson.annotations.SerializedName

data class GifObject(
    @SerializedName("type")
    val type: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("bitly_url")
    val bitlyUrl: String?,
    @SerializedName("embed_url")
    val embedUrl: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("source")
    val source: String?,
    @SerializedName("rating")
    val rating: String?,
    @SerializedName("content_url")
    val contentUrl: String?,
    @SerializedName("user")
    val user: UserResponse?,
    @SerializedName("source_tld")
    val sourceTld: String?,
    @SerializedName("source_post_url")
    val sourcePostUrl: String?,
    @SerializedName("update_datetime")
    val updateDatetime: String?,
    @SerializedName("create_datetime")
    val createDatetime: String?,
    @SerializedName("import_datetime")
    val importDatetime: String?,
    @SerializedName("trending_datetime")
    val trendingDatetime: String?,
    @SerializedName("images")
    val images: ImagesResponse?,
    @SerializedName("title")
    val title: String?
)

data class PaginationResponse(
    @SerializedName("offset")
    val offset: Int?,
    @SerializedName("total_count")
    val totalCount: Int?,
    @SerializedName("count")
    val count: Int?
)

data class MetaResponse(
    @SerializedName("msg")
    val msg: String?,
    @SerializedName("status")
    val responseCode: Int?,
    @SerializedName("response_id")
    val responseId: String?
)

data class UserResponse(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("banner_url")
    val bannerUrl: String?,
    @SerializedName("profile_url")
    val profileUrl: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("display_name")
    val displayName: String?
)

data class ImagesResponse(
    @SerializedName("fixed_height")
    val fixedHeightResponse: FixedResponse?,
    @SerializedName("fixed_height_still")
    val fixedHeightStillResponse: FixedResponse?,
    @SerializedName("fixed_width")
    val fixedWidthResponse: FixedResponse?,
    @SerializedName("fixed_width_still")
    val fixedWidthStillResponse: FixedResponse?
)

data class FixedResponse(
    @SerializedName("url")
    val url: String?,
    @SerializedName("width")
    val width: String?,
    @SerializedName("height")
    val height: String?,
    @SerializedName("size")
    val size: String?,
    @SerializedName("mp4")
    val mp4Url: String?,
    @SerializedName("mp4_size")
    val mp4Size: String?,
    @SerializedName("webp")
    val webpUrl: String?,
    @SerializedName("webp_size")
    val webpSize: String?
)