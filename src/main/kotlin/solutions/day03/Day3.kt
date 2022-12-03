package solutions.day03

import solutions.Solver


class Day3 : Solver {


    private fun findCommonChar(strings: List<String>): Char? {
        val charSets = strings.map { it.toSet() }
        if (charSets.isNotEmpty()) {
            val first = charSets.first()
            val rest = charSets.drop(1)
            return first.find { ch -> rest.all { it.contains(ch) } }
        }
        return null
    }

    private fun findRucksackError(rucksack: String): Char {
        val compartmentA = rucksack.substring(0, rucksack.length / 2)
        val compartmentB = rucksack.substring(rucksack.length / 2)
        return findCommonChar(listOf(compartmentA, compartmentB))!!
    }

    override fun solve(input: List<String>, partTwo: Boolean): String = if (!partTwo) {
        input.map { findRucksackError(it) }
            .sumOf {
                getPriority(it)
            }
            .toString()
    } else {
        input.chunked(3)
            .map { findCommonChar(it)!! }
            .sumOf { getPriority(it) }
            .toString()
    }

    private fun getPriority(it: Char): Int = if (it.isLowerCase()) {
        27 - it.rangeTo('z').count()
    } else {
        53 - it.rangeTo('Z').count()
    }
}