package fillooow.app.customprogressbar.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import fillooow.app.customprogressbar.R

class SpeedometerProgressView @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : BaseProgressView(context, attrs, defStyleAttr) {

    override val strokeWidthResId: Int = R.dimen.speedometer_progress_bar_stroke_width
    override val backgroundPaintColorResId: Int = R.color.kit_grey_300
    override val foregroundPaintColorResId: Int = R.color.kit_brand

    private val path = Path()
    private val rectF = RectF(0f, 0f, 2f, 11f)

    override fun Canvas.drawProgress() {

        save()
        for (i in 1 .. 27) {

            drawRoundRect(rectF, 1f, 1f, foregroundPaint)
            translate(100f, 0f)
        }
        restore()
    }
}