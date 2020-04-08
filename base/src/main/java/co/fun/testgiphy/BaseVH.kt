package co.`fun`.testgiphy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import toothpick.Toothpick

abstract class BaseVH<T> : RecyclerView.ViewHolder {

    constructor(
        parent: ViewGroup,
        @LayoutRes layoutId: Int
    ) : super(LayoutInflater.from(parent.context).inflate(layoutId, parent, false)) {
        init()
    }

    constructor(view: View) : super(view) {
        init()
    }

    protected fun init() {
        val scopes = Toothpick
            .openScope(Scope.APPLICATION)
            .openSubScope(Scope.VIEW)
        Toothpick.inject(this, scopes)
        Toothpick.closeScope(Scope.VIEW)
    }

    abstract fun bind(item: T)
    open fun unbind() {}
}
