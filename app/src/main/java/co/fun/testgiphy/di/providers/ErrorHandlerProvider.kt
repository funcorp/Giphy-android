package co.`fun`.testgiphy.di.providers

import co.`fun`.testgiphy.ErrorHandlerDelegate
import javax.inject.Inject
import javax.inject.Provider

class ErrorHandlerProvider @Inject constructor() : Provider<ErrorHandlerDelegate> {

    override fun get(): ErrorHandlerDelegate =
        ErrorHandlerDelegate()
}
