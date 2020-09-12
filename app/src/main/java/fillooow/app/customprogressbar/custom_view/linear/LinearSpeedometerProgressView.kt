package fillooow.app.customprogressbar.custom_view.linear

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.annotation.DimenRes
import androidx.core.view.updateLayoutParams
import fillooow.app.customprogressbar.R
import fillooow.app.customprogressbar.custom_view.base.BaseSpeedometerProgressView

class LinearSpeedometerProgressView @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : BaseSpeedometerProgressView(context, attrs, defStyleAttr) {

    override val strokeWidthResId: Int = R.dimen.linear_speedometer_progress_bar_stroke_width
    override val backgroundPaintColorResId: Int = R.color.kit_grey_300
    override val foregroundPaintColorResId: Int = R.color.kit_brand

    override val visibleDivisions = 27
    override val divisionRadius = asPixels(R.dimen.linear_speedometer_progress_bar_division_radius)

    override val regularDivisionHeight = asPixels(R.dimen.linear_speedometer_progress_bar_regular_division_height)
    override val bigDivisionHeight = asPixels(R.dimen.linear_speedometer_progress_bar_big_division_height)
    override val divisionWidth = asPixels(R.dimen.linear_speedometer_progress_bar_division_width)

    override val regularItemTopOffset = (bigDivisionHeight - regularDivisionHeight) / 2

    override val bigDivision = RectF(0f, 0f, divisionWidth, bigDivisionHeight)
    override val regularDivision = RectF(0f, regularItemTopOffset, divisionWidth,
                                        regularDivisionHeight + regularItemTopOffset)

    override val bigDivisionPeriodicity = 7

    override val divisionPaint = Paint().apply {

        style = Paint.Style.FILL
        color = backgroundPaint.color
        isAntiAlias = true
    }

    /**
     * Для удобства рассчетов и отрисовки подразумеваем, что у нас на 2 элемента больше,
     * чем указано в [visibleDivisions]. Так удобнее вычислять и отрисовывать граничные
     * деления шкалы прогресс бара.
     */
    override val divisionsRange = 0 .. 28

    /**
     * Расстояние между соседними шкалами. Вычисляется
     * динамически в [onMeasure], так как зависит от ширины экрана.
     */
    private var horizontalDivisionOffset = 0f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        horizontalDivisionOffset = calculateDivisionOffset()
    }

    override fun Canvas.drawProgress() {

        save()

        divisionPaint.color = foregroundPaint.color

        for (divisionPosition in divisionsRange) {

            /**
             * Нулевое и последнее деления шкалы не отрисовываются, они нужны лишь
             * для рассчетов текущего прогресса и корректной отрисовки видимых делений шкалы.
             */
            if ((divisionPosition == divisionsRange.first) or (divisionPosition == divisionsRange.last)) continue

            if ((divisionPosition == calculateProgressDivisions()) or (calculateProgressDivisions() == 0)) {
                divisionPaint.color = backgroundPaint.color
            }

            if (divisionPosition.rem(bigDivisionPeriodicity) != 0) drawRegularDivision() else drawBigDivision()

            translate(horizontalDivisionOffset + divisionWidth, 0f)
        }
        restore()
    }

    private fun asPixels(@DimenRes dimensionResource: Int) = resources.getDimension(dimensionResource)

    /**
     * Считает расстояние между делениями шкал в зависимости от ширины экрана
     * доступной для отрисовки [LinearSpeedometerProgressView].
     *
     * Опирается на [getMeasuredWidth]
     */
    private fun calculateDivisionOffset(): Float {

        return (measuredWidth - divisionWidth * visibleDivisions) / (visibleDivisions - 1)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        updateLayoutParams { height = resolvedStrokeWidth }
    }
}