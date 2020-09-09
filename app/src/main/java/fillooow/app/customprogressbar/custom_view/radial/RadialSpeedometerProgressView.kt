package fillooow.app.customprogressbar.custom_view.radial

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
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

    override fun Canvas.drawProgress() {
        TODO("Not yet implemented")
    }
}