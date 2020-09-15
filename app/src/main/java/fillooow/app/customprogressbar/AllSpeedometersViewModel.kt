package fillooow.app.customprogressbar

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt

private const val VISIBLE_DIVISIONS_RADIAL = 51
private const val VISIBLE_DIVISIONS_LINEAR = 27
private const val ALL_VALUES_ANIMATION_TIME = (VISIBLE_DIVISIONS_RADIAL + 1) * (VISIBLE_DIVISIONS_LINEAR + 1) * 9

class AllSpeedometersViewModel : ViewModel() {

    var isTestAllLinearBtnEnabled = MutableLiveData(false)
    var isTestAllRadialBtnEnabled  = MutableLiveData(false)

    val linearProgress = MutableLiveData<Float>(0f)
    val radialProgress = MutableLiveData<Float>(0f)

    val colorAtProgressRangePairs = listOf(

        0f .. 25f to R.color.kit_success,
        25f .. 50f to R.color.kit_warning,
        50f .. 100f to R.color.kit_alert,
        25f .. 0f to R.color.kit_brand
    )

    init {

        viewModelScope.launch {
            defaultTestLinearSpeedometer()
            defaultTestRadialSpeedometer()
        }
    }

    fun testAllDivisionsLinear(view: View)  {
        viewModelScope.launch {
            testAllDivisionsLinearSpeedometer()
        }
    }

    fun testAllDivisionsRadial(view: View)  {
        viewModelScope.launch {
            testAllDivisionsRadialSpeedometer()
        }
    }

    private suspend fun defaultTestLinearSpeedometer() {

        delay(800)
        linearProgress.value = 50f

        delay(800)
        linearProgress.value = 0f

        delay(800)
        linearProgress.value = 100f

        delay(800)
        linearProgress.value = 25f

        delay(800)
        linearProgress.value = 75f

        delay(800)
        linearProgress.value = 40f

        isTestAllLinearBtnEnabled.value = true
    }

    private suspend fun defaultTestRadialSpeedometer() {

        delay(800)
        radialProgress.value = 25f

        delay(800)
        radialProgress.value = 50f

        delay(800)
        radialProgress.value = 20f

        delay(800)
        radialProgress.value = 100f


        delay(800)
        radialProgress.value = 25f

        delay(800)
        radialProgress.value = 75f

        for (i in (0 .. 4)) {

            delay(800)
            radialProgress.value = (100f / 52f) * Random.nextInt(0 .. 52)
        }

        delay(800)
        radialProgress.value = 40f

        isTestAllRadialBtnEnabled.value = true
    }

    private suspend fun testAllDivisionsLinearSpeedometer() {

        isTestAllLinearBtnEnabled.value = false

        for (i in (0 .. (VISIBLE_DIVISIONS_LINEAR + 1))) {

            delay((ALL_VALUES_ANIMATION_TIME / (VISIBLE_DIVISIONS_LINEAR + 1)).toLong())
            linearProgress.value = (100f / (VISIBLE_DIVISIONS_LINEAR + 1)) * i
        }

        isTestAllLinearBtnEnabled.value = true
    }

    private suspend fun testAllDivisionsRadialSpeedometer() {

        isTestAllRadialBtnEnabled.value = false

        delay(400)

        for (i in (0 .. (VISIBLE_DIVISIONS_LINEAR + 1))) {

            delay((ALL_VALUES_ANIMATION_TIME / (VISIBLE_DIVISIONS_RADIAL + 1)).toLong())
            radialProgress.value = (100f / (VISIBLE_DIVISIONS_LINEAR + 1)) * i
        }

        isTestAllRadialBtnEnabled.value = true
    }
}