package co.`fun`.testgiphy.view

import android.view.View
import android.view.ViewGroup
import co.`fun`.testgiphy.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import co.`fun`.testgiphy.data.Giphy
import co.`fun`.testgiphy.search.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.vh_giphy_card.view.*
import javax.inject.Inject


class GiphyCardDelegate : AdapterDelegate {

    override fun createViewHolder(parent: ViewGroup): BaseVH<ItemType> =
        GiphyCardVH(parent)

}

class GiphyCardVH(parent: ViewGroup) : BaseVH<ItemType>(parent,
    R.layout.vh_giphy_card
),
    LayoutContainer {

    override val containerView: View = itemView

    @Inject
    lateinit var resourceUtils: ResourceUtils

    override fun bind(item: ItemType) = with(itemView) {
        item as Giphy
        val context = this.context

        item.title
            .takeIf(String::isNotEmpty)
            ?.run(tvGiphyCardTitle::setText)
            ?: run { tvGiphyCardTitle.visibility = View.GONE }

        item.username
            .takeIf(String::isNotEmpty)
            ?.run(tvGiphyCardUsername::setText)
            ?: run { tvGiphyCardUsername.visibility = View.GONE }

        item.url
            .takeIf(String::isNotEmpty)
            .let { url ->
                Glide
                    .with(context)
                    .load(url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(
                        RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                    )
                    .into(ivGiphyCard)
            }

        return@with
    }

    override fun unbind() = with(itemView) {
        if (tvGiphyCardTitle.visibility == View.GONE) {
            tvGiphyCardTitle.visibility = View.VISIBLE
        }
        if (tvGiphyCardUsername.visibility == View.GONE) {
            tvGiphyCardUsername.visibility = View.VISIBLE
        }
    }

}