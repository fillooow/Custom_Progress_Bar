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
     * менять цветовой ресурс шкалы деления прогресса
     */
    @ColorRes
    var progressColorResId: Int = R.color.kit_brand

    /**
     * Позволяет указать, следует ли использовать в качестве
     * цветового ресурса прогресса поле [rainbowColorResId]
     *
     * true - если нужно, чтобы цвет прогресса менялся на лету в зависимости
     * от текущего значения прогресса - [progress]. В таком случае, ресурс цвета
     * регулируется полем [rainbowColorResId]
     *
     * false - если нужно, чтобы цвет прогресса не изменялся на лету. В таком случае,
     * регулировать цвет прогресса можно полем [progressColorResId]
     */
    var useRainbowColorResId: Boolean = false

    /**
     * Позволяет задать в качестве цветового ресурса прогресса ресурс, который
     * будет на лету меняться в зависимости от прогресса в текущий момент времени
     */
    private val rainbowColorResId: Int
        get() = when (progress) {

            in 0f .. 25f -> R.color.kit_success
            in 25f .. 50f -> R.color.kit_warning
            in 50f .. 100f -> R.color.kit_alert

            else -> R.color.kit_alert
        }

    override val foregroundPaintColorResId: Int
        get() = if (useRainbowColorResId) rainbowColorResId else progressColorResId

    protected abstract val visibleDivisions: Int

    /**
     * Интервал, хранящий в себе индексы делений шкал. Используется при расчетах.
     *
     * Для удобства рассчетов и отрисовки подразумеваем, что у нас на 2 элемента больше,
     * чем указано в [visibleDivisions]. Так удобнее вычислять и отрисовывать граничные
     * деления шкалы прогресс бара.
     */
    protected val divisionsRange: IntRange
        get() = 0 .. visibleDivisions + 1

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
    protected val regularItemTopOffset: Float
        get() = (bigDivisionHeight - regularDivisionHeight) / 2

    protected abstract val bigDivision: RectF
    protected abstract val regularDivision: RectF

    /**
     * Краска, с помощью которой отрисовываются деления шкалы
     */
    protected abstract val divisionPaint: Paint

    /**
     * Переводит [progress] в деления шкалы прогресса SpeedometerProgressView.
     *
     * Из-за удобства вычислений, подразумеваем, что имеется
     * нулевое деление шкалы (которое не будет отрисовано). Поэтому,
     * в формуле ниже дважды добавляется единица в разных местах.
     */
    protected fun calculateProgressDivisions(): Int {

        return (progressPercents * (visibleDivisions + 1f)).roundToInt() + 1
    }

    protected fun Canvas.drawDivisionAtPosition(divisionPosition: Int) {

        when (divisionPosition.rem(bigDivisionPeriodicity) != 0) {

            true -> drawDivision(regularDivision)
            false -> drawDivision(bigDivision)
        }
    }

    private fun Canvas.drawDivision(divisionRect: RectF) {
        drawRoundRect(divisionRect, divisionRadius, divisionRadius, divisionPaint)
    }
}