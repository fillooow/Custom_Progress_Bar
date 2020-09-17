package fillooow.app.customprogressbar

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fillooow.app.customprogressbar.custom_view.base.model.ColorAtProgressRangePair
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RadialSpeedometerViewModel : ViewModel() {

    val speedometerText = MutableLiveData("Нет операций")

    var shouldShowNumberFormatExceptionToast = MutableLiveData<Boolean>(false)

    val radialProgress = MutableLiveData<Float>(0f)
    val editTextCharacters = MutableLiveData<String>("50, 77.5, 22")

    val colorAtProgressRangePairs = listOf(

        ColorAtProgressRangePair(
            colorRes = R.color.kit_success,
            progressRange = 0f .. 25f
        ),

        ColorAtProgressRangePair(
            colorRes = R.color.kit_warning,
            progressRange = 25f .. 50f
        ),

        ColorAtProgressRangePair(
            colorRes = R.color.kit_alert,
            progressRange = 50f .. 100f
        )
    )

    fun onStartAnimationClick(view: View) {
        viewModelScope.launch {

            try {

                val progressValues = editTextCharacters.value!!.trim().split(",").map(String::toFloat)

                progressValues.forEach {
                    delay(1000)
                    radialProgress.value = it
                    speedometerText.value = textList.random()
                }
            } catch (error: NumberFormatException) {

                shouldShowNumberFormatExceptionToast.value = true
            }
        }
    }

    private val textList = listOf(
        "Беспокоиться не о чем",
        "Стоит обратить внимание",
        "Возможны лимиты или запрос",
        "Установлены лимиты",
        "Установлены ограничения"
    )
}