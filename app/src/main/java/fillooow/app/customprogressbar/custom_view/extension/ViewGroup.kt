package fillooow.app.customprogressbar.custom_view.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes


internal inline val ViewGroup.inflater: LayoutInflater
    get() = LayoutInflater.from(context)

fun ViewGroup.inflateAndAttach(@LayoutRes layoutResId: Int): View = inflater.inflate(layoutResId, this, true)