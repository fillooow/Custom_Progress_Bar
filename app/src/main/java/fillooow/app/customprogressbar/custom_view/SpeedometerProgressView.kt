package fillooow.app.customprogressbar.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import fillooow.app.customprogressbar.R
import fillooow.app.customprogressbar.custom_view.extension.dpFloat

class SpeedometerProgressView @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : BaseProgressView(context, attrs, defStyleAttr) {

    override val strokeWidthResId: Int = R.dimen.speedometer_progress_bar_stroke_width
    override val backgroundPaintColorResId: Int = R.color.kit_grey_300
    override val foregroundPaintColorResId: Int = R.color.kit_brand

    private val itemHeight = R.dimen.speedometer_progress_bar_item_height
    private val itemWidth = R.dimen.speedometer_progress_bar_item_width

    private val path = Path()
    private val regularItem = RectF(0f, 2f.asDp(), 2f.asDp(), 11f.asDp())
    private val bigItem = RectF(0f, 0f, 2f.asDp(), 15f.asDp())

    private val rectPaint = Paint().apply {

        style = Paint.Style.FILL
        color = backgroundPaint.color
    }

    private val currentProgress: Int = 17

    override fun Canvas.drawProgress() {

        save()
        for (i in 1 .. 27) {

            rectPaint.color = when (i <= animatedProgress.toInt()) {

                true -> foregroundPaint.color
                false -> backgroundPaint.color
            }

            if (i.rem(7) != 0) drawRegularItem() else drawBigItem()
            translate(11f.dpFloat(context), 0f)
        }
        restore()
    }

    private fun Canvas.drawRegularItem() = drawRoundRect(regularItem, 14f.asDp(), 14f.asDp(), rectPaint)

    private fun Canvas.drawBigItem() = drawRoundRect(bigItem, 14f.asDp(), 14f.asDp(), rectPaint)

    private fun Float.asDp() = dpFloat(context)
}