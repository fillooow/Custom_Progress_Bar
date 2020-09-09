package fillooow.app.customprogressbar.custom_view.base

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import fillooow.app.customprogressbar.custom_view.extension.createStrokePaint
import fillooow.app.customprogressbar.custom_view.extension.getColor

/**
 * Базовая прогресс вью
 * Генерируются биндинги на [progress] и [animatedProgress]
 * Все прогрессы имеют какой-то background и foreground
 * Для определения этих цветов заведены абстрактные свойства [backgroundPaintColorResId] и [foregroundPaintColorResId]
 * Аниматор используется один и тот же с интерполятором Безье
 *
 * Пользователь класса обязан так же переопределить метод [drawProgress],
 * в котором описать правила отрисовки прогресса на канве.
 */
abstract class BaseProgressView @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : View(context, attrs, defStyleAttr) {

    protected companion object {

        const val MIN_PROGRESS_VALUE = 0f
        const val MAX_PROGRESS_VALUE = 100f

        private const val EDIT_MODE_PROGRESS_VALUE = 50f
    }

    @get:DimenRes
    protected abstract val strokeWidthResId: Int

    @get:ColorRes
    protected abstract val backgroundPaintColorResId: Int

    @get:ColorRes
    protected abstract val foregroundPaintColorResId: Int

    /**
     * Хранит значение [foregroundPaintColorResId] до его последующего изменения.
     */
    @ColorRes
    private var cachedForegroundPaintColorResId: Int? = null

    var animatedProgress: Float
        get() = progress
        set(value) = with(animator) {

            setFloatValues(value)
            start()
        }

    var progress = 0f
        set(value) {
            field = value.coerceIn(
                MIN_PROGRESS_VALUE,
                MAX_PROGRESS_VALUE
            )
            invalidate()
        }

    protected val resolvedStrokeWidth by lazy { resources.getDimensionPixelSize(strokeWidthResId) }

    /**
     * Задняя краска. Это нарисованная полоска с цветом, указанным в [backgroundPaintColorResId], которая визуально
     * находится под [foregroundPaint] и отражает длину прогресс баров, основанных на [BaseProgressView].
     */
    protected open val backgroundPaint by lazy {
        createStrokePaint(context, strokeWidthResId, backgroundPaintColorResId)
    }

    /**
     * Передняя краска. Это нарисованная полоска с цветом, указанным в [foregroundPaintColorResId], которая визуально
     * находится над [backgroundPaint] и отражает текущий прогресс для прогресс баров, основанных на [BaseProgressView].
     */
    protected open val foregroundPaint: Paint
        get() {

            /**
             * Создаем объект краски [Paint] для передней краски во время инициализации
             * объекта [foregroundPaint] или после смены ресурса передней краски [foregroundPaintColorResId]:
             *
             * Кеш передней краски [cachedForegroundPaint] == null, только в случае,
             * если мы не проиницализировали переднюю краску [foregroundPaint]. Поэтому, создаем краску.
             *
             * Если закешированный ресурс краски [cachedForegroundPaintColorResId] не совпадает c текущим
             * ресурсом краски [foregroundPaintColorResId] - нужно пересоздать краску.
             */
            if (cachedForegroundPaint == null) {

                cachedForegroundPaintColorResId = foregroundPaintColorResId
                cachedForegroundPaint = createStrokePaint(context, strokeWidthResId, foregroundPaintColorResId)
            }

            if (cachedForegroundPaintColorResId != foregroundPaintColorResId) {

                cachedForegroundPaint!!.color = getColor(foregroundPaintColorResId)
            }

            return cachedForegroundPaint!!
        }

    /**
     * Хранит значение [foregroundPaint] до его последующего изменения.
     */
    private var cachedForegroundPaint: Paint? = null

    private val animator by lazy {

        ObjectAnimator.ofFloat(this, "progress", progress).apply {
            duration = resources.getInteger(android.R.integer.config_longAnimTime).toLong()
            interpolator =
                BezierInterpolator.getDefaultPrincipleInstance()
        }
    }

    abstract fun Canvas.drawProgress()

    final override fun onDraw(canvas: Canvas) = canvas.drawProgress()

    @CallSuper
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (isInEditMode) progress =
            EDIT_MODE_PROGRESS_VALUE
    }
}