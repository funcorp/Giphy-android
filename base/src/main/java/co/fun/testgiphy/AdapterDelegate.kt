package co.`fun`.testgiphy

import android.view.ViewGroup


interface AdapterDelegate {

    fun createViewHolder(parent: ViewGroup): BaseVH<ItemType>

}