package solutions

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

abstract class DayTest {

    @Test
    fun part1() {
        runTests(getPart1TestCases(), false)
    }

    @Test
    fun part2() {
        runTests(getPart2TestCases(), true)
    }

    fun runTests(list: List<TestCase>, isPartTwo: Boolean) {
        val solver = getSolver();
        list.forEach {
            Assertions.assertEquals(it.expected, solver.solve(it.input, isPartTwo))
        }
    }

    abstract fun getPart1TestCases(): List<TestCase>
    abstract fun getPart2TestCases(): List<TestCase>
    abstract fun getSolver(): Solver;


}