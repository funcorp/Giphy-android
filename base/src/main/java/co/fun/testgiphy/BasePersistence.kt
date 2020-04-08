@file:Suppress("MemberVisibilityCanBePrivate")

package co.`fun`.testgiphy

import arrow.syntax.function.partially1
import io.paperdb.Paper
import timber.log.Timber


open class BasePersistence(private val bookName: String) {

    fun <T : Any> write(key: String, data: T): T =
        try {
            Paper
                .book(bookName)
                .write(key, data)
                .read<T>(key)
        } catch (e: Exception) {
            delete(key)
            throw e
        }

    fun <T : Any> read(
        key: String,
        defaultValue: T
    ): T =
        Paper.book(bookName).read(key, defaultValue)

    fun delete(key: String): Unit =
        tryWithErrorSuppressed
            .partially1 { s -> Paper.book(s).delete(key) }
            .invoke()

    fun eraseBook(): Unit =
        tryWithErrorSuppressed
            .partially1 { s -> Paper.book(s).destroy() }
            .invoke()

    private val tryWithErrorSuppressed: ((String) -> Unit) -> Unit = { f ->
        try {
            f(bookName)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }


}