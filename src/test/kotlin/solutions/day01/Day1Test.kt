package solutions.day01

import solutions.DayTest
import solutions.Solver
import solutions.TestCase
import solutions.day02.Day2

internal class Day1Test : DayTest() {

    override fun getPart1TestCases(): List<TestCase> {
        return listOf(
            TestCase(
                listOf(
                    "1000",
                    "2000",
                    "3000",
                    "",
                    "4000",
                    "",
                    "5000",
                    "6000",
                    "",
                    "7000",
                    "8000",
                    "9000",
                    "",
                    "10000"
                ), "24000"
            )
        )
    }

    override fun getPart2TestCases(): List<TestCase> {
        return listOf(
            TestCase(
                listOf(
                    "1000",
                    "2000",
                    "3000",
                    "",
                    "4000",
                    "",
                    "5000",
                    "6000",
                    "",
                    "7000",
                    "8000",
                    "9000",
                    "",
                    "10000"
                ), "45000"
            )
        )
    }

    override fun getSolver(): Solver = Day1()
}