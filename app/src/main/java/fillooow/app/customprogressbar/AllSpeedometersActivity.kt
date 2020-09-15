package fillooow.app.customprogressbar

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import fillooow.app.customprogressbar.databinding.ActivityAllSpeedometersBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.random.nextInt

class AllSpeedometersActivity : AppCompatActivity() {

    private val viewModel by viewModels<AllSpeedometersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_speedometers)

        val binding: ActivityAllSpeedometersBinding = DataBindingUtil.setContentView(this, R.layout.activity_all_speedometers)

        binding.vm = viewModel
        binding.lifecycleOwner = this

        defaultTestLinearSpeedometer()
        defaultTestRadialSpeedometer()
    }

    private fun defaultTestLinearSpeedometer() = CoroutineScope(viewModel.viewModelScope.coroutineContext).launch {

        delay(800)
        viewModel.linearProgress.value = 50f

        delay(800)
        viewModel.linearProgress.value = 0f

        delay(800)
        viewModel.linearProgress.value = 100f

        delay(800)
        viewModel.linearProgress.value = 25f

        delay(800)
        viewModel.linearProgress.value = 75f

        delay(800)
        viewModel.linearProgress.value = 40f
    }

    private fun defaultTestRadialSpeedometer() = CoroutineScope(viewModel.viewModelScope.coroutineContext).launch {

        delay(5000)

        delay(800)
        viewModel.radialProgress.value = 25f

        delay(800)
        viewModel.radialProgress.value = 50f

        delay(800)
        viewModel.radialProgress.value = 20f

//        for (i in (0 .. 52)) {
//
//            delay(300)
//            viewModel.radialProgress.value = (100f / 52f) * i
//        }

        delay(800)
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
        viewModel.radialProgress.value = 40f
    }
}