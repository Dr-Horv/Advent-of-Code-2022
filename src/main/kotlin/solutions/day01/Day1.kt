package solutions.day01

import solutions.Solver

class Day1 : Solver {
    override fun solve(input: List<String>, partTwo: Boolean): String {
        val lines = input.toMutableList()
        lines.add("")
        val sums = mutableListOf<Int>()

        var curr = 0;
        for (line in lines) {
            if (line == "") {
                sums.add(curr)
                curr = 0
                continue
            }

            curr += line.toInt()
        }


        return if (!partTwo) {
            sums.max().toString()
        } else {
            sums.sortedDescending()
                .take(3)
                .sum()
                .toString()
        }
    }
}