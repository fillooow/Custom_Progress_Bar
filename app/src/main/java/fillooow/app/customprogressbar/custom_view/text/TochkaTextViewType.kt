package fillooow.app.customprogressbar.custom_view.text

import android.os.Parcelable
import fillooow.app.customprogressbar.custom_view.base.AttrEnum
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class TochkaTextViewType(override val id: Int) : AttrEnum, Parcelable {

    AG700_7XL(0),
    AG700_6XL(1),
    AG700_5XL(2),
    AG700_4XL(3),
    AG700_3XL(4),
    AG700_2XL(5),
    AG700_XL(6),

    TS500_3XL(7),
    TS500_2XL(8),
    TS500_XL(9),
    TS500_L(10),
    TS500_M(11),
    TS500_S(12),
    TS500_XS(13),

    TS400_3XL(14),
    TS400_2XL(15),
    TS400_XL(16),
    TS400_L(17),
    TS400_M(18),
    TS400_S(19),
    TS400_XS(20),

    TS700_3XL(21),
    TS700_2XL(22),
    TS700_XL(23),
    TS700_L(24),
    TS700_M(25),
    TS700_S(26),
    TS700_XS(27)
}