package fillooow.app.customprogressbar.custom_view.base.model

import androidx.annotation.ColorRes

data class ColorAtProgressRangePair(

    @ColorRes
    val colorRes: Int,

    val progressRange: ClosedFloatingPointRange<Float>
)