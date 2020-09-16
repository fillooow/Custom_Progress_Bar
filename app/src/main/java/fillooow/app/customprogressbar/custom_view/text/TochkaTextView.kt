package fillooow.app.customprogressbar.custom_view.text


import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatTextView
import fillooow.app.customprogressbar.custom_view.text.style.TochkaTextViewStyleFactory
import fillooow.app.customprogressbar.R
import fillooow.app.customprogressbar.custom_view.extension.applyStyleable
import fillooow.app.customprogressbar.custom_view.extension.getGlobalTextViewStyle
import fillooow.app.customprogressbar.custom_view.extension.increaseHitBox
import fillooow.app.customprogressbar.custom_view.extension.removeDanglingPrepositions
import fillooow.app.customprogressbar.custom_view.extension.withAlpha

@SuppressWarnings("TooManyFunctions")
open class TochkaTextView @JvmOverloads constructor(

    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0

) : AppCompatTextView(context, attrs, defStyleAttr) {

    private companion object {

        private val DEFAULT_STYLE = TochkaTextViewType.TS400_M
        private const val DEFAULT_COLOR_ALPHA = 255
        private const val DEFAULT_REMOVE_DANGLING_PREPOSITION = true
        private const val DEFAULT_USE_LINE_SPACING = true
        private const val DEFAULT_BLUR_AMOUNT = false
    }

    var type: TochkaTextViewType = DEFAULT_STYLE
        set(value) {

            field = value
            applyStyle(value)
        }

    internal var textColor: Int = Color.BLACK
        set(@ColorInt value) {

            field = value
            setTextColor(textColor withAlpha textColorAlpha)
        }

    private var useLineSpacing: Boolean = DEFAULT_USE_LINE_SPACING

    private var textColorAlpha: Int = DEFAULT_COLOR_ALPHA
        set(value) {

            if (field == value) return
            field = value

            setTextColor(textColor withAlpha textColorAlpha)
        }

    private var blurAmount: Boolean = DEFAULT_BLUR_AMOUNT

    private val actionTextColorStateList by lazy {

        ColorStateList(

            arrayOf(
                intArrayOf(android.R.attr.state_enabled, android.R.attr.state_pressed),
                intArrayOf(android.R.attr.state_enabled, -android.R.attr.state_pressed),
                intArrayOf(-android.R.attr.state_enabled)
            ),

            intArrayOf(
                textColor withAlpha clickedAlpha,
                textColor,
                textColor withAlpha disabledAlpha
            )
        )
    }

    private val disabledAlpha by lazy { resources.getInteger(R.integer.alpha_25) }
    private val clickedAlpha by lazy { resources.getInteger(R.integer.alpha_50) }

//    private val textViewBlurFacade = TochkaTextViewBlurFacade(this, ::superSetText)

    open var removeDanglingPrepositions: Boolean = DEFAULT_REMOVE_DANGLING_PREPOSITION

    init {

        attrs?.let {

            it.applyStyleable(context, R.styleable.TochkaTextView, ::applyAttrs)
            type = it.getGlobalTextViewStyle(context, DEFAULT_STYLE)
        }
    }

    fun setup(newType: TochkaTextViewType = type,
              newTextColor: Int = textColor,
              newTextColorAlpha: Int = textColorAlpha,
              newUseLineSpacing: Boolean = useLineSpacing,
              newRemoveDanglingPrepositions: Boolean = removeDanglingPrepositions) {

        useLineSpacing = newUseLineSpacing
        removeDanglingPrepositions = newRemoveDanglingPrepositions
        textColorAlpha = newTextColorAlpha
        textColor = newTextColor
        type = newType
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

//        textViewBlurFacade.unsubscribe()
    }

    override fun setText(text: CharSequence?, type: BufferType?) {

        val textToSet = when {
            removeDanglingPrepositions && text != null -> text.removeDanglingPrepositions()
            else -> text
        }

        when {

            textToSet == null || blurAmount.not() -> super.setText(textToSet, type)
//            else -> textViewBlurFacade.setBlurredText(textToSet, type, false)
        }
    }

    private fun superSetText(text: CharSequence?, type: BufferType?) = super.setText(text, type)

    private fun applyAttrs(typedArray: TypedArray) = with(typedArray) {

        useLineSpacing = getBoolean(R.styleable.TochkaTextView_useStyleLineSpacing, DEFAULT_USE_LINE_SPACING)

        textColor = currentTextColor
        textColorAlpha = getInt(R.styleable.TochkaTextView_textColorAlpha, DEFAULT_COLOR_ALPHA)

        removeDanglingPrepositions = getBoolean(
            R.styleable.TochkaTextView_removeDanglingPrepositions,
            DEFAULT_REMOVE_DANGLING_PREPOSITION
        )

        blurAmount = getBoolean(R.styleable.TochkaTextView_blurAmount, DEFAULT_BLUR_AMOUNT)

        setText(text, BufferType.NORMAL)
    }

    private fun applyStyle(viewType: TochkaTextViewType) {

        val style = TochkaTextViewStyleFactory(this@TochkaTextView).getStyle(viewType)
        style.apply(useLineSpacing)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)

        if (textColors.isStateful.not()) applyDefaultColorStateList()
        increaseHitBox(R.dimen.space_3)
    }

    private fun applyDefaultColorStateList() {

        isFocusable = true
        isClickable = true
        setTextColor(actionTextColorStateList)
    }
}