package solutions.day20

import org.junit.jupiter.api.Test
import solutions.DayTest
import solutions.Solver
import solutions.TestCase
import solutions.createTestCase
import kotlin.test.assertEquals
import kotlin.test.assertSame

class Day20Test : DayTest() {
    override fun getPart1TestCases(): List<TestCase> = createTestCase(
        "1\n" +
                "2\n" +
                "-3\n" +
                "3\n" +
                "-2\n" +
                "0\n" +
                "4\n", "3"
    )

    override fun getPart2TestCases(): List<TestCase> = createTestCase(
        "1\n" +
                "2\n" +
                "-3\n" +
                "3\n" +
                "-2\n" +
                "0\n" +
                "4\n", "1623178306"
    )

    @Test
    fun testChainFromList() {
        val list = listOf(0L, 1L, 2L, 3L, 4L, 5L)
        val start = Day20().chainFromList(list)
        val chainList = mutableListOf<Chain<Long>>()
        var curr = start
        do {
            chainList += curr
            curr = curr.next!!
        } while (curr.index != 0)

        for ((index, curr) in chainList.withIndex()) {
            val prev = curr.prev
            val next = curr.next
            val prevIndex = chainList[(index - 1 + chainList.size) % chainList.size]
            val nextIndex = chainList[(index + 1 + chainList.size) % chainList.size]
            assertEquals(prevIndex, prev)
            assertEquals(nextIndex, next)
        }

        assertEquals(list, start.toList().map { it.value })
    }


    @Test
    fun testChainMovePositiveOne() {
        val list = listOf(0L, 1L, 2L, 3L, 4L, 5L)
        val start = Day20().chainFromList(list)
        var curr = start
        do {
            curr = curr.next!!
        } while (curr.index != 2)

        assertEquals(2, curr.value)
        curr.move(1)

        assertEquals(1, start.next!!.value)
        assertEquals(3, start.next!!.next!!.value)
        assertEquals(2, start.next!!.next!!.next!!.value)

        assertEquals(listOf(0L, 1L, 3L, 2L, 4L, 5L), start.toList().map { it.value })
    }

    @Test
    fun testChainMovePositiveFour() {
        val list = listOf(0L, 1L, 2L, 3L, 4L, 5L)
        val start = Day20().chainFromList(list)
        var curr = start
        do {
            curr = curr.next!!
        } while (curr.index != 2)

        assertEquals(2, curr.value)
        curr.move(4)


        assertEquals(listOf(0L, 2L, 1L, 3L, 4L, 5L), start.toList().map { it.value })
    }

    @Test
    fun testChainMoveNegativeOne() {
        val list = listOf(0L, 1L, 2L, 3L, 4L, 5L)
        val start = Day20().chainFromList(list)
        var curr = start
        do {
            curr = curr.next!!
        } while (curr.index != 2)

        assertEquals(2, curr.value)
        curr.move(-1)

        assertEquals(listOf(0L, 2L, 1L, 3L, 4L, 5L), start.toList().map { it.value })
    }


    @Test
    fun testChainMoveNegativeFour() {
        val list = listOf(0L, 1L, 2L, 3L, 4L, 5L)
        val start = Day20().chainFromList(list)
        var curr = start
        do {
            curr = curr.next!!
        } while (curr.index != 2)

        assertEquals(2, curr.value)
        curr.move(-4)

        assertEquals(listOf(0L, 1L, 3L, 2L, 4L, 5L), start.toList().map { it.value })
    }

    override fun getSolver(): Solver = Day20()

}