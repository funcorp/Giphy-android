package co.`fun`.testgiphy

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import co.`fun`.testgiphy.Scope.APPLICATION
import co.`fun`.testgiphy.Scope.FRAGMENT
import toothpick.Toothpick


open class BaseFragment : Fragment() {

    open var layoutId: Int = 0

    private val onCreateBodies = mutableListOf<() -> Unit>()
    private val onAttachBodies = mutableListOf<() -> Unit>()
    private val onViewCreatedBodies = mutableListOf<() -> Unit>()
    private val onDestroyBodies = mutableListOf<() -> Unit>()

    open fun onCreate(block: () -> Unit) {
        onCreateBodies.add(block)
    }

    open fun onAttach(block: () -> Unit) {
        onAttachBodies.add(block)
    }

    open fun onViewCreated(block: () -> Unit) {
        onViewCreatedBodies.add(block)
    }

    open fun onDestroyView(block: () -> Unit) {
        onDestroyBodies.add(block)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Toothpick
            .openScopes(APPLICATION, FRAGMENT)
            .let { scope -> Toothpick.inject(this, scope) }
            .also { Toothpick.closeScope(FRAGMENT) }
        onCreateBodies.forEach { f -> f() }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onAttachBodies.forEach { f -> f() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutId
            .takeIf { it != 0 }
            ?.let { id -> inflater.inflate(id, container, false) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedBodies.forEach { f -> f() }
    }

    override fun onDestroyView() {
        onDestroyBodies.forEach { f -> f() }
        super.onDestroyView()
    }

}
