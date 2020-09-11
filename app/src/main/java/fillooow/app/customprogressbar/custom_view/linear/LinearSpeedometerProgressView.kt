package fillooow.app.customprogressbar.custom_view.linear

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.annotation.DimenRes
import androidx.core.view.updateLayoutParams
import fillooow.app.customprogressbar.R
import fillooow.app.customprogressbar.custom_view.base.BaseProgressView
import kotlin.math.roundToInt

/**
 * Количество видимых (рисуемых) шкал делений спидометра.
 *
 * [Float] - дабы избегать излишних операций приведения типа при расчетах.
 */
private const val VISIBLE_DIVISIONS = 27f

/**
 * Каждое седьмое деление шкалы должно быть большим - [LinearSpeedometerProgressView.bigDivision]
 */
private const val BIG_DIVISION_REGULARITY = 7

class LinearSpeedometerProgressView @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : BaseProgressView(context, attrs, defStyleAttr) {

    override val strokeWidthResId: Int = R.dimen.linear_speedometer_progress_bar_stroke_width
    override val backgroundPaintColorResId: Int = R.color.kit_grey_300
    override val foregroundPaintColorResId: Int = R.color.kit_brand

    private val regularDivisionVerticalOffset = asPixels(
        R.dimen.linear_speedometer_progress_bar_regular_division_vertical_offset
    )

    private val regularDivisionRadius = asPixels(R.dimen.linear_speedometer_progress_bar_division_radius)

    private val regularDivisionHeight = asPixels(R.dimen.linear_speedometer_progress_bar_regular_division_height)
    private val bigDivisionHeight = asPixels(R.dimen.linear_speedometer_progress_bar_big_division_height)
    private val divisionWidth = asPixels(R.dimen.linear_speedometer_progress_bar_division_width)

    /**
     * Расстояние между соседними шкалами. Вычисляется
     * динамически в [onMeasure], так как зависит от ширины экрана.
     */
    private var horizontalDivisionOffset = 0f

    /**
     * Так как [bigDivision] выше, чем [regularDivision],
     * нужно добавить отступ сверху для [regularDivision].
     */
    private val regularItemTopOffset = (bigDivisionHeight - regularDivisionHeight) / 2

    private val bigDivision = RectF(0f, 0f, divisionWidth, bigDivisionHeight)
    private val regularDivision = RectF(0f, regularDivisionVerticalOffset, divisionWidth,
                                        regularDivisionHeight + regularItemTopOffset)

    private val divisionPaint = Paint().apply {

        style = Paint.Style.FILL
        color = backgroundPaint.color
        isAntiAlias = true
    }

    /**
     * Для удобства рассчетов и отрисовки подразумеваем, что у нас на 2 элемента больше,
     * чем указано в [VISIBLE_DIVISIONS]. Так удобнее вычислять и отрисовывать граничные
     * деления шкалы прогресс бара.
     */
    private val divisionsRange = 0 .. 28

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
             * для рассчетов текущего прогресса и корректного отрисовки видимых делений шкалы.
             */
            if ((divisionPosition == divisionsRange.first) or (divisionPosition == divisionsRange.last)) continue

            if ((divisionPosition == calculateProgressDivisions()) or (calculateProgressDivisions() == 0)) {
                divisionPaint.color = backgroundPaint.color
            }

            if (divisionPosition.rem(BIG_DIVISION_REGULARITY) != 0) drawRegularDivision() else drawBigDivision()

            translate(horizontalDivisionOffset + divisionWidth, 0f)
        }
        restore()
    }

    private fun Canvas.drawBigDivision() = drawDivision(bigDivision)
    private fun Canvas.drawRegularDivision() = drawDivision(regularDivision)

    private fun Canvas.drawDivision(divisionRect: RectF) {
        drawRoundRect(divisionRect, regularDivisionRadius, regularDivisionRadius, divisionPaint)
    }

    private fun asPixels(@DimenRes dimensionResource: Int) = resources.getDimension(dimensionResource)

    /**
     * Считает расстояние между делениями шкал в зависимости от ширины экрана
     * доступной для отрисовки [LinearSpeedometerProgressView].
     *
     * Опирается на [getMeasuredWidth]
     */
    private fun calculateDivisionOffset(): Float {

        return (measuredWidth - divisionWidth * VISIBLE_DIVISIONS) / (VISIBLE_DIVISIONS - 1f)
    }

    /**
     * Переводит [progress] в деления шкалы прогресса.
     */
    private fun calculateProgressDivisions() = (progress / 100f * (VISIBLE_DIVISIONS + 1f)).roundToInt()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        updateLayoutParams { height = resolvedStrokeWidth }
    }
}