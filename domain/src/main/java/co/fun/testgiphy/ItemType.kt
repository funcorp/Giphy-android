package co.`fun`.testgiphy


interface ItemType {

    fun getItemType(): Int

    companion object {
        const val CARD = 0
        const val LOADING = 1
        const val EMPTY = 2
    }

}