package fillooow.app.customprogressbar

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LinearSpeedometerViewModel : ViewModel() {

    var shouldShowNumberFormatExceptionToast = MutableLiveData<Boolean>(false)

    val isRainbowResIdUsing = MutableLiveData<Boolean>(false)
    val progressColorResId = MutableLiveData<Int>(R.color.kit_success)

    val editTextCharacters = MutableLiveData<String>("50, 77.5, 22")
    val linearProgress = MutableLiveData<Float>(0f)

    fun onStartAnimationClick(view: View) {
        viewModelScope.launch {

            try {

                val progressValues = editTextCharacters.value!!.trim().split(",").map(String::toFloat)

                progressValues.forEach {

                    delay(800)
                    if (isRainbowResIdUsing.value!!.not()) {

                        progressColorResId.value = mapColorResIdAtProgress(it)
                    }
                    linearProgress.value = it
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
}

// Скорость заполнения шкалы у линейного спидометра - 1500мс (1.5 сек)
// Скорость заполнения шкалы у круглого спидометра - 2000мс (2 сек)
// Скорость заполнения длинной хуйни - 1500мс (1.5 сек)