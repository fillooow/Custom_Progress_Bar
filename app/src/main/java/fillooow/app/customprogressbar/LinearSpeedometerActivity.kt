package fillooow.app.customprogressbar

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import fillooow.app.customprogressbar.databinding.ActivityLinearSpeedometerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LinearSpeedometerActivity : AppCompatActivity() {

    private val viewModel by viewModels<LinearSpeedometerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLinearSpeedometerBinding = DataBindingUtil.setContentView(this, R.layout.activity_linear_speedometer)

        binding.vm = viewModel
        binding.lifecycleOwner = this

        viewModel.shouldShowNumberFormatExceptionToast.observe(this, Observer { status ->
            if (status == true) {

                viewModel.shouldShowNumberFormatExceptionToast.value = false

                showToast("Проверьте числа, похоже, они введены неверно")
            }
        })
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

    private fun showToast(text: String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}