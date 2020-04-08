package co.`fun`.testgiphy.view

import android.content.res.ColorStateList
import android.view.ViewGroup
import co.`fun`.testgiphy.*
import co.`fun`.testgiphy.data.Loading
import co.`fun`.testgiphy.search.R
import kotlinx.android.synthetic.main.vh_loading.view.*
import javax.inject.Inject

class LoadingDelegate : AdapterDelegate {

    override fun createViewHolder(parent: ViewGroup): BaseVH<ItemType> =
        LoadingVH(parent)

}


class LoadingVH(parent: ViewGroup) : BaseVH<ItemType>(parent,
    R.layout.vh_loading
) {

    @Inject
    lateinit var resourceUtils: ResourceUtils

    override fun bind(item: ItemType) = with(itemView) {
        item as Loading
        item.color
            ?.let(resourceUtils::getColorSafe)
            ?.let(ColorStateList::valueOf)
            ?.let(pbLoadingVH::setProgressTintList)
        return@with
    }
}