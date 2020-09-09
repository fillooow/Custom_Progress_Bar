package fillooow.app.customprogressbar.custom_view.radial

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import androidx.annotation.DimenRes
import androidx.core.view.updateLayoutParams
import fillooow.app.customprogressbar.R
import fillooow.app.customprogressbar.custom_view.base.BaseProgressView

class RadialSpeedometerProgressView @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : BaseProgressView(context, attrs, defStyleAttr) {

    override val strokeWidthResId: Int
        get() = TODO("Not yet implemented")
    override val backgroundPaintColorResId: Int
        get() = TODO("Not yet implemented")
    override val foregroundPaintColorResId: Int
        get() = TODO("Not yet implemented")

    private val viewWidth = resources.getDimensionPixelSize(R.dimen.radial_speedometer_progress_bar_stroke_width)
    private val viewHeight = resources.getDimensionPixelSize(R.dimen.radial_speedometer_progress_bar_stroke_height)

    override fun Canvas.drawProgress() {
        TODO("Not yet implemented")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        updateLayoutParams {
            width = viewWidth
            height = viewHeight
            setBackgroundColor(Color.CYAN)
        }
    }

    private fun asPixels(@DimenRes dimensionResource: Int) = resources.getDimension(dimensionResource)
}