package solutions.day06

import solutions.DayTest
import solutions.Solver
import solutions.TestCase
import solutions.createTestCase

class Day6Test : DayTest() {
    override fun getPart1TestCases(): List<TestCase> = listOf(
        TestCase(listOf("mjqjpqmgbljsphdztnvjfqwrcgsmlb"), "7"),
        TestCase(listOf("bvwbjplbgvbhsrlpgdmjqwftvncz"), "5"),
        TestCase(listOf("nppdvjthqldpwncqszvftbrmjlhg"), "6"),
        TestCase(listOf("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), "10"),
        TestCase(listOf("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), "11"),
    )

    override fun getPart2TestCases(): List<TestCase> = listOf(
        TestCase(listOf("mjqjpqmgbljsphdztnvjfqwrcgsmlb"), "19"),
        TestCase(listOf("bvwbjplbgvbhsrlpgdmjqwftvncz"), "23"),
        TestCase(listOf("nppdvjthqldpwncqszvftbrmjlhg"), "23"),
        TestCase(listOf("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg"), "29"),
        TestCase(listOf("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"), "26"),
    )

    override fun getSolver(): Solver = Day6()

}