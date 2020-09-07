package fillooow.app.customprogressbar.custom_view.extension

import android.content.Context
import android.util.DisplayMetrics
import kotlin.math.roundToInt

fun Number.dpInt(context: Context): Int {

    val metrics = context.resources.displayMetrics
    val value = this.toFloat() * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)

    return value.roundToInt()
}

fun Number.dpFloat(context: Context): Float {

    val metrics = context.resources.displayMetrics
    return this.toFloat() * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}