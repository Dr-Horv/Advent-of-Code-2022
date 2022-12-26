package solutions.day21

import solutions.DayTest
import solutions.Solver
import solutions.TestCase
import solutions.createTestCase


class Day21Test : DayTest() {
    override fun getPart1TestCases(): List<TestCase> = createTestCase(
        "root: pppw + sjmn\n" +
                "dbpl: 5\n" +
                "cczh: sllz + lgvd\n" +
                "zczc: 2\n" +
                "ptdq: humn - dvpt\n" +
                "dvpt: 3\n" +
                "lfqf: 4\n" +
                "humn: 5\n" +
                "ljgn: 2\n" +
                "sjmn: drzm * dbpl\n" +
                "sllz: 4\n" +
                "pppw: cczh / lfqf\n" +
                "lgvd: ljgn * ptdq\n" +
                "drzm: hmdt - zczc\n" +
                "hmdt: 32\n", "152"
    )

    override fun getPart2TestCases(): List<TestCase> = createTestCase(
        "root: pppw + sjmn\n" +
                "dbpl: 5\n" +
                "cczh: sllz + lgvd\n" +
                "zczc: 2\n" +
                "ptdq: humn - dvpt\n" +
                "dvpt: 3\n" +
                "lfqf: 4\n" +
                "humn: 5\n" +
                "ljgn: 2\n" +
                "sjmn: drzm * dbpl\n" +
                "sllz: 4\n" +
                "pppw: cczh / lfqf\n" +
                "lgvd: ljgn * ptdq\n" +
                "drzm: hmdt - zczc\n" +
                "hmdt: 32\n", "301"
    )
    
    override fun getSolver(): Solver = Day21()

}