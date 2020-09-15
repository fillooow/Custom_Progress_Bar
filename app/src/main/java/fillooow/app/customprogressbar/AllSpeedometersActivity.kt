package fillooow.app.customprogressbar

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import fillooow.app.customprogressbar.databinding.ActivityAllSpeedometersBinding

private const val VISIBLE_DIVISIONS_RADIAL = 51
private const val VISIBLE_DIVISIONS_LINEAR = 27
private const val ALL_VALUES_ANIMATION_TIME = (VISIBLE_DIVISIONS_RADIAL + 1) * (VISIBLE_DIVISIONS_LINEAR + 1) * 12

class AllSpeedometersActivity : AppCompatActivity() {

    private val viewModel by viewModels<AllSpeedometersViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_speedometers)

        val binding: ActivityAllSpeedometersBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_all_speedometers)

        binding.vm = viewModel
        binding.lifecycleOwner = this
    }
}