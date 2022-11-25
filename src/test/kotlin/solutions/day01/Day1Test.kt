package solutions.day01

import solutions.DayTest
import solutions.Solver
import solutions.TestCase

internal class Day1Test: DayTest() {

    override fun getPart1TestCases1(): List<TestCase> {
        return listOf(TestCase(listOf("Hello"), "Hello"))
    }

    override fun getPart2TestCases1(): List<TestCase> {
        return listOf(TestCase(listOf("Hello"), "Hello"))
    }

    override fun getSolver(): Solver = Day1()
}