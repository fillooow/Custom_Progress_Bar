package fillooow.app.customprogressbar.custom_view.base.model

import androidx.annotation.ColorRes

// fixme: internal
data class ColorAtProgressRangePair(

    @ColorRes
    val colorRes: Int,

    val progressRange: ClosedFloatingPointRange<Float>
)