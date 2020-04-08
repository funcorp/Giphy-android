package co.`fun`.testgiphy

sealed class ViewState
data class ErrorState(val throwable: Throwable) : ViewState()
data class ShowTrendingState(val items: List<ItemType>) : ViewState()
data class ShowSearchResultsState(val items: List<ItemType>) : ViewState()
class EmptyState : ViewState()
