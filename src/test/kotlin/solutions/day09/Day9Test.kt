package solutions.day09

import solutions.DayTest
import solutions.Solver
import solutions.TestCase
import solutions.createTestCase

class Day9Test : DayTest() {
    override fun getPart1TestCases(): List<TestCase> = createTestCase(
        "R 4\n" +
                "U 4\n" +
                "L 3\n" +
                "D 1\n" +
                "R 4\n" +
                "D 1\n" +
                "L 5\n" +
                "R 2\n", "13"
    )

    override fun getPart2TestCases(): List<TestCase> = listOf(
        TestCase(
            listOf(
                "R 4",
                "U 4",
                "L 3",
                "D 1",
                "R 4",
                "D 1",
                "L 5",
                "R 2"
            ), "1"
        ),
        TestCase(
            listOf(
                "R 5",
                "U 8",
                "L 8",
                "D 3",
                "R 17",
                "D 10",
                "L 25",
                "U 20"
            ), "36"
        )
    )

    override fun getSolver(): Solver = Day9()

}