package fillooow.app.customprogressbar.custom_view.text.style

import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.annotation.VisibleForTesting.PROTECTED
import fillooow.app.customprogressbar.custom_view.extension.applyLineSpacing
import fillooow.app.customprogressbar.custom_view.extension.setTextStyle
import fillooow.app.customprogressbar.R

internal abstract class TochkaTextViewStyle(private val textView: TextView) {

    @VisibleForTesting(otherwise = PROTECTED)
    abstract val textStyleResId: Int

    @VisibleForTesting(otherwise = PROTECTED)
    abstract val lineSpacingExtraResId: Int

    fun apply(useLineSpacing: Boolean = true) {

        setupTextStyle()
        if (useLineSpacing) setupLineSpacing()
    }

    private fun setupTextStyle() = textView.setTextStyle(textStyleResId)
    private fun setupLineSpacing() = textView.applyLineSpacing(lineSpacingExtraResId)
}

internal abstract class TochkaTextViewContentStyle(textView: TextView)
    : TochkaTextViewStyle(textView) {

    final override val lineSpacingExtraResId: Int = R.dimen.tochka_text_view_content_line_spacing_extra
}