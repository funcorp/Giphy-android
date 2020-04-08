package co.`fun`.testgiphy

import arrow.core.Try
import co.`fun`.testgiphy.data.GiphyRepository
import javax.inject.Inject


class SearchGifsUseCase @Inject constructor(
    private val repository: GiphyRepository
) {

    fun execute(query: String, offset: Int) =
        Try.invoke { repository.searchGifs(query, offset) }
            .map { result -> result?.items.orEmpty() }

}