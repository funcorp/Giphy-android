package co.`fun`.testgiphy

import co.`fun`.testgiphy.data.GiphyRepository
import co.`fun`.testgiphy.data.SearchResult
import co.`fun`.testgiphy.data.Trending
import javax.inject.Inject


class GiphyRepositoryImpl @Inject constructor(
    private val api: GiphyApi,
    @GiphyDb private val persistence: BasePersistence
) : GiphyRepository {

    override fun fetchTrending(offset: Int): Trending? =
        api.getTrending(
            limit = PARAM_LIMIT,
            offset = offset
        )
            .execute()
            .body()
            ?.let { response ->
                response.data
                    .map(mapToGiphy)
                    .let(::Trending)
                    .also { trending -> persistence.write(KEY_TRENDING_CACHE, trending) }
            }

    override fun searchGifs(query: String, offset: Int): SearchResult? =
        api.getSearch(
            query = query,
            limit = PARAM_LIMIT,
            offset = offset,
            rating = "G"
        )
            .execute()
            .body()
            ?.let { response ->
                response.data
                    .map(mapToGiphy)
                    .let(::SearchResult)
                    .also { searchResult -> persistence.write(KEY_SEARCH_CACHE, searchResult) }
            }

    companion object {
        private const val PARAM_LIMIT = 10
        private const val KEY_TRENDING_CACHE = "key_trending_cache"
        private const val KEY_SEARCH_CACHE = "key_search_cache"
    }

}
