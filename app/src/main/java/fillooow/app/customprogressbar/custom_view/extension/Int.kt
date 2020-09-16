package fillooow.app.customprogressbar.custom_view.extension

import android.content.res.ColorStateList
import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import androidx.core.graphics.ColorUtils

private const val ALPHA_TRANSPARENT = 0x0L
private const val ALPHA_OPAQUE = 0xFFL

@ColorInt
infix fun @receiver:ColorInt Int.withAlpha(@IntRange(from = ALPHA_TRANSPARENT, to = ALPHA_OPAQUE) alpha: Int): Int {

    return ColorUtils.setAlphaComponent(this, alpha)
}

inline val @receiver:ColorInt Int.asColorStateList: ColorStateList
    get() = ColorStateList.valueOf(this)