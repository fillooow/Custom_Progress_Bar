package fillooow.app.customprogressbar

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    fun toLinearSpeedometer(view: View) = view.context.startActivityIntent(LinearSpeedometerActivity::class.java)
    fun toRadialSpeedometer(view: View) = view.context.startActivityIntent(RadialSpeedometerActivity::class.java)
    fun toAllSpeedometers(view: View) = view.context.startActivityIntent(AllSpeedometersActivity::class.java)

    private fun Context.startActivityIntent(activity: Class<out Any>) = startActivity(Intent(this, activity))
}