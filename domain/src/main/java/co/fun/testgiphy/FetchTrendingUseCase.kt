package co.`fun`.testgiphy

import arrow.core.Try
import co.`fun`.testgiphy.data.GiphyRepository
import javax.inject.Inject


class FetchTrendingUseCase @Inject constructor(
    private val repository: GiphyRepository
) {

    fun execute(offset: Int) =
        Try.invoke { repository.fetchTrending(offset) }
            .map { trending -> trending?.items.orEmpty() }

}
