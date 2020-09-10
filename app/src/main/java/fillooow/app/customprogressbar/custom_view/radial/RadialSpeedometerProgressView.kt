package fillooow.app.customprogressbar.custom_view.radial

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.annotation.DimenRes
import androidx.core.view.updateLayoutParams
import fillooow.app.customprogressbar.R
import fillooow.app.customprogressbar.custom_view.base.BaseProgressView
import fillooow.app.customprogressbar.custom_view.extension.toRadians
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

private const val VISIBLE_ITEMS = 51f

class RadialSpeedometerProgressView @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : BaseProgressView(context, attrs, defStyleAttr) {

    private companion object {

        private const val ARC_START_ANGLE = 145f
        private const val ARC_ANGLE_LENGTH = 250f
        private const val ARC_ANGLE_BETWEEN_ITEMS = 5f
    }

    override val strokeWidthResId: Int = R.dimen.radial_speedometer_progress_bar_stroke_width
    override val backgroundPaintColorResId: Int = R.color.kit_grey_300
    override val foregroundPaintColorResId: Int = R.color.kit_brand

    private val viewWidth = resources.getDimensionPixelSize(R.dimen.radial_speedometer_progress_bar_width)
    private val viewHeight = resources.getDimensionPixelSize(R.dimen.radial_speedometer_progress_bar_height)

    private val bigItemHeight = asPixels(R.dimen.radial_speedometer_progress_bar_big_item_height)
    private val bigItemWidth = asPixels(R.dimen.radial_speedometer_progress_bar_item_width)

    private val regularItemHeight = asPixels(R.dimen.radial_speedometer_progress_bar_regular_item_height)
    private val regularItemWidth = asPixels(R.dimen.radial_speedometer_progress_bar_item_width)

    private val itemRadius = asPixels(R.dimen.radial_speedometer_progress_bar_item_radius)

    private val circleRadius = viewHeight / 2f - bigItemHeight / 2f
    private val circleCenter = viewHeight / 2f
    // todo: заменить на offsetName
    private val rect = RectF(bigItemHeight / 2, bigItemHeight / 2, viewWidth.toFloat() - bigItemHeight / 2f, viewHeight.toFloat() - bigItemHeight / 2f)

    private val itemsRange = 0 .. 52

    private val regularItemTopOffsetCalculated = (bigItemHeight - regularItemHeight) / 2

    /**
     * Существует, так как у нас итем рисуется не с серидины, а с угла
     * поэтому, нужно сместить всю вью на середину самой себя
     */
    private val horizontalItemOffset = bigItemWidth / 2

    private val bigItem = RectF(- horizontalItemOffset, 0f, bigItemWidth - horizontalItemOffset, bigItemHeight)
    private val regularItem = RectF(- horizontalItemOffset, regularItemTopOffsetCalculated, regularItemWidth - horizontalItemOffset, regularItemHeight + regularItemTopOffsetCalculated)

    private val testItemPaint = Paint().apply {

        style = Paint.Style.FILL
        color = Color.RED
        isAntiAlias = true
    }

    override fun Canvas.drawProgress() {

        val progressSweepAngle = ARC_ANGLE_LENGTH * progress / MAX_PROGRESS_VALUE

        drawLine(bigItemHeight / 2, circleRadius + bigItemHeight / 2, 2 * circleRadius + bigItemHeight / 2, circleRadius + bigItemHeight / 2, Paint().apply { color = Color.BLACK })
        drawLine(circleRadius + bigItemHeight / 2, bigItemHeight / 2, circleRadius + bigItemHeight / 2, 2 * circleRadius + bigItemHeight / 2, Paint().apply { color = Color.BLACK })

//        drawArc(rect, ARC_START_ANGLE, ARC_ANGLE_LENGTH, false, backgroundPaint.apply { strokeCap = Paint.Cap.SQUARE; strokeWidth = bigItemWidth })
//        drawArc(rect, ARC_START_ANGLE, progressSweepAngle, false, foregroundPaint.apply { strokeCap = Paint.Cap.SQUARE; strokeWidth = bigItemWidth })

//        val currentProgressX = circleCenter + circleRadius * cos((ARC_START_ANGLE + progressSweepAngle).toRadians())
//        val currentProgressY = circleCenter + circleRadius * sin((ARC_START_ANGLE + progressSweepAngle).toRadians())
        drawSpeedometerFirstVersion()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        updateLayoutParams {
            width = viewWidth
            height = viewHeight
            setBackgroundColor(Color.CYAN)
        }
    }

    private fun asPixels(@DimenRes dimensionResource: Int) = resources.getDimension(dimensionResource)

    private fun progressInt() = (progress / 100f * (VISIBLE_ITEMS + 1f)).roundToInt()

    private fun Canvas.drawSpeedometerFirstVersion() {


        for (itemPosition in itemsRange) {

            save()

            if ((itemPosition == itemsRange.first) or (itemPosition == itemsRange.last)) continue

            testItemPaint.color = when (itemPosition <= progressInt()) {

                true -> foregroundPaint.color
                false -> backgroundPaint.color
            }

            val handleCenterX = circleCenter + (bigItemHeight / 2 + circleRadius) * cos((ARC_START_ANGLE + ARC_ANGLE_BETWEEN_ITEMS * (itemPosition - 1)).toRadians())
            val handleCenterY = circleCenter + (bigItemHeight / 2 + circleRadius) * sin((ARC_START_ANGLE + ARC_ANGLE_BETWEEN_ITEMS * (itemPosition - 1)).toRadians())

            translate(handleCenterX, handleCenterY)
            /**
             * itemPosition - 1 <- так как нам не нужен сдвиг бля первого элемента
             * + 90f <- так как по умолчанию палочка рисуется сверху вниз (то есть, вертикально),
             * а отрисовка производится из правого угла точка (2 * circleRadius, 0), где точка имеет координаты (x, y)
             */
            rotate(ARC_START_ANGLE + 90f + 5f * (itemPosition - 1))
            if (itemPosition.rem(13) != 0) drawRoundRect(regularItem, itemRadius, itemRadius, testItemPaint) else drawRoundRect(bigItem, itemRadius, itemRadius, testItemPaint)
            restore()
        }
    }
}