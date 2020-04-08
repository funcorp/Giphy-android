package co.`fun`.testgiphy

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.`fun`.testgiphy.ItemType.Companion.LOADING
import co.`fun`.testgiphy.data.Loading


open class BaseAdapter : RecyclerView.Adapter<BaseVH<ItemType>>() {

    private val items: MutableList<ItemType> = mutableListOf()
    protected val delegates: SparseArrayCompat<AdapterDelegate> = SparseArrayCompat()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH<ItemType> =
        delegates.get(viewType)!!.createViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseVH<ItemType>, position: Int) =
        holder.bind(items[position])

    override fun onViewRecycled(holder: BaseVH<ItemType>) = holder.unbind()

    override fun getItemViewType(position: Int): Int = items[position].getItemType()

    fun add(list: List<ItemType>) {
        val newList = items + list
        val diffResult = calculateDiff(newList)
        items.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun replace(list: List<ItemType>) {
        val diffResult = calculateDiff(list)
        items.clear()
        items.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun addSingleItem(item: ItemType) {
        val newList = items + item
        val diffResult = calculateDiff(newList)
        items.add(item)
        diffResult.dispatchUpdatesTo(this)
    }

    fun addLoading() {
        val isLoading = items.any(isLoadingItem)
        if (!isLoading) addSingleItem(Loading())
    }

    fun removeLoading() =
        items.indexOfFirst(isLoadingItem)
            .takeIf { it != -1 }
            ?.let { index ->
                val loadingItem = items.first(isLoadingItem)
                val newList = items.minus(loadingItem)
                val diffResult = calculateDiff(newList)
                items.removeAt(index)
                diffResult.dispatchUpdatesTo(this)
            } ?: Unit

    private val isLoadingItem: (ItemType) -> Boolean = { it.getItemType() == LOADING }

    private fun calculateDiff(items: List<ItemType>) =
        DiffUtil.calculateDiff(
            DiffUtilCallback(
                newList = items,
                oldList = listOf(*this.items.toTypedArray()),
                areItemsTheSame = { o1, o2 -> o1 == o2 }
            )
        )

    inner class DiffUtilCallback<R>(
        private val newList: List<R>,
        private val oldList: List<R>,
        private val areItemsTheSame: (R, R) -> Boolean
    ) : DiffUtil.Callback() {

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? = Any()

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            areItemsTheSame(oldList[oldItemPosition], newList[newItemPosition])

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }

}