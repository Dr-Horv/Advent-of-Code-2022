package solutions.day02

import solutions.DayTest
import solutions.Solver
import solutions.TestCase
import solutions.createTestCase

class Day2Test : DayTest() {
    override fun getPart1TestCases(): List<TestCase> {
        return createTestCase(
            "A Y\n" +
                    "B X\n" +
                    "C Z\n",
            "15"
        )
    }

    override fun getPart2TestCases(): List<TestCase> {
        return createTestCase(
            "A Y\n" +
                    "B X\n" +
                    "C Z\n",
            "12"
        )
    }

    override fun getSolver(): Solver = Day2()

}