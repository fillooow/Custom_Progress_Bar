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

    private val circleRadius = viewHeight / 2f - bigItemHeight / 2f
    private val circleCenter = viewHeight / 2f
    // todo: заменить на offsetName
    private val rect = RectF(bigItemHeight / 2, bigItemHeight / 2, viewWidth.toFloat() - bigItemHeight / 2f, viewHeight.toFloat() - bigItemHeight / 2f)

    private val itemsRange = 0 .. 52

    private val testItemPaint = Paint().apply {

        style = Paint.Style.FILL
        color = Color.RED
        isAntiAlias = true
    }

    override fun Canvas.drawProgress() {

        val progressSweepAngle = ARC_ANGLE_LENGTH * progress / MAX_PROGRESS_VALUE

        drawArc(rect, ARC_START_ANGLE, ARC_ANGLE_LENGTH, false, backgroundPaint.apply { strokeCap = Paint.Cap.SQUARE; strokeWidth = bigItemWidth })
        drawArc(rect, ARC_START_ANGLE, progressSweepAngle, false, foregroundPaint.apply { strokeCap = Paint.Cap.SQUARE; strokeWidth = bigItemWidth })

//        val currentProgressX = circleCenter + circleRadius * cos((ARC_START_ANGLE + progressSweepAngle).toRadians())
//        val currentProgressY = circleCenter + circleRadius * sin((ARC_START_ANGLE + progressSweepAngle).toRadians())
//        drawSpeedometerFirstVersion()
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

            if ((itemPosition == itemsRange.first) or (itemPosition == itemsRange.last)) continue

            testItemPaint.color = when (itemPosition <= progressInt()) {

                true -> foregroundPaint.color
                false -> backgroundPaint.color
            }

            val handleCenterX = circleCenter + circleRadius * cos((ARC_START_ANGLE + ARC_ANGLE_BETWEEN_ITEMS * itemPosition).toRadians())
            val handleCenterY = circleCenter + circleRadius * sin((ARC_START_ANGLE + ARC_ANGLE_BETWEEN_ITEMS * itemPosition).toRadians())

            drawCircle(handleCenterX, handleCenterY, bigItemWidth * 2, testItemPaint)
        }
    }
}