import exception.BadTokenException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

internal class HashMapTest {
    private val map = configureMap()

    @Test
    fun testILoc() {
        assertAll(
            { assertEquals(10, map.iloc[0]) },
            { assertEquals(400, map.iloc[2]) },
            { assertEquals(20, map.iloc[5]) },
            { assertEquals(500, map.iloc[8]) }
        )
    }

    @Test
    fun testPloc() {
        assertAll(
            {
                assertEquals(
                    specialHashMapOf(
                        "1" to 10,
                        "2" to 20,
                        "3" to 30,
                    ), map.ploc[">=1"]
                )
                assertEquals(
                    specialHashMapOf(
                        "(1, 5, 3)" to 400,
                    ), map.ploc["<5, >=5, >=3     "]
                )
                assertEquals(
                    specialHashMapOf(
                        "1" to 10
                    ), map.ploc["=1"]
                )
                assertEquals(
                    specialHashMapOf(
                        "1" to 10
                    ), map.ploc["<=1"]
                )
                assertEquals(
                    specialHashMapOf(
                        "(10, 5)" to 300,
                    ), map.ploc[">5, <6"]
                )
                assertEquals(
                    specialHashMapOf(
                        "(1, 5, 3)" to 400,
                    ), map.ploc["!=5, =5, !=5"]
                )
            },
        )
    }

    @Test
    fun testPlocBadToken() {
        assertAll(
            {
                assertThrows<BadTokenException> { map.ploc["(1, 5, 3)"] }
                assertThrows<BadTokenException> { map.ploc["><><><<><><><><><>"] }
                assertThrows<BadTokenException> { map.ploc["5+>+5 > 0"] }
                assertThrows<BadTokenException> { map.ploc[""] }
                assertThrows<BadTokenException> { map.ploc[" "] }
                assertThrows<BadTokenException> { map.ploc["!"] }
            }
        )
    }

    private fun configureMap(): SpecialHashMap<Int> {
        val map = SpecialHashMap<Int>()
        map["value1"] = 1
        map["value2"] = 2
        map["value3"] = 3
        map["1"] = 10
        map["2"] = 20
        map["3"] = 30
        map["(1, 5)"] = 100
        map["(5, 5)"] = 200
        map["(10, 5)"] = 300
        map["(1, 5, 3)"] = 400
        map["(5, 5, 4)"] = 500
        map["(10, 5, 5)"] = 600
        return map
    }
}