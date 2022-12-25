package solutions.day18

import solutions.DayTest
import solutions.Solver
import solutions.TestCase
import solutions.createTestCase

class Day18Test : DayTest() {
    override fun getPart1TestCases(): List<TestCase> = listOf(
        TestCase(
            listOf(
                "0,0,0",
                "1,0,0",
                "2,0,0",
                "1,0,1",
                "1,1,1",
            ),
            "22"
        ),
        TestCase(
            listOf(
                "0,0,0",
                "1,0,0",
                "2,0,0",
                "1,0,1",
            ),
            "18"
        ),
        TestCase(
            listOf(
                "1,1,1",
                "2,1,1",
            ),
            "10"
        ),
        createTestCase(
            "2,2,2\n" +
                    "1,2,2\n" +
                    "3,2,2\n" +
                    "2,1,2\n" +
                    "2,3,2\n" +
                    "2,2,1\n" +
                    "2,2,3\n" +
                    "2,2,4\n" +
                    "2,2,6\n" +
                    "1,2,5\n" +
                    "3,2,5\n" +
                    "2,1,5\n" +
                    "2,3,5\n", "64"
        ).first()
    )

    override fun getPart2TestCases(): List<TestCase> = createTestCase(
        "2,2,2\n" +
                "1,2,2\n" +
                "3,2,2\n" +
                "2,1,2\n" +
                "2,3,2\n" +
                "2,2,1\n" +
                "2,2,3\n" +
                "2,2,4\n" +
                "2,2,6\n" +
                "1,2,5\n" +
                "3,2,5\n" +
                "2,1,5\n" +
                "2,3,5\n", "58"
    )

    override fun getSolver(): Solver = Day18()

}