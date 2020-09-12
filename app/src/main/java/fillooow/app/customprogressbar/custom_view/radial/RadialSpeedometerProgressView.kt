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
import fillooow.app.customprogressbar.custom_view.base.BaseSpeedometerProgressView
import fillooow.app.customprogressbar.custom_view.extension.toRadians
import kotlin.math.cos
import kotlin.math.sin

class RadialSpeedometerProgressView @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : BaseSpeedometerProgressView(context, attrs, defStyleAttr) {

    private companion object {

        private const val ARC_START_ANGLE = 145f
        private const val ARC_ANGLE_LENGTH = 250f
        private const val ARC_ANGLE_BETWEEN_ITEMS = 5f
    }

    private val viewWidth = resources.getDimensionPixelSize(R.dimen.radial_speedometer_progress_bar_width)
    private val viewHeight = resources.getDimensionPixelSize(R.dimen.radial_speedometer_progress_bar_height)

    override val strokeWidthResId: Int = R.dimen.radial_speedometer_progress_bar_stroke_width
    override val backgroundPaintColorResId: Int = R.color.kit_grey_300
    override val foregroundPaintColorResId: Int = R.color.kit_brand

    override val bigDivisionHeight = asPixels(R.dimen.radial_speedometer_progress_bar_big_division_height)
    override val regularDivisionHeight = asPixels(R.dimen.radial_speedometer_progress_bar_regular_division_height)
    override val divisionWidth = asPixels(R.dimen.radial_speedometer_progress_bar_division_width)

    override val regularItemTopOffset = (bigDivisionHeight - regularDivisionHeight) / 2

    override val visibleDivisions = 51
    override val divisionsRange = 0 .. 52

    override val bigDivisionPeriodicity = 13

    override val divisionRadius = asPixels(R.dimen.radial_speedometer_progress_bar_division_radius)

    private val circleCenter = viewHeight / 2f
    private val circleRadius = viewHeight / 2f - bigDivisionHeight / 2f

    /**
     * Так как прямоугольник [RectF] рисуется от левого верхнего угла до правого нижнего,
     * происходит смещение на половину ширины деления шкалы. Данное поле нивелирует это смещение.
     */
    private val horizontalDivisionOffset = divisionWidth / 2

    override val bigDivision = RectF(- horizontalDivisionOffset, 0f, divisionWidth - horizontalDivisionOffset, bigDivisionHeight)
    override val regularDivision = RectF(- horizontalDivisionOffset, regularItemTopOffset, divisionWidth - horizontalDivisionOffset, regularDivisionHeight + regularItemTopOffset)

    override val divisionPaint = Paint().apply {

        style = Paint.Style.FILL
        color = backgroundPaint.color
        isAntiAlias = true
    }

    override fun Canvas.drawProgress() {

//        drawTestHelpers()
        drawRadialSpeedometer()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        updateLayoutParams {
            width = viewWidth
            height = viewHeight
        }
    }

    private fun asPixels(@DimenRes dimensionResource: Int) = resources.getDimension(dimensionResource)

    private fun Canvas.drawRadialSpeedometer() {

        for (divisionPosition in divisionsRange) {

            save()

            /**
             * Нулевое и последнее деления шкалы не отрисовываются, они нужны лишь
             * для рассчетов текущего прогресса и корректной отрисовки видимых делений шкалы.
             */
            if ((divisionPosition == divisionsRange.first) or (divisionPosition == divisionsRange.last)) continue

            divisionPaint.color = when (divisionPosition <= calculateProgressDivisions()) {

                true -> foregroundPaint.color
                false -> backgroundPaint.color
            }

            val divisionLeftTopCornerX = circleCenter + (bigDivisionHeight / 2 + circleRadius) * cos((ARC_START_ANGLE + ARC_ANGLE_BETWEEN_ITEMS * (divisionPosition - 1)).toRadians())
            val divisionLeftTopCornerY = circleCenter + (bigDivisionHeight / 2 + circleRadius) * sin((ARC_START_ANGLE + ARC_ANGLE_BETWEEN_ITEMS * (divisionPosition - 1)).toRadians())

            translate(divisionLeftTopCornerX, divisionLeftTopCornerY)

            /**
             * divisionPosition - 1 <- так как нам не нужен сдвиг бля первого элемента
             * + 90f <- так как по умолчанию палочка рисуется сверху вниз (то есть, вертикально),
             * а отрисовка производится из правого угла точка (2 * circleRadius, 0), где точка имеет координаты (x, y)
             */
            rotate(ARC_START_ANGLE + 90f + 5f * (divisionPosition - 1))

            if (divisionPosition.rem(bigDivisionPeriodicity) != 0) drawRegularDivision() else drawBigDivision()
            restore()
        }
    }

    private val testRect = RectF(bigDivisionHeight / 2, bigDivisionHeight / 2, viewWidth.toFloat() - bigDivisionHeight / 2f, viewHeight.toFloat() - bigDivisionHeight / 2f)

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

        drawArc(testRect, ARC_START_ANGLE, ARC_ANGLE_LENGTH, false, backgroundPaint.apply { strokeCap = Paint.Cap.SQUARE; strokeWidth = divisionWidth })
        drawArc(testRect, ARC_START_ANGLE, progressSweepAngle, false, foregroundPaint.apply { strokeCap = Paint.Cap.SQUARE; strokeWidth = divisionWidth })
    }
}