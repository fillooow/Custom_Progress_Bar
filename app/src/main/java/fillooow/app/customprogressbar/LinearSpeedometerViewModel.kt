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