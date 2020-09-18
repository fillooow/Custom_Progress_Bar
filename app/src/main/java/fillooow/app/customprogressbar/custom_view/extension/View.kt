package fillooow.app.customprogressbar.custom_view.extension

import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

internal fun View.getColor(@ColorRes colorResId: Int) = ContextCompat.getColor(context, colorResId)

internal fun View.getColorStateList(@ColorRes colorResId: Int) = ContextCompat.getColorStateList(context, colorResId)

internal fun View.getFont(@FontRes fontResId: Int) = ResourcesCompat.getFont(context, fontResId)!!

fun View.getDimension(@DimenRes dimensionResource: Int) = resources.getDimension(dimensionResource)

internal fun View.increaseHitBox(@DimenRes increaseSizeResId: Int) {

    val extraPadding = resources.getDimensionPixelSize(increaseSizeResId)
    increaseHitBox(extraPadding.toFloat())
}

internal fun View.increaseHitBox(increaseSizePx: Float) {

    val parent = parent as? View ?: return
    val extraPadding = increaseSizePx.toInt()

    parent.post {

        val rect = Rect()
        getHitRect(rect)

        rect.top -= extraPadding
        rect.bottom += extraPadding
        rect.left -= extraPadding
        rect.right += extraPadding

        parent.touchDelegate = TouchDelegate(rect, this)
    }
}