package fillooow.app.customprogressbar

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import fillooow.app.customprogressbar.databinding.ActivityLinearSpeedometerBinding

class LinearSpeedometerActivity : AppCompatActivity() {

    private val viewModel by viewModels<LinearSpeedometerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLinearSpeedometerBinding = DataBindingUtil.setContentView(this, R.layout.activity_linear_speedometer)

        binding.vm = viewModel
        binding.lifecycleOwner = this
    }
}