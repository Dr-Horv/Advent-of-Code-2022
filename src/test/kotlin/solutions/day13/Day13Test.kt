package solutions.day13

import solutions.DayTest
import solutions.Solver
import solutions.TestCase
import solutions.createTestCase

class Day13Test : DayTest() {
    override fun getPart1TestCases(): List<TestCase> = createTestCase(
        "[1,1,3,1,1]\n" +
                "[1,1,5,1,1]\n" +
                "\n" +
                "[[1],[2,3,4]]\n" +
                "[[1],4]\n" +
                "\n" +
                "[9]\n" +
                "[[8,7,6]]\n" +
                "\n" +
                "[[4,4],4,4]\n" +
                "[[4,4],4,4,4]\n" +
                "\n" +
                "[7,7,7,7]\n" +
                "[7,7,7]\n" +
                "\n" +
                "[]\n" +
                "[3]\n" +
                "\n" +
                "[[[]]]\n" +
                "[[]]\n" +
                "\n" +
                "[1,[2,[3,[4,[5,6,7]]]],8,9]\n" +
                "[1,[2,[3,[4,[5,6,0]]]],8,9]\n", "13"
    )

    override fun getPart2TestCases(): List<TestCase> = createTestCase(
        "[1,1,3,1,1]\n" +
                "[1,1,5,1,1]\n" +
                "\n" +
                "[[1],[2,3,4]]\n" +
                "[[1],4]\n" +
                "\n" +
                "[9]\n" +
                "[[8,7,6]]\n" +
                "\n" +
                "[[4,4],4,4]\n" +
                "[[4,4],4,4,4]\n" +
                "\n" +
                "[7,7,7,7]\n" +
                "[7,7,7]\n" +
                "\n" +
                "[]\n" +
                "[3]\n" +
                "\n" +
                "[[[]]]\n" +
                "[[]]\n" +
                "\n" +
                "[1,[2,[3,[4,[5,6,7]]]],8,9]\n" +
                "[1,[2,[3,[4,[5,6,0]]]],8,9]\n", "140"
    )

    override fun getSolver(): Solver = Day13()

}