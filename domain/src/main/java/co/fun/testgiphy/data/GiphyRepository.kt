package co.`fun`.testgiphy.data


interface GiphyRepository {

    fun fetchTrending(offset: Int = 0): Trending?
    fun searchGifs(query: String, offset: Int = 0): SearchResult?

}