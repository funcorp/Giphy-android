package co.`fun`.testgiphy.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import co.`fun`.testgiphy.*
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class TrendingViewModel @Inject constructor(
    private val fetchTrendingUseCase: FetchTrendingUseCase
) : ViewModel() {

    fun getItems(offset: Int = 0): LiveData<ViewState> = liveData(Dispatchers.IO) {
        fetchTrendingUseCase.execute(offset)
            .fold<ViewState>(
                ifFailure = ::ErrorState,
                ifSuccess = { items ->
                    if (items.isEmpty()) EmptyState() else ShowTrendingState(
                        items
                    )
                }
            )
            .run { emit(this) }
    }

}