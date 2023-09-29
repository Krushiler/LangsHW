import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertIsNot


internal class ItemTest {
    @Test
    fun `test Item create() function`() {
        assertAll(
            { assertIs<Item.Symbol>(Item.create("-")) },
            { assertIs<Item.Number>(Item.create("15678")) },
            { assertIsNot<Item.Number>(Item.create("-")) },
            { assertIsNot<Item.Symbol>(Item.create("15678")) },
        )
    }

    @Test
    fun `test toInfixExpression() returns correct result`() {
        assertAll(
            { assertEquals("((13 - 4) + 55)", "+ - 13 4 55".toInfixExpression()) },
            { assertEquals("(2 + (2 * (2 - 1)))", "+ 2 * 2 - 2 1".toInfixExpression()) },
            { assertEquals("((10 + 20) + 30)", "+ + 10 20 30".toInfixExpression()) },
            { assertEquals("((3 + 10) / ((2 + 3) * (3 - 5)))", "/ + 3 10 * + 2 3 - 3 5".toInfixExpression()) },
        )
    }

    @Test
    fun `test toInfixExpression() throws exception on incorrect arguments`() {
        assertAll(
            { assertThrows<IllegalArgumentException> { "- - 1 1".toInfixExpression() } },
            { assertThrows<IllegalArgumentException> { "- - - 3 - + + + + 1".toInfixExpression() } },
        )
    }
}