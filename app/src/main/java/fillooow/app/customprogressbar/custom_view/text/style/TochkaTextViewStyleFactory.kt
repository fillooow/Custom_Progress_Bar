package fillooow.app.customprogressbar.custom_view.text.style

import android.widget.TextView
import fillooow.app.customprogressbar.custom_view.text.TochkaTextViewType

internal class TochkaTextViewStyleFactory(private val textView: TextView) {

    fun getStyle(viewType: TochkaTextViewType): TochkaTextViewStyle = when (viewType) {

        TochkaTextViewType.AG700_7XL -> TochkaTextView7xlAG700Style(textView)
        TochkaTextViewType.AG700_6XL -> TochkaTextView6xlAG700Style(textView)
        TochkaTextViewType.AG700_5XL -> TochkaTextView5xlAG700Style(textView)
        TochkaTextViewType.AG700_4XL -> TochkaTextView4xlAG700Style(textView)
        TochkaTextViewType.AG700_3XL -> TochkaTextView3xlAG700Style(textView)
        TochkaTextViewType.AG700_2XL -> TochkaTextView2xlAG700Style(textView)
        TochkaTextViewType.AG700_XL -> TochkaTextViewXLAG700Style(textView)

        TochkaTextViewType.TS500_3XL -> TochkaTextView3xlTS500Style(textView)
        TochkaTextViewType.TS500_2XL -> TochkaTextView2xlTS500Style(textView)
        TochkaTextViewType.TS500_XL -> TochkaTextViewXLTS500Style(textView)
        TochkaTextViewType.TS500_L -> TochkaTextViewLTS500Style(textView)
        TochkaTextViewType.TS500_M -> TochkaTextViewMTS500Style(textView)
        TochkaTextViewType.TS500_S -> TochkaTextViewSTS500Style(textView)
        TochkaTextViewType.TS500_XS -> TochkaTextViewXSTS500Style(textView)

        TochkaTextViewType.TS400_3XL -> TochkaTextView3xlTS400Style(textView)
        TochkaTextViewType.TS400_2XL -> TochkaTextView2xlTS400Style(textView)
        TochkaTextViewType.TS400_XL -> TochkaTextViewXLTS400Style(textView)
        TochkaTextViewType.TS400_L -> TochkaTextViewLTS400Style(textView)
        TochkaTextViewType.TS400_M -> TochkaTextViewMTS400Style(textView)
        TochkaTextViewType.TS400_S -> TochkaTextViewSTS400Style(textView)
        TochkaTextViewType.TS400_XS -> TochkaTextViewXSTS400Style(textView)

        TochkaTextViewType.TS700_3XL -> TochkaTextView3xlTS700Style(textView)
        TochkaTextViewType.TS700_2XL -> TochkaTextView2xlTS700Style(textView)
        TochkaTextViewType.TS700_XL -> TochkaTextViewXLTS700Style(textView)
        TochkaTextViewType.TS700_L -> TochkaTextViewLTS700Style(textView)
        TochkaTextViewType.TS700_M -> TochkaTextViewMTS700Style(textView)
        TochkaTextViewType.TS700_S -> TochkaTextViewSTS700Style(textView)
        TochkaTextViewType.TS700_XS -> TochkaTextViewXSTS700Style(textView)
    }
}