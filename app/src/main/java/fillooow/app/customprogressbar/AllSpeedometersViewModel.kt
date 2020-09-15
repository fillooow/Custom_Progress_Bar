package fillooow.app.customprogressbar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AllSpeedometersViewModel : ViewModel() {

    val progress = MutableLiveData<Float>(0f)
    val radialProgress = MutableLiveData<Float>(0f)

    val colorAtProgressRangePairs = listOf(

        0f .. 25f to R.color.kit_success,
        25f .. 50f to R.color.kit_warning,
        50f .. 100f to R.color.kit_alert,
        25f .. 0f to R.color.kit_brand
    )
}