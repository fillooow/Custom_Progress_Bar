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

    var linearAnimationDuration = MutableLiveData(1500L)
    var radialAnimationDuration = MutableLiveData(2000L)

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
            isTestAllRadialBtnEnabled.value = true

//            defaultTestLinearSpeedometer()
            setupRadialProgress(40f)
//            defaultTestRadialSpeedometer()
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

        val defaultValuesForTest = listOf(25f, 50f, 20f, 100f, 25f, 75f)

        defaultValuesForTest.forEach {
            setupRadialProgress(it)
        }

        for (i in (0 .. 4)) {

            val nextRandom = (100f / 52f) * Random.nextInt(0 .. 52)
            setupRadialProgress(nextRandom)
        }

        setupRadialProgress(40f)

        isTestAllRadialBtnEnabled.value = true
    }

    private suspend fun testAllDivisionsLinearSpeedometer() {

        isTestAllLinearBtnEnabled.value = false
        linearAnimationDuration.value = 300L

        for (i in (0 .. (VISIBLE_DIVISIONS_LINEAR + 1))) {

//            delay((ALL_VALUES_ANIMATION_TIME / (VISIBLE_DIVISIONS_LINEAR + 1)).toLong())
            val nextProgress = (100f / (VISIBLE_DIVISIONS_LINEAR + 1)) * i
            setupLinearProgress(nextProgress)
        }

        isTestAllLinearBtnEnabled.value = true
        linearAnimationDuration.value = 1500L
    }

    private suspend fun testAllDivisionsRadialSpeedometer() {

        isTestAllRadialBtnEnabled.value = false
        radialAnimationDuration.value = 300L

        setupRadialProgress(0f)

        delay(1500L)

        for (i in (0 .. (VISIBLE_DIVISIONS_RADIAL + 1))) {

//            delay((ALL_VALUES_ANIMATION_TIME / (VISIBLE_DIVISIONS_RADIAL + 1)).toLong())
            val nextProgress = (100f / (VISIBLE_DIVISIONS_RADIAL + 1)) * i
            setupRadialProgress(nextProgress)
        }

        radialAnimationDuration.value = 2000L
        isTestAllRadialBtnEnabled.value = true
    }

    private fun mapColorResIdAtProgress(progress: Float) = when (progress) {

        in 0f .. 25f -> R.color.kit_success
        in 25f .. 50f -> R.color.kit_warning
        in 50f .. 100f -> R.color.kit_alert

        else -> R.color.kit_alert
    }

    private suspend fun setupLinearProgress(progress: Float) {

        linearProgress.value = progress

        delay(linearAnimationDuration.value!!.plus(300L))
        if (useRainbowResIdUsing.value!!.not()) {

            progressColorLinearResId.value = mapColorResIdAtProgress(progress)
        }
    }

    private suspend fun setupRadialProgress(progress: Float) {

        radialProgress.value = progress
        if (useRainbowResIdUsing.value!!.not()) {

            progressColorRadialResId.value = mapColorResIdAtProgress(progress)
        }
        speedometerText.value = mapRadialTextAtProgress(progress)

//        delay(1000)
        delay(radialAnimationDuration.value!!.plus(600L))
    }

    private fun mapRadialTextAtProgress(progress: Float) = when (progress) {

        in 0f .. 0f -> "Нет операций"
        in 0f .. 25f -> "Беспокоиться не о чем"
        in 25f .. 50f -> "Стоит обратить внимание"
        in 50f .. 75f -> "Возможны лимиты или запрос"
        in 100f .. 100f -> "Установлены ограничения"
        in 75f .. 100f -> "Установлены лимиты"

        else -> "Установлены ограничения"
    }
}