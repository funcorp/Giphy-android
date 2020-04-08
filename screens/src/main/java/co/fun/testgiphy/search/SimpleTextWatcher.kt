package co.`fun`.testgiphy.search

import android.text.Editable
import android.text.TextWatcher
import co.`fun`.testgiphy.StringLambda


class SimpleTextWatcher(private val observeFunc: StringLambda) : TextWatcher {

    override fun afterTextChanged(s: Editable?) = Unit

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
        observeFunc(s.toString())

}