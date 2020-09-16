package fillooow.app.customprogressbar.custom_view.extension

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

internal fun View.getColor(@ColorRes colorResId: Int) = ContextCompat.getColor(context, colorResId)

internal fun View.getColorStateList(@ColorRes colorResId: Int) = ContextCompat.getColorStateList(context, colorResId)

internal fun View.getFont(@FontRes fontResId: Int) = ResourcesCompat.getFont(context, fontResId)!!

fun View.getPixels(@DimenRes dimensionResource: Int) = resources.getDimension(dimensionResource)