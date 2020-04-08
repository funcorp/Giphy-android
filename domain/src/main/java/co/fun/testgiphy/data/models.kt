package co.`fun`.testgiphy.data

import co.`fun`.testgiphy.ItemType


data class Trending(
    val items: List<Giphy>
)

data class Giphy(
    val id: String,
    val url: String,
    val previewUrl: String,
    val height: Int,
    val width: Int,
    val username: String,
    val rating: String,
    val title: String
) : ItemType {
    override fun getItemType(): Int = ItemType.CARD
}

data class SearchResult(
    val items: List<Giphy>
)

data class Loading(
    val color: Int? = null
): ItemType {
    override fun getItemType(): Int = ItemType.LOADING
}