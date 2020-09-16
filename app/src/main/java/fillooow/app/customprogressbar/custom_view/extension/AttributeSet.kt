package fillooow.app.customprogressbar.custom_view.extension

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.annotation.StyleableRes
import fillooow.app.customprogressbar.R
import fillooow.app.customprogressbar.custom_view.text.TochkaTextViewType

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

internal fun AttributeSet.getGlobalTextViewStyle(context: Context,
                                                 defaultStyle: TochkaTextViewType): TochkaTextViewType {
    var style = defaultStyle

    applyStyleable(context, R.styleable.GlobalAttributes) {

        style = getEnum(R.styleable.GlobalAttributes_viewStyle, defaultStyle.id)
    }
    return style
}