package co.`fun`.testgiphy.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import co.`fun`.testgiphy.*
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class SearchViewModel @Inject constructor(
    private val useCase: SearchGifsUseCase
) : ViewModel() {

    fun getSearchResult(query: CharSequence, offset: Int = 0): LiveData<ViewState> =
        liveData(Dispatchers.IO) {
            useCase.execute(query = query.toString(), offset = offset)
                .fold<ViewState>(
                    ifFailure = ::ErrorState,
                    ifSuccess = { items ->
                        if (items.isEmpty()) EmptyState() else ShowSearchResultsState(
                            items
                        )
                    }
                )
                .run { emit(this) }
        }

}