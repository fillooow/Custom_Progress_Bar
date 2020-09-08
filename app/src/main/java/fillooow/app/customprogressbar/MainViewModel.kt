package fillooow.app.customprogressbar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val progress = MutableLiveData<Float>(0f)
}