package solutions.day04

import solutions.DayTest
import solutions.Solver
import solutions.TestCase
import solutions.createTestCase

class Day4Test : DayTest() {
    override fun getPart1TestCases(): List<TestCase> = createTestCase(
        "2-4,6-8\n" +
                "2-3,4-5\n" +
                "5-7,7-9\n" +
                "2-8,3-7\n" +
                "6-6,4-6\n" +
                "2-6,4-8", "2"
    )

    override fun getPart2TestCases(): List<TestCase> = createTestCase(
        "2-4,6-8\n" +
                "2-3,4-5\n" +
                "5-7,7-9\n" +
                "2-8,3-7\n" +
                "6-6,4-6\n" +
                "2-6,4-8\n", "4"
    )

    override fun getSolver(): Solver = Day4()

}