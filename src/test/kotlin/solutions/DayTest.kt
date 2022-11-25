package solutions

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

abstract class DayTest {

    @Test
    fun part1() {
        runTests(getPart1TestCases1())
    }

    @Test
    fun part2() {
        runTests(getPart2TestCases1())
    }

    fun runTests(list: List<TestCase>) {
        val solver = getSolver();
        list.forEach {
            Assertions.assertEquals(solver.solve(it.input), it.expected)
        }
    }

    abstract fun getPart1TestCases1() : List<TestCase>
    abstract fun getPart2TestCases1() : List<TestCase>
    abstract fun getSolver() : Solver;


}