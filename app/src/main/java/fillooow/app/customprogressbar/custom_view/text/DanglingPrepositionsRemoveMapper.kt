package fillooow.app.customprogressbar.custom_view.text

internal object DanglingPrepositionsRemoveMapper : (CharSequence) -> CharSequence {

    private const val NOWRAP_SPACE_CHARACTER = "\u00A0"

    /**
     * Слова длинною в 1-2 символа с последующим пробелом
     * Прим.: в, с, до, от, на, из ...
     */
    private const val PREPOSITION_REGEXP_LITERAL = "\\b[а-яА-ЯеЁ]{1,2}\\h+|[№#]\\h\\s*"

    /**
     * Аббревиатуры длинною от 1 до 3 символов, заканчивающиеся точкой
     * Прим.: г., д., ул., стр. ...
     */
    private const val ABBREVIATION_REGEXP_LITERAL = "\\b[а-я]{1,3}\\.\\h+([А-Я]|[0-9])"

    /**
     * Даты дд.мм.гггг
     * Прим.: 08.08.2008
     */
    private const val DATE_REGEXP_LITERAL = "\\s*(3[01]|[12][0-9]|0?[1-9])\\.(1[012]|0?[1-9])\\.((?:19|20)\\d{2})\\s*"

    /**
     * Номера
     * Прим.: № 123, # 45
     */
    private const val NUMBER_REGEXP_LITERAL = "[№#]\\h\\s*"

    /**
     * Тире
     */
    private const val DASH_REGEXP_LITERAL = "\\h\\—"

    /**
     * Заменяем "\\h" на "[ \t]", потому что "horizontal whitespace" был введен в Java 8
     * На низких уровнях API (прим. 21) выбрасывало [java.util.regex.PatternSyntaxException]
     */
    private val REGEXP = ("(" +
            PREPOSITION_REGEXP_LITERAL + "|" +
            ABBREVIATION_REGEXP_LITERAL + "|" +
            DATE_REGEXP_LITERAL + "|" +
            NUMBER_REGEXP_LITERAL + "|" +
            DASH_REGEXP_LITERAL +
            ")").replace("\\h", "[ \t]").toRegex()

    override fun invoke(sequence: CharSequence): CharSequence = REGEXP.replace(sequence) {

        return@replace it.value.replace("[ \t]+".toRegex(), NOWRAP_SPACE_CHARACTER)
    }
}