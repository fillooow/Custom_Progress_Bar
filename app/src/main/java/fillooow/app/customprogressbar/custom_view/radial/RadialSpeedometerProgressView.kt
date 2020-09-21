package fillooow.app.customprogressbar.custom_view.radial

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.core.view.updateLayoutParams
import fillooow.app.customprogressbar.R
import fillooow.app.customprogressbar.custom_view.base.BaseSpeedometerProgressView
import fillooow.app.customprogressbar.custom_view.extension.getDimension
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
        private const val ARC_ANGLE_BETWEEN_ITEMS = 5f
        private const val ORIENTATION_CHANGE_ANGLE = 90f
    }

    private val viewWidth = resources.getDimensionPixelSize(R.dimen.radial_speedometer_progress_bar_width)
    private val viewHeight = resources.getDimensionPixelSize(R.dimen.radial_speedometer_progress_bar_height)

    override val strokeWidthResId: Int = R.dimen.radial_speedometer_progress_bar_stroke_width
    override val backgroundPaintColorResId: Int = R.color.kit_grey_300

    override val bigDivisionHeight = getDimension(R.dimen.radial_speedometer_progress_bar_big_division_height)
    override val regularDivisionHeight = getDimension(R.dimen.radial_speedometer_progress_bar_regular_division_height)
    override val divisionWidth = getDimension(R.dimen.radial_speedometer_progress_bar_division_width)

    override val visibleDivisions = 51
    override val bigDivisionPeriodicity = 13

    override val divisionRadius = getDimension(R.dimen.radial_speedometer_progress_bar_division_radius)

    private val circleCenter = viewHeight / 2f
    private val circleRadius = viewHeight / 2f - bigDivisionHeight / 2f

    /**
     * Так как прямоугольник [RectF] рисуется от левого верхнего угла до правого нижнего,
     * происходит смещение на половину ширины деления шкалы. Данное поле нивелирует это смещение.
     */
    private val horizontalDivisionOffset = divisionWidth / 2

    /**
     * Расстояние от центра окружности до левого верхнего угла,
     * с которого начнет отрисовываться деление шкалы.
     */
    private val divisionLeftTopCornerFromCenterOffset = bigDivisionHeight / 2 + circleRadius

    override val bigDivision = RectF(
        -horizontalDivisionOffset, 0f,
        divisionWidth - horizontalDivisionOffset, bigDivisionHeight
    )

    override val regularDivision = RectF(
        -horizontalDivisionOffset, regularItemTopOffset,
        divisionWidth - horizontalDivisionOffset,
        regularDivisionHeight + regularItemTopOffset
    )

    override val divisionPaint = Paint().apply {

        style = Paint.Style.FILL
        color = backgroundPaint.color
        isAntiAlias = true
    }

    override fun Canvas.drawProgress() {

        divisionPaint.color = foregroundPaint.color

        for (divisionPosition in divisionsRange) {

            save()

            /**
             * Нулевое и последнее деления шкалы не отрисовываются, они нужны лишь
             * для рассчетов текущего прогресса и корректной отрисовки видимых делений шкалы.
             */
            if ((divisionPosition == divisionsRange.first) or (divisionPosition == divisionsRange.last)) continue

            if ((divisionPosition == calculateProgressDivisions()) or (calculateProgressDivisions() == 0)) {
                divisionPaint.color = backgroundPaint.color
            }

            val divisionLeftTopCornerX = circleCenter + calculateCircumferentialOffsetByPositionX(divisionPosition)
            val divisionLeftTopCornerY = circleCenter + calculateCircumferentialOffsetByPositionY(divisionPosition)

            translate(divisionLeftTopCornerX, divisionLeftTopCornerY)

            rotate(ARC_START_ANGLE + ORIENTATION_CHANGE_ANGLE + ARC_ANGLE_BETWEEN_ITEMS * (divisionPosition - 1))

            drawDivisionAtPosition(divisionPosition)
            restore()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        updateLayoutParams {
            width = viewWidth
            height = viewHeight
        }
    }

    /**
     * Рассчитывает смещение по окружности для X координаты
     * по номеру позиции деления шкалы.
     */
    private fun calculateCircumferentialOffsetByPositionX(divisionPosition: Int): Float {

        return divisionLeftTopCornerFromCenterOffset * calculateRadiansOffsetByPositionX(divisionPosition)
    }

    /**
     * Рассчитывает смещение по окружности для Y координаты
     * по номеру позиции деления шкалы.
     */
    private fun calculateCircumferentialOffsetByPositionY(divisionPosition: Int): Float {

        return divisionLeftTopCornerFromCenterOffset * calculateRadiansOffsetByPositionY(divisionPosition)
    }

    private fun calculateRadiansOffsetByPositionX(divisionPosition: Int): Float {

        return cos(toRadians(ARC_START_ANGLE + ARC_ANGLE_BETWEEN_ITEMS * (divisionPosition - 1)))
    }

    private fun calculateRadiansOffsetByPositionY(divisionPosition: Int): Float {

        return sin(toRadians(ARC_START_ANGLE + ARC_ANGLE_BETWEEN_ITEMS * (divisionPosition - 1)))
    }
}