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

    private val regularItemRadiusPx = asPixels(R.dimen.linear_speedometer_progress_bar_item_radius)

    private val regularItemHeightPx = asPixels(R.dimen.linear_speedometer_progress_bar_regular_item_height)
    private val bigItemHeightPx = asPixels(R.dimen.linear_speedometer_progress_bar_big_item_height)
    private val itemWidthPx = asPixels(R.dimen.linear_speedometer_progress_bar_item_width)

    private var horizontalItemsOffset = 0f

    /**
     * Так как [bigItem] выше, чем [regularItem], нужно добавить отступ сверху для [regularItem],
     * чтобы отцентрировать все item-ы в [LinearSpeedometerProgressView].
     */
    private val regularItemTopOffsetCalculated = (bigItemHeightPx - regularItemHeightPx) / 2

    private val regularItem = RectF(0f, regularItemVerticalOffsetPx, itemWidthPx, regularItemHeightPx + regularItemTopOffsetCalculated)
    private val bigItem = RectF(0f, 0f, itemWidthPx, bigItemHeightPx)

    private val rectPaint = Paint().apply {

        style = Paint.Style.FILL
        color = backgroundPaint.color
        isAntiAlias = true
    }

    /**
     * Всего 27 элементов.
     *
     * Из них:
     * [regularItem] - 24,
     * [bigItem] - 3.
     */
    private val itemsRange = 1 .. 27

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        horizontalItemsOffset = calculateItemsOffset()
    }

    override fun Canvas.drawProgress() {

        save()
        for (itemPosition in itemsRange) {

            rectPaint.color = when (itemPosition <= animatedProgress.toInt()) {

                true -> foregroundPaint.color
                false -> backgroundPaint.color
            }

            if (itemPosition.rem(7) != 0) drawRegularItem() else drawBigItem()
            translate(horizontalItemsOffset + itemWidthPx, 0f)
        }
//        drawLine(0f, bigItemHeightPx / 2 , measuredWidth.toFloat(), bigItemHeightPx / 2, rectPaint.apply { color = getColor(R.color.colorAccent)})
        restore()
    }

    private fun Canvas.drawRegularItem() = drawRoundRect(regularItem, regularItemRadiusPx, regularItemRadiusPx, rectPaint)

    private fun Canvas.drawBigItem() = drawRoundRect(bigItem, regularItemRadiusPx, regularItemRadiusPx, rectPaint)

    private fun asPixels(@DimenRes dimensionResource: Int) = resources.getDimension(dimensionResource)

    /**
     * Считает расстояние между item-ами в зависимости от ширины экрана
     * доступной для отрисовки [LinearSpeedometerProgressView].
     * Опирается на [getMeasuredWidth]
     */
    private fun calculateItemsOffset(): Float = (measuredWidth - itemWidthPx * itemsRange.last) / (itemsRange.last - 1)
}



/*
private class BigItem : Item() {

    override fun drawItem() {
        TODO("Not yet implemented")
    }
}

private abstract class Item() {

    abstract fun drawItem()
}
*/