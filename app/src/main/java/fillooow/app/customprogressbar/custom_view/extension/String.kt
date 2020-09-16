package fillooow.app.customprogressbar.custom_view.extension

import fillooow.app.customprogressbar.custom_view.text.DanglingPrepositionsRemoveMapper

fun CharSequence.removeDanglingPrepositions(): CharSequence {

    return DanglingPrepositionsRemoveMapper.invoke(this)
}