package fillooow.app.customprogressbar.custom_view.base

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import androidx.annotation.ColorRes
import fillooow.app.customprogressbar.R
import kotlin.math.roundToInt

/**
 * Базовый класс для ProgressView типа Speedometer
 *
 * Speedometer состоит из шкал делений двух типов:
 *  большого - [bigDivision]
 *  обычного - [regularDivision]
 *
 * Углы шкал делений должны быть закруглены с радиусом [divisionRadius]
 */
abstract class BaseSpeedometerProgressView @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : BaseProgressView(context, attrs, defStyleAttr) {

    /**
     * Публичная переменная, с помощью которой можно
     * менять цвет шкалы деления прогресса
     */
    @ColorRes
    var progressColorResId: Int = R.color.kit_brand

    override val foregroundPaintColorResId: Int
        get() = progressColorResId

    override var specifiedProgress = 0f
        set(value) {

            field = value
            updateProgressColor()
        }

    /**
     * Количество видимых (рисуемых) шкал делений спидометра.
     */
    protected abstract val visibleDivisions: Int

    /**
     * Интервал, хранящий в себе индексы делений шкал. Используется при расчетах.
     */
    protected abstract val divisionsRange: IntRange

    /**
     * В спидометре большое деление шкалы - [bigDivision] - отрисовывается
     * с некоторой периодичностью, указанной в этой переменной.
     */
    protected abstract val bigDivisionPeriodicity: Int

    protected abstract val divisionRadius: Float
    protected abstract val divisionWidth: Float

    protected abstract val bigDivisionHeight: Float
    protected abstract val regularDivisionHeight: Float

    /**
     * Так как [bigDivision] выше, чем [regularDivision],
     * нужно добавить отступ сверху для [regularDivision].
     */
    protected abstract val regularItemTopOffset: Float

    protected abstract val bigDivision: RectF
    protected abstract val regularDivision: RectF

    /**
     * Краска, с помощью которой отрисовываются деления шкалы
     */
    protected abstract val divisionPaint: Paint

    /**
     * Переводит [progress] в деления шкалы прогресса SpeedometerProgressView.
     */
    protected fun calculateProgressDivisions() = (progress / 100f * (visibleDivisions + 1f)).roundToInt()

    protected fun Canvas.drawDivisionAtPosition(divisionPosition: Int) {

        when (divisionPosition.rem(bigDivisionPeriodicity) != 0) {

            true -> drawDivision(regularDivision)
            false -> drawDivision(bigDivision)
        }
    }

    private fun Canvas.drawDivision(divisionRect: RectF) {
        drawRoundRect(divisionRect, divisionRadius, divisionRadius, divisionPaint)
    }

    private fun updateProgressColor() {

        progressColorResId = when (specifiedProgress) {

            in 0f .. 25f -> R.color.kit_success
            in 25f .. 50f -> R.color.kit_warning
            in 50f .. 100f -> R.color.kit_alert

            else -> R.color.kit_alert
        }
    }
}