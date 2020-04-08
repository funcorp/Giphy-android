package co.`fun`.testgiphy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.`fun`.testgiphy.Scope.ACTIVITY
import co.`fun`.testgiphy.Scope.APPLICATION
import toothpick.Toothpick


open class BaseActivity : AppCompatActivity() {

    open var layoutId: Int = 0

    private val onCreateBodies = mutableListOf<() -> Unit>()
    private val onStartBodies = mutableListOf<() -> Unit>()
    private val onDestroyBodies = mutableListOf<() -> Unit>()

    open fun onCreate(block: () -> Unit) {
        onCreateBodies.add(block)
    }

    open fun onStart(block: () -> Unit) {
        onStartBodies.add(block)
    }

    open fun onDestroy(block: () -> Unit) {
        onDestroyBodies.add(block)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutId.takeIf { it != 0 }?.let(::setContentView)
        Toothpick
            .openScopes(APPLICATION, ACTIVITY)
            .let { scope -> Toothpick.inject(this, scope) }
            .also { Toothpick.closeScope(ACTIVITY) }
        onCreateBodies.forEach { f -> f() }
    }

    override fun onStart() {
        super.onStart()
        onStartBodies.forEach { f -> f() }
    }

    override fun onDestroy() {
        onDestroyBodies.forEach { f -> f() }
        super.onDestroy()
    }

}