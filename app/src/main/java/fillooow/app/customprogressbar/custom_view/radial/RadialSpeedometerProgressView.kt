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

private const val VISIBLE_DIVISIONS = 51f

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

    private val bigDivisionHeight = asPixels(R.dimen.radial_speedometer_progress_bar_big_division_height)
    private val bigDivisionWidth = asPixels(R.dimen.radial_speedometer_progress_bar_division_width)

    private val regularDivisionHeight = asPixels(R.dimen.radial_speedometer_progress_bar_regular_division_height)
    private val regularDivisionWidth = asPixels(R.dimen.radial_speedometer_progress_bar_division_width)

    private val divisionRadius = asPixels(R.dimen.radial_speedometer_progress_bar_division_radius)

    private val circleRadius = viewHeight / 2f - bigDivisionHeight / 2f
    private val circleCenter = viewHeight / 2f
    // todo: заменить на offsetName
    private val rect = RectF(bigDivisionHeight / 2, bigDivisionHeight / 2, viewWidth.toFloat() - bigDivisionHeight / 2f, viewHeight.toFloat() - bigDivisionHeight / 2f)

    private val divisionRange = 0 .. 52

    private val regularDivisionTopOffsetCalculated = (bigDivisionHeight - regularDivisionHeight) / 2

    /**
     * Существует, так как у нас итем рисуется не с серидины, а с угла
     * поэтому, нужно сместить всю вью на середину самой себя
     */
    private val horizontalDivisionOffset = bigDivisionWidth / 2

    private val bigDivision = RectF(- horizontalDivisionOffset, 0f, bigDivisionWidth - horizontalDivisionOffset, bigDivisionHeight)
    private val regularDivision = RectF(- horizontalDivisionOffset, regularDivisionTopOffsetCalculated, regularDivisionWidth - horizontalDivisionOffset, regularDivisionHeight + regularDivisionTopOffsetCalculated)

    private val testDivisionPaint = Paint().apply {

        style = Paint.Style.FILL
        color = Color.RED
        isAntiAlias = true
    }

    override fun Canvas.drawProgress() {

//        drawTestHelpers()
        drawSpeedometerFirstVersion()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        updateLayoutParams {
            width = viewWidth
            height = viewHeight
//            setBackgroundColor(Color.CYAN)
        }
    }

    private fun asPixels(@DimenRes dimensionResource: Int) = resources.getDimension(dimensionResource)

    private fun progressInt() = (progress / 100f * (VISIBLE_DIVISIONS + 1f)).roundToInt()

    private fun Canvas.drawSpeedometerFirstVersion() {

        for (divisionPosition in divisionRange) {

            save()

            if ((divisionPosition == divisionRange.first) or (divisionPosition == divisionRange.last)) continue

            testDivisionPaint.color = when (divisionPosition <= progressInt()) {

                true -> foregroundPaint.color
                false -> backgroundPaint.color
            }

            val handleCenterX = circleCenter + (bigDivisionHeight / 2 + circleRadius) * cos((ARC_START_ANGLE + ARC_ANGLE_BETWEEN_ITEMS * (divisionPosition - 1)).toRadians())
            val handleCenterY = circleCenter + (bigDivisionHeight / 2 + circleRadius) * sin((ARC_START_ANGLE + ARC_ANGLE_BETWEEN_ITEMS * (divisionPosition - 1)).toRadians())

            translate(handleCenterX, handleCenterY)
            /**
             * divisionPosition - 1 <- так как нам не нужен сдвиг бля первого элемента
             * + 90f <- так как по умолчанию палочка рисуется сверху вниз (то есть, вертикально),
             * а отрисовка производится из правого угла точка (2 * circleRadius, 0), где точка имеет координаты (x, y)
             */
            rotate(ARC_START_ANGLE + 90f + 5f * (divisionPosition - 1))
            if (divisionPosition.rem(13) != 0) drawRoundRect(regularDivision, divisionRadius, divisionRadius, testDivisionPaint) else drawRoundRect(bigDivision, divisionRadius, divisionRadius, testDivisionPaint)
            restore()
        }
    }

    private fun Canvas.drawTestHelpers() {

        drawTestRadialArcs()
        drawTestCenterLines()
    }

    private fun Canvas.drawTestCenterLines() {

        drawLine(bigDivisionHeight / 2, circleRadius + bigDivisionHeight / 2, 2 * circleRadius + bigDivisionHeight / 2, circleRadius + bigDivisionHeight / 2, Paint().apply { color = Color.BLACK })
        drawLine(circleRadius + bigDivisionHeight / 2, bigDivisionHeight / 2, circleRadius + bigDivisionHeight / 2, 2 * circleRadius + bigDivisionHeight / 2, Paint().apply { color = Color.BLACK })
    }

    private fun Canvas.drawTestRadialArcs() {

        val progressSweepAngle = ARC_ANGLE_LENGTH * progress / MAX_PROGRESS_VALUE

        drawArc(rect, ARC_START_ANGLE, ARC_ANGLE_LENGTH, false, backgroundPaint.apply { strokeCap = Paint.Cap.SQUARE; strokeWidth = bigDivisionWidth })
        drawArc(rect, ARC_START_ANGLE, progressSweepAngle, false, foregroundPaint.apply { strokeCap = Paint.Cap.SQUARE; strokeWidth = bigDivisionWidth })

    }
}