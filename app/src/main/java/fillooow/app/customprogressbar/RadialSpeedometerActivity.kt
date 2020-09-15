package fillooow.app.customprogressbar

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import fillooow.app.customprogressbar.databinding.ActivityRadialSpeedometerBinding

class RadialSpeedometerActivity : AppCompatActivity() {

    private val viewModel by viewModels<RadialSpeedometerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityRadialSpeedometerBinding = DataBindingUtil.setContentView(this, R.layout.activity_radial_speedometer)

        binding.vm = viewModel
        binding.lifecycleOwner = this
    }
}