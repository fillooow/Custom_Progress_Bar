package fillooow.app.customprogressbar.custom_view.extension

import android.content.res.TypedArray
import androidx.annotation.StyleableRes
import fillooow.app.customprogressbar.custom_view.base.AttrEnum

inline fun <reified T> TypedArray.getEnum(@StyleableRes enumResId: Int,
                                          defaultAttrId: Int = 0): T where T : Enum<*>, T : AttrEnum {

    val enumConstants = T::class.java.enumConstants

    return enumConstants!!.find { it.id == getInt(enumResId, defaultAttrId) } ?: enumConstants.first()
}