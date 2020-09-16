package fillooow.app.customprogressbar.custom_view.extension

import android.text.Editable
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.annotation.StyleRes
import androidx.core.widget.TextViewCompat
import fillooow.app.customprogressbar.custom_view.text.SimpleTextWatcher

internal fun TextView.setTextStyle(@StyleRes styleResId: Int) {
    TextViewCompat.setTextAppearance(this, styleResId)
}

internal fun TextView.applyLineSpacing(@DimenRes dimenResId: Int, multiplier: Float = 1f) {

    setLineSpacing(resources.getDimensionPixelSize(dimenResId).toFloat(), multiplier)
}

inline fun TextView.afterTextChanged(crossinline action: (text: CharSequence) -> Unit) {

    addTextChangedListener(object : SimpleTextWatcher() {

        override fun afterTextChanged(editable: Editable) = action.invoke(editable)
    })
}

inline fun TextView.beforeTextChanged(crossinline action: (text: CharSequence) -> Unit) {

    addTextChangedListener(object : SimpleTextWatcher() {

        override fun beforeTextChanged(value: CharSequence, start: Int, count: Int, after: Int) {

            action.invoke(value)
        }
    })
}

inline fun TextView.onTextChanged(crossinline action: (text: CharSequence?) -> Unit) {

    addTextChangedListener(object : SimpleTextWatcher() {

        override fun onTextChanged(value: CharSequence, start: Int, before: Int, count: Int) {

            action.invoke(value)
        }
    })
}