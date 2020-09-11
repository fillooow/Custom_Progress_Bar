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
 * Количество видимых шкал делений спидометра
 */
private const val VISIBLE_DIVISIONS = 27f

class LinearSpeedometerProgressView @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : BaseProgressView(context, attrs, defStyleAttr) {

    override val strokeWidthResId: Int = R.dimen.linear_speedometer_progress_bar_stroke_width
    override val backgroundPaintColorResId: Int = R.color.kit_grey_300
    override val foregroundPaintColorResId: Int = R.color.kit_brand

    private val regularDivisionVerticalOffset = asPixels(R.dimen.linear_speedometer_progress_bar_regular_division_vertical_offset)

    private val regularDivisionRadius = asPixels(R.dimen.linear_speedometer_progress_bar_division_radius)

    private val regularDivisionHeight = asPixels(R.dimen.linear_speedometer_progress_bar_regular_division_height)
    private val bigDivisionHeight = asPixels(R.dimen.linear_speedometer_progress_bar_big_division_height)
    private val divisionWidth = asPixels(R.dimen.linear_speedometer_progress_bar_division_width)

    /**
     * Расстояние между соседними item-ами. Вычисляется динамически в [onMeasure]
     */
    private var horizontalDivisionOffset = 0f

    /**
     * Так как [bigDivision] выше, чем [regularDivision], нужно добавить отступ сверху для [regularDivision],
     * чтобы отцентрировать все item-ы в [LinearSpeedometerProgressView].
     */
    private val regularItemTopOffsetCalculated = (bigDivisionHeight - regularDivisionHeight) / 2

    private val regularDivision = RectF(0f, regularDivisionVerticalOffset, divisionWidth, regularDivisionHeight + regularItemTopOffsetCalculated)
    private val bigDivision = RectF(0f, 0f, divisionWidth, bigDivisionHeight)

    private val rectPaint = Paint().apply {

        style = Paint.Style.FILL
        color = backgroundPaint.color
        isAntiAlias = true
    }

    /**
     * Всего 27 элементов. (или 29)
     *
     * Из них:
     * [regularDivision] - 24,
     * [bigDivision] - 3.
     *
     * 0 и 28 шкалы делений не отрисовываются
     */
    private val divisionsRange = 0 .. 28

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        horizontalDivisionOffset = calculateDivisionOffset()
    }

    override fun Canvas.drawProgress() {

        save()

        rectPaint.color = foregroundPaint.color

        for (divisionPosition in divisionsRange) {

            /**
             * Из-за округления, необходимо пропускать нулевое деление, которое мы не рисуем
             * Последнее деление нам тоже не нужно
             * Вообще, это все абстракция для удобства расчетов
             */
            if ((divisionPosition == divisionsRange.first) or (divisionPosition == divisionsRange.last)) continue

            if ((divisionPosition == progressInt()) or (progressInt() == 0)) rectPaint.color = backgroundPaint.color

            if (divisionPosition.rem(7) != 0) drawRegularDivision() else drawBigDivision()

            translate(horizontalDivisionOffset + divisionWidth, 0f)
        }
        restore()
    }

    private fun Canvas.drawRegularDivision() = drawRoundRect(regularDivision, regularDivisionRadius, regularDivisionRadius, rectPaint)

    private fun Canvas.drawBigDivision() = drawRoundRect(bigDivision, regularDivisionRadius, regularDivisionRadius, rectPaint)

    private fun asPixels(@DimenRes dimensionResource: Int) = resources.getDimension(dimensionResource)

    /**
     * Считает расстояние между делениями шкал в зависимости от ширины экрана
     * доступной для отрисовки [LinearSpeedometerProgressView].
     * Опирается на [getMeasuredWidth]
     */
    private fun calculateDivisionOffset(): Float = (measuredWidth - divisionWidth * VISIBLE_DIVISIONS) / (VISIBLE_DIVISIONS - 1)

    /**
     * Приходится применять float и [roundToInt], так как существует погрешность
     * при операциях деления/умножения
     */
    private fun progressInt() = (progress / 100f * (VISIBLE_DIVISIONS + 1f)).roundToInt()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        updateLayoutParams { height = resolvedStrokeWidth }
    }
}

// todo: цвета