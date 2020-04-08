package co.`fun`.testgiphy

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.lang.ref.WeakReference
import javax.inject.Inject

data class SnackbarConfig(
    val throwable: Throwable,
    val actionTitle: Title,
    val clickFunc: ViewLambda = {}
)

class ErrorHandlerDelegate @Inject constructor() {

    fun showErrorMessage(view: View, config: SnackbarConfig) {
        val weakView = WeakReference(view)
        weakView.get()?.let { root ->
            val (throwable, title, clickFunc) = config
            throwable.message
                ?.takeIf(CharSequence::isNotEmpty)
                ?.let { message -> "${throwable::class.java.simpleName}: $message" }
                ?.let { messageString ->
                    Snackbar
                        .make(root, messageString, Snackbar.LENGTH_LONG)
                        .setAction(title, clickFunc)
                        .show()
                }
        }
        weakView.clear()
    }

}