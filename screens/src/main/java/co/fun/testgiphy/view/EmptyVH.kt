package co.`fun`.testgiphy.view

import android.view.View
import android.view.ViewGroup
import co.`fun`.testgiphy.AdapterDelegate
import co.`fun`.testgiphy.BaseVH
import co.`fun`.testgiphy.ItemType
import co.`fun`.testgiphy.search.R
import kotlinx.android.extensions.LayoutContainer

class EmptyListDelegate : AdapterDelegate {
    override fun createViewHolder(parent: ViewGroup): BaseVH<ItemType> =
        EmptyListVH(parent)
}

class EmptyListVH(parent: ViewGroup) : BaseVH<ItemType>(parent,
    R.layout.vh_empty_list
),
    LayoutContainer {
    override val containerView: View = itemView

    override fun bind(item: ItemType) = Unit

}

class EmptyItem : ItemType {
    override fun getItemType(): Int = ItemType.EMPTY
}