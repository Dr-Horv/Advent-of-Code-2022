package solutions.day12

import solutions.DayTest
import solutions.Solver
import solutions.TestCase
import solutions.createTestCase

class Day12Test : DayTest() {
    override fun getPart1TestCases(): List<TestCase> = createTestCase(
        "Sabqponm\n" +
                "abcryxxl\n" +
                "accszExk\n" +
                "acctuvwj\n" +
                "abdefghi\n", "31"
    )

    override fun getPart2TestCases(): List<TestCase> = createTestCase(
        "Sabqponm\n" +
                "abcryxxl\n" +
                "accszExk\n" +
                "acctuvwj\n" +
                "abdefghi\n", "29"
    )

    override fun getSolver(): Solver = Day12()

}