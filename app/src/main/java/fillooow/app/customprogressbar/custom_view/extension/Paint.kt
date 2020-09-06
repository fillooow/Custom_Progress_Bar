package fillooow.app.customprogressbar.custom_view.extension

import android.content.Context
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat
import fillooow.app.customprogressbar.R

/**
 * Создание краски для рисования на канве.
 * Предопределена установка краски.
 * Все остальное можно поменять через [builder].
 */
inline fun createPaint(context: Context,
                       @ColorRes colorResId: Int = R.color.kit_brand,
                       crossinline builder: Paint.() -> Unit = {}): Paint = Paint(ANTI_ALIAS_FLAG).apply {

    color = ContextCompat.getColor(context, colorResId)
    builder.invoke(this)
}

inline fun createStrokePaint(context: Context,
                             @DimenRes strokeWidthResId: Int,
                             @ColorRes colorResId: Int = R.color.kit_brand,
                             crossinline builder: Paint.() -> Unit = {}): Paint = createPaint(context, colorResId) {

    style = Paint.Style.STROKE
    strokeCap = Paint.Cap.ROUND
    strokeWidth = context.resources.getDimensionPixelSize(strokeWidthResId).toFloat()
    builder.invoke(this)
}