package solutions.day04

import solutions.Solver


class Day4 : Solver {

    private infix fun IntRange.contains(other: IntRange): Boolean =
        (this.contains(other.first) and this.contains(other.last)) or
                (other.contains(this.first) and other.contains(this.last))

    private infix fun IntRange.overlaps(other: IntRange): Boolean =
        this.contains(other.first) or
                this.contains(other.last) or
                other.contains(this.first) or
                other.contains(this.last)

    private fun parsePair(line: String): Pair<IntRange, IntRange> {
        val pairsStr = line.split(",");
        return Pair(parseIntRange(pairsStr[0]), parseIntRange(pairsStr[1]))
    }

    private fun parseIntRange(a: String): IntRange {
        val rangeInts = a.split("-")
            .map { it.toInt() }
        return IntRange(rangeInts[0], rangeInts[1])
    }

    override fun solve(input: List<String>, partTwo: Boolean): String {
        val condition: (Pair<IntRange, IntRange>) -> Boolean = if (!partTwo) {
            { it.first contains it.second }
        } else {
            { it.first overlaps it.second }
        }
        
        return input.map { parsePair(it) }
            .count(condition)
            .toString()
    }

}