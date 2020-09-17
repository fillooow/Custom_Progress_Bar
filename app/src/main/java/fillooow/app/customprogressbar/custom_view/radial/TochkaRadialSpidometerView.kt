package fillooow.app.customprogressbar.custom_view.radial

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.FrameLayout
import fillooow.app.customprogressbar.R
import fillooow.app.customprogressbar.custom_view.base.model.ColorAtProgressRangePair
import fillooow.app.customprogressbar.custom_view.extension.applyStyleable
import fillooow.app.customprogressbar.custom_view.extension.getColor
import fillooow.app.customprogressbar.custom_view.extension.inflateAndAttach
import fillooow.app.customprogressbar.custom_view.text.TochkaTextView
import kotlinx.android.synthetic.main.tochka_radial_speedometer_view.view.*

class TochkaRadialSpeedometerView @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0

) : FrameLayout(context, attrs, defStyleAttr) {

    var speedometerText: String
        get() = radialSpeedometerTextView.text?.toString() ?: ""
        set(value) {

            if (value == radialSpeedometerTextView.text) return

            radialSpeedometerTextView.animateChangeText(value)
        }

    var animatedProgress: Float
        get() = radialSpeedometerProgressView.animatedProgress
        set(value) {

            if (value == radialSpeedometerProgressView.animatedProgress) return

            radialSpeedometerProgressView.animatedProgress = value
        }

    var colorAtProgressRangePairs: List<ColorAtProgressRangePair>
        get() = radialSpeedometerProgressView.colorAtProgressRangePairs
        set(value) {

            radialSpeedometerProgressView.colorAtProgressRangePairs = value
        }

    private val radialSpeedometerTextView by lazy { tochka_radial_speedometer_view_text }
    private val radialSpeedometerProgressView by lazy { tochka_radial_speedometer_progress_view }

    private val viewWidth = resources.getDimensionPixelSize(R.dimen.radial_speedometer_progress_bar_width)
    private val viewHeight = resources.getDimensionPixelSize(R.dimen.radial_speedometer_progress_bar_height)

    init {

        inflateAndAttach(R.layout.tochka_radial_speedometer_view)
        attrs?.applyStyleable(context, R.styleable.TochkaRadialSpeedometerView, ::applyAttrs)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        layoutParams.apply {
            width = viewWidth
            height = viewHeight
        }
    }

    private fun applyAttrs(typedArray: TypedArray) = with(typedArray) {

        speedometerText = getString(R.styleable.TochkaRadialSpeedometerView_speedometerText) ?: ""
    }

    private fun TochkaTextView.animateChangeText(title: String) {

        animate().cancel()

        animate().alpha(0f).setDuration(TEXT_ANIMATION_DURATION).withEndAction {

            text = title

            mapTextColorByProgress(animatedProgress)

            animate().alpha(1f).setDuration(TEXT_ANIMATION_DURATION).start()

        }.start()
    }

    private fun mapTextColorByProgress(progress: Float) {

        radialSpeedometerTextView.textColor = when (progress) {

            0f -> getColor(R.color.kit_grey_400)
            else -> getColor(R.color.kit_primary)
        }
    }
}

private const val TEXT_ANIMATION_DURATION = 200L