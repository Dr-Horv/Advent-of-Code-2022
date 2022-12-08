package solutions.day08

import solutions.DayTest
import solutions.Solver
import solutions.TestCase
import solutions.createTestCase

class Day8Test : DayTest() {
    override fun getPart1TestCases(): List<TestCase> = createTestCase(
        "30373\n" +
                "25512\n" +
                "65332\n" +
                "33549\n" +
                "35390\n", "21"
    )

    override fun getPart2TestCases(): List<TestCase> = createTestCase(
        "30373\n" +
                "25512\n" +
                "65332\n" +
                "33549\n" +
                "35390\n", "8"
    )

    override fun getSolver(): Solver = Day8()

}