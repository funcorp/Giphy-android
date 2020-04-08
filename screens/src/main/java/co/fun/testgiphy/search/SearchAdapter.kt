package co.`fun`.testgiphy.search

import co.`fun`.testgiphy.ItemType
import co.`fun`.testgiphy.BaseAdapter
import co.`fun`.testgiphy.view.EmptyListDelegate
import co.`fun`.testgiphy.view.GiphyCardDelegate
import co.`fun`.testgiphy.view.LoadingDelegate

class SearchAdapter : BaseAdapter() {

    init {
        delegates.append(ItemType.EMPTY,
            EmptyListDelegate()
        )
        delegates.append(ItemType.CARD,
            GiphyCardDelegate()
        )
        delegates.append(ItemType.LOADING,
            LoadingDelegate()
        )
    }

}