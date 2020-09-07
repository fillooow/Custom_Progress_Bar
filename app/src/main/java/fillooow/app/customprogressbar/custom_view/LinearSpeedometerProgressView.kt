package fillooow.app.customprogressbar.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.annotation.DimenRes
import fillooow.app.customprogressbar.R

class LinearSpeedometerProgressView @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : BaseProgressView(context, attrs, defStyleAttr) {

    override val strokeWidthResId: Int = R.dimen.linear_speedometer_progress_bar_stroke_width
    override val backgroundPaintColorResId: Int = R.color.kit_grey_300
    override val foregroundPaintColorResId: Int = R.color.kit_brand

    private val regularItemVerticalOffsetPx = asPixels(R.dimen.linear_speedometer_progress_bar_regular_item_vertical_offset)
    private val itemsHorizontalOffsetPx = asPixels(R.dimen.linear_speedometer_progress_bar_item_horizontal_offset)

    private val regularItemRadiusPx = asPixels(R.dimen.linear_speedometer_progress_bar_item_radius)

    private val regularItemHeightPx = asPixels(R.dimen.linear_speedometer_progress_bar_regular_item_height)
    private val regularItemWidthPx = asPixels(R.dimen.linear_speedometer_progress_bar_regular_item_width)

    private val bigItemHeightPx = asPixels(R.dimen.linear_speedometer_progress_bar_big_item_height)
    private val bigItemWidthPx = asPixels(R.dimen.linear_speedometer_progress_bar_big_item_width)

    /**
     * Так как [bigItem] выше, чем [regularItem], нужно добавить отступ сверху для [regularItem],
     * чтобы отцентрировать все item-ы в [LinearSpeedometerProgressView].
     */
    private val regularItemTopOffsetCalculated = (bigItemHeightPx - regularItemHeightPx) / 2

    private val regularItem = RectF(0f, regularItemVerticalOffsetPx, regularItemWidthPx, regularItemHeightPx + regularItemTopOffsetCalculated)
    private val bigItem = RectF(0f, 0f, bigItemWidthPx, bigItemHeightPx)

    private val rectPaint = Paint().apply {

        style = Paint.Style.FILL
        color = backgroundPaint.color
        isAntiAlias = true
    }

    override fun Canvas.drawProgress() {

        save()
        for (i in 1 .. 27) {

            rectPaint.color = when (i <= animatedProgress.toInt()) {

                true -> foregroundPaint.color
                false -> backgroundPaint.color
            }

            if (i.rem(7) != 0) drawRegularItem() else drawBigItem()
            translate(itemsHorizontalOffsetPx, 0f)
        }
        restore()
    }

    private fun Canvas.drawRegularItem() = drawRoundRect(regularItem, regularItemRadiusPx, regularItemRadiusPx, rectPaint)

    private fun Canvas.drawBigItem() = drawRoundRect(bigItem, regularItemRadiusPx, regularItemRadiusPx, rectPaint)

    private fun asPixels(@DimenRes dimensionResource: Int) = resources.getDimension(dimensionResource)
}