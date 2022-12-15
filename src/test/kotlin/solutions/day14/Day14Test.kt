package solutions.day14

import solutions.DayTest
import solutions.Solver
import solutions.TestCase
import solutions.createTestCase

class Day14Test : DayTest() {
    override fun getPart1TestCases(): List<TestCase> = createTestCase(
        "498,4 -> 498,6 -> 496,6\n" +
                "503,4 -> 502,4 -> 502,9 -> 494,9\n", "24"
    )

    override fun getPart2TestCases(): List<TestCase> = createTestCase(
        "498,4 -> 498,6 -> 496,6\n" +
                "503,4 -> 502,4 -> 502,9 -> 494,9\n", "93"
    )

    override fun getSolver(): Solver = Day14()

}