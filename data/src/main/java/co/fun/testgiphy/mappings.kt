package co.`fun`.testgiphy

import co.`fun`.testgiphy.data.Giphy

val mapToGiphy: (GifObject) -> Giphy = { gifObject ->
    val imagesResponse = gifObject.images?.fixedWidthResponse
    Giphy(
        id = gifObject.id.orExceptionIfNull(),
        url = imagesResponse?.url.orExceptionIfNull(),
        previewUrl = gifObject.images?.fixedWidthStillResponse?.url.orExceptionIfNull(),
        height = imagesResponse?.height?.toIntOrNull() ?: 0,
        width = imagesResponse?.width?.toIntOrNull() ?: 0,
        username = gifObject.user?.displayName ?: gifObject.username.orExceptionIfNull(),
        rating = gifObject.rating.orExceptionIfNull(),
        title = gifObject.title.orExceptionIfNull()
    )
}

fun String?.orExceptionIfNull(): String =
    this ?: throw IllegalArgumentException("response field can't be null")