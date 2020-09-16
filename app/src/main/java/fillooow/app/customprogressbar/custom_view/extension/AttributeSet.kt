package fillooow.app.customprogressbar.custom_view.extension

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.annotation.StyleableRes

fun AttributeSet.getTypedArray(context: Context,
                               @StyleableRes styleableResId: IntArray): TypedArray {

    return context.obtainStyledAttributes(this, styleableResId)
}

inline fun AttributeSet.applyStyleable(context: Context,
                                       @StyleableRes styleableResId: IntArray,
                                       action: TypedArray.() -> Unit) {

    val typedArray = getTypedArray(context, styleableResId)

    typedArray.action()

    typedArray.recycle()
}
