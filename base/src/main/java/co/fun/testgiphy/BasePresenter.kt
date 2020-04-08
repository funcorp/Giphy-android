@file:Suppress("unused")

package co.`fun`.testgiphy

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import timber.log.Timber


abstract class BasePresenter<V : BaseView> {

    var view: BaseView
        get() = views.first()
        private set(value) {}

    private val views = mutableSetOf<BaseView>()

    private var supervisorJob: Job = SupervisorJob()
    var presenterScope = CoroutineScope(Dispatchers.Main + supervisorJob)

    fun attachView(view: BaseView) {
        views.add(view)
        onFirstViewAttached()
    }

    fun onDestroy() {
        supervisorJob.cancelChildren()
        views.clear()
    }

    open fun onFirstViewAttached() {
    }

    protected fun coroutine(
        body: suspend () -> Unit,
        error: (e: Throwable) -> Unit = Timber::e,
        finally: suspend () -> Unit = {}
    ) {
        val exceptionHandler = CoroutineExceptionHandler { _, t ->
            Timber.e(t)
            error.invoke(t)
        }
        presenterScope.launch(exceptionHandler) {
            body.invoke()
            finally.invoke()
        }
    }

}