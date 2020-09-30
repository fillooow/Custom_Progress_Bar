package fillooow.app.customprogressbar

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RadialSpeedometerViewModel : ViewModel() {

    val speedometerText = MutableLiveData("Нет операций")

    var shouldShowNumberFormatExceptionToast = MutableLiveData<Boolean>(false)

    val progressColorResId = MutableLiveData<Int>(R.color.kit_success)

    val useRainbowColorResId = MutableLiveData<Boolean>(false)

    val radialProgress = MutableLiveData<Float>(0f)
    val editTextCharacters = MutableLiveData<String>("50, 77.5, 22")

    fun onStartAnimationClick(view: View) {
        viewModelScope.launch {

            try {

                val progressValues = editTextCharacters.value!!.trim().split(",").map(String::toFloat)

                progressValues.forEachIndexed { index, progressValue ->

                    if (index != 0) delay(2300)

                    if (useRainbowColorResId.value!!.not()) {

                        progressColorResId.value = mapColorResIdAtProgress(progressValue)
                    }
                    speedometerText.value = mapRadialTextAtProgress(progressValue)
                    radialProgress.value = progressValue
                }
            } catch (error: NumberFormatException) {

                shouldShowNumberFormatExceptionToast.value = true
            }
        }
    }

    private fun mapColorResIdAtProgress(progress: Float) = when (progress) {

        in 0f .. 25f -> R.color.kit_success
        in 25f .. 50f -> R.color.kit_warning
        in 50f .. 100f -> R.color.kit_alert

        else -> R.color.kit_alert
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