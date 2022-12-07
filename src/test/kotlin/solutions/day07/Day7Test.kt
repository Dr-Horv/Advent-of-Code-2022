package solutions.day07

import solutions.DayTest
import solutions.Solver
import solutions.TestCase
import solutions.createTestCase

class Day7Test : DayTest() {
    override fun getPart1TestCases(): List<TestCase> = createTestCase(
        "\$ cd /\n" +
                "\$ ls\n" +
                "dir a\n" +
                "14848514 b.txt\n" +
                "8504156 c.dat\n" +
                "dir d\n" +
                "\$ cd a\n" +
                "\$ ls\n" +
                "dir e\n" +
                "29116 f\n" +
                "2557 g\n" +
                "62596 h.lst\n" +
                "\$ cd e\n" +
                "\$ ls\n" +
                "584 i\n" +
                "\$ cd ..\n" +
                "\$ cd ..\n" +
                "\$ cd d\n" +
                "\$ ls\n" +
                "4060174 j\n" +
                "8033020 d.log\n" +
                "5626152 d.ext\n" +
                "7214296 k\n", "95437"
    )

    override fun getPart2TestCases(): List<TestCase> = createTestCase(
        "\$ cd /\n" +
                "\$ ls\n" +
                "dir a\n" +
                "14848514 b.txt\n" +
                "8504156 c.dat\n" +
                "dir d\n" +
                "\$ cd a\n" +
                "\$ ls\n" +
                "dir e\n" +
                "29116 f\n" +
                "2557 g\n" +
                "62596 h.lst\n" +
                "\$ cd e\n" +
                "\$ ls\n" +
                "584 i\n" +
                "\$ cd ..\n" +
                "\$ cd ..\n" +
                "\$ cd d\n" +
                "\$ ls\n" +
                "4060174 j\n" +
                "8033020 d.log\n" +
                "5626152 d.ext\n" +
                "7214296 k\n", "24933642"
    )

    override fun getSolver(): Solver = Day7()

}