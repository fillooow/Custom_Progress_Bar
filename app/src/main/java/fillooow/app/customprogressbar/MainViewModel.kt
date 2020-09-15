package fillooow.app.customprogressbar

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val colorAtProgressRangePairs = listOf(

        0f .. 25f to R.color.kit_success,
        25f .. 50f to R.color.kit_warning,
        50f .. 100f to R.color.kit_alert,
        25f .. 0f to R.color.kit_brand
    )

    fun toLinearSpeedometer(view: View) = view.context.startActivityIntent(LinearSpeedometerActivity::class.java)
    fun toRadialSpeedometer(view: View) = view.context.startActivityIntent(RadialSpeedometerActivity::class.java)
    fun toAllSpeedometers(view: View) = view.context.startActivityIntent(AllSpeedometersActivity::class.java)

    private fun Context.startActivityIntent(activity: Class<out Any>) = startActivity(Intent(this, activity))
}