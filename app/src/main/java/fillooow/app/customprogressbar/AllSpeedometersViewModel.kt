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
    var isTestAllRadialBtnEnabled = MutableLiveData(false)

    val useRainbowResIdUsing = MutableLiveData<Boolean>(false)
    val progressColorRadialResId = MutableLiveData<Int>(R.color.kit_success)
    val progressColorLinearResId = MutableLiveData<Int>(R.color.kit_success)

    val linearProgress = MutableLiveData<Float>(0f)
    val radialProgress = MutableLiveData<Float>(0f)

    val speedometerText = MutableLiveData("Нет операций")

    init {

        viewModelScope.launch {
            defaultTestLinearSpeedometer()
            defaultTestRadialSpeedometer()
        }
    }

    fun testAllDivisionsLinear(view: View) {
        viewModelScope.launch {
            testAllDivisionsLinearSpeedometer()
        }
    }

    fun testAllDivisionsRadial(view: View) {
        viewModelScope.launch {
            testAllDivisionsRadialSpeedometer()
        }
    }

    private suspend fun defaultTestLinearSpeedometer() {

        val defaultValuesForTest = listOf(50f, 0f, 100f, 25f, 75f, 40f)

        defaultValuesForTest.forEach{
            setupLinearProgress(it)
        }

        isTestAllLinearBtnEnabled.value = true
    }

    private suspend fun defaultTestRadialSpeedometer() {
        /*

        val defaultValuesForTest = listOf(25f, 50f, 20f, 100f, 25f, 75f)

        defaultValuesForTest.forEach {
            setupRadialProgress(it)
        }

        for (i in (0 .. 4)) {

            delay(800)
            val nextRandom = (100f / 52f) * Random.nextInt(0 .. 52)
            setupRadialProgress(nextRandom)
        }

        delay(800)
        setupRadialProgress(40f)*/

        isTestAllRadialBtnEnabled.value = true
    }

    private suspend fun testAllDivisionsLinearSpeedometer() {

        isTestAllLinearBtnEnabled.value = false

        for (i in (0 .. (VISIBLE_DIVISIONS_LINEAR + 1))) {

//            delay((ALL_VALUES_ANIMATION_TIME / (VISIBLE_DIVISIONS_LINEAR + 1)).toLong())
            val nextProgress = (100f / (VISIBLE_DIVISIONS_LINEAR + 1)) * i
            setupLinearProgress(nextProgress)
        }

        isTestAllLinearBtnEnabled.value = true
    }

    private suspend fun testAllDivisionsRadialSpeedometer() {

        isTestAllRadialBtnEnabled.value = false

        for (i in (0 .. (VISIBLE_DIVISIONS_LINEAR + 1))) {

//            delay((ALL_VALUES_ANIMATION_TIME / (VISIBLE_DIVISIONS_RADIAL + 1)).toLong())
            val nextProgress = (100f / (VISIBLE_DIVISIONS_LINEAR + 1)) * i
            setupRadialProgress(nextProgress)
        }

        isTestAllRadialBtnEnabled.value = true
    }

    private fun mapColorResIdAtProgress(progress: Float) = when (progress) {

        in 0f .. 25f -> R.color.kit_success
        in 25f .. 50f -> R.color.kit_warning
        in 50f .. 100f -> R.color.kit_alert

        else -> R.color.kit_alert
    }

    private suspend fun setupLinearProgress(progress: Float) {

        delay(800)
        if (useRainbowResIdUsing.value!!.not()) {

            progressColorLinearResId.value = mapColorResIdAtProgress(progress)
        }
        linearProgress.value = progress
    }

    private suspend fun setupRadialProgress(progress: Float) {

        delay(800)
        if (useRainbowResIdUsing.value!!.not()) {

            progressColorRadialResId.value = mapColorResIdAtProgress(progress)
        }
        speedometerText.value = mapRadialTextAtProgress(progress)
        radialProgress.value = progress
    }

    private fun mapRadialTextAtProgress(progress: Float) = when (progress) {

        in 0f .. 0f -> "Нет операций"
        in 0f .. 25f -> "Беспокоиться не о чем"
        in 25f .. 50f -> "Стоит обратить внимание"
        in 50f .. 75f -> "Возможны лимиты или запрос"
        in 75f .. 100f -> "Установлены лимиты"

        else -> "Установлены ограничения"
    }
}