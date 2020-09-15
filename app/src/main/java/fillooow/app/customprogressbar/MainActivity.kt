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
import kotlin.random.Random
import kotlin.random.nextInt

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.vm = viewModel
        binding.lifecycleOwner = this

//        testLinearSpeedometer()
        testRadialSpeedometerProgressView()
    }

    private fun testRadialSpeedometerProgressView() = CoroutineScope(viewModel.viewModelScope.coroutineContext).launch {

        delay(800)
        viewModel.radialProgress.value = 25f
//
        delay(800)
        viewModel.radialProgress.value = 50f
//
        delay(800)
        viewModel.radialProgress.value = 20f

//        for (i in (0 .. 52)) {
//
//            delay(300)
//            viewModel.radialProgress.value = (100f / 52f) * i
//        }

        /*delay(800)
        viewModel.radialProgress.value = 100f


        delay(800)
        viewModel.radialProgress.value = 25f

        delay(800)
        viewModel.radialProgress.value = 75f

        for (i in (0 .. 4)) {

            delay(800)
            viewModel.radialProgress.value = (100f / 52f) * Random.nextInt(0 .. 52)
        }

        delay(800)
        viewModel.radialProgress.value = 40f*/
    }

    private fun testLinearSpeedometer() = CoroutineScope(viewModel.viewModelScope.coroutineContext).launch {

        delay(800)
        viewModel.progress.value = 50f

        delay(800)
        viewModel.progress.value = 0f

        delay(800)
        viewModel.progress.value = 100f

        delay(800)
        viewModel.progress.value = 25f

        delay(800)
        viewModel.progress.value = 75f

        delay(800)
        viewModel.progress.value = 40f
    }
}