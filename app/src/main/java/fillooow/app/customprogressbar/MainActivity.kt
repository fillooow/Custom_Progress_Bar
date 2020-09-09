package fillooow.app.customprogressbar

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import fillooow.app.customprogressbar.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.vm = viewModel
        binding.lifecycleOwner = this

//        testLinearSpeedometer()
    }

    private fun testLinearSpeedometer() = CoroutineScope(viewModel.viewModelScope.coroutineContext).launch {

        for (i in (0 .. 28)) {

            delay(200)
            viewModel.progress.value = (100f / 28f) * i
            Log.i("value", "${viewModel.progress.value}")
        }

        delay(800)
        viewModel.progress.value = 40f
    }
}