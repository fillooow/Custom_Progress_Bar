package fillooow.app.customprogressbar

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LinearSpeedometerViewModel : ViewModel() {

    var shouldShowNumberFormatExceptionToast = MutableLiveData<Boolean>(false)

    val linearProgress = MutableLiveData<Float>(0f)
    val editTextCharacters = MutableLiveData<String>("50, 77.5, 22")

    val colorAtProgressRangePairs = listOf(

        0f .. 25f to R.color.kit_success,
        25f .. 50f to R.color.kit_warning,
        50f .. 100f to R.color.kit_alert,
        25f .. 0f to R.color.kit_brand
    )

    fun onStartAnimationClick(view: View) {
        viewModelScope.launch {

            try {

                val progressValues = editTextCharacters.value!!.trim().split(",").map(String::toFloat)

                progressValues.forEach {
                    delay(800)
                    linearProgress.value = it
                }
            } catch (error: NumberFormatException) {

                shouldShowNumberFormatExceptionToast.value = true
            }
        }
    }
}