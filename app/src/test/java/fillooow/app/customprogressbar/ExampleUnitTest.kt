package fillooow.app.customprogressbar

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect_float() {
        assertEquals(100f / 28f / 100f * 28f, 1f)
    }

    @Test
    fun addition_isCorrect_double() {
        assertEquals(100.0 / 28.0 / 100.0 * 28.0, 1.0, 0.00001)
    }
}