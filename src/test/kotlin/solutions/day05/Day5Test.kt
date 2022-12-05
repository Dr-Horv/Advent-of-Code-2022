package solutions.day05

import solutions.DayTest
import solutions.Solver
import solutions.TestCase
import solutions.createTestCase

class Day5Test : DayTest() {
    override fun getPart1TestCases(): List<TestCase> = createTestCase(
        "    [D]    \n" +
                "[N] [C]    \n" +
                "[Z] [M] [P]\n" +
                " 1   2   3 \n" +
                "\n" +
                "move 1 from 2 to 1\n" +
                "move 3 from 1 to 3\n" +
                "move 2 from 2 to 1\n" +
                "move 1 from 1 to 2\n", "CMZ"
    )

    override fun getPart2TestCases(): List<TestCase> = createTestCase(
        "    [D]    \n" +
                "[N] [C]    \n" +
                "[Z] [M] [P]\n" +
                " 1   2   3 \n" +
                "\n" +
                "move 1 from 2 to 1\n" +
                "move 3 from 1 to 3\n" +
                "move 2 from 2 to 1\n" +
                "move 1 from 1 to 2\n", "MCD"
    )

    override fun getSolver(): Solver = Day5()

}