package solutions.day17

import org.junit.jupiter.api.Test
import solutions.DayTest
import solutions.TestCase
import solutions.createTestCase
import kotlin.test.assertEquals

class Day17Test : DayTest() {

    @Test
    fun findCircle() {
        val testData = listOf(
            22, 33, 55, 77, 99,
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
        )
        val res = getSolver().findCycle(
            testData
        )

        assertEquals(IntRange(5, 15), res)
    }

    override fun getPart1TestCases(): List<TestCase> = createTestCase(
        ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>\n", "3068"
    )

    override fun getPart2TestCases(): List<TestCase> = createTestCase(
        ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>\n", "1514285714288"
    )

    override fun getSolver() = Day17()

}