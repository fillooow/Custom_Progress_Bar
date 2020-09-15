package fillooow.app.customprogressbar

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import fillooow.app.customprogressbar.databinding.ActivityRadialSpeedometerBinding

class RadialSpeedometerActivity : AppCompatActivity() {

    private val viewModel by viewModels<RadialSpeedometerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityRadialSpeedometerBinding = DataBindingUtil.setContentView(this, R.layout.activity_radial_speedometer)

        binding.vm = viewModel
        binding.lifecycleOwner = this

        viewModel.shouldShowNumberFormatExceptionToast.observe(this, Observer { status ->
            if (status == true) {

                viewModel.shouldShowNumberFormatExceptionToast.value = false

                showToast("Проверьте числа, похоже, они введены неверно")
            }
        })

    }

    private fun showToast(text: String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}