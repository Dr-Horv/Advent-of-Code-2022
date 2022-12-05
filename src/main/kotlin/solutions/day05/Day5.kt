package solutions.day05

import solutions.Solver

class Day5 : Solver {


    override fun solve(input: List<String>, partTwo: Boolean): String {
        val inputList = input.toMutableList()
        val stacks = parseStacks(inputList)
        performMoves(stacks, inputList, partTwo)

        return (1..stacks.keys.max())
            .map { stacks[it]!!.first() }
            .joinToString("")
    }

    private fun performMoves(
        stacks: MutableMap<Int, MutableList<Char>>,
        inputList: MutableList<String>,
        partTwo: Boolean
    ) {

        inputList.forEach {
            if (it.isBlank()) {
                return@forEach
            }
            val parts = it.split("from").map(String::trim)
            val count = parts[0].removePrefix("move ").toInt()
            val fromToParts = parts[1].split("to").map(String::trim).map(String::toInt)
            val from = fromToParts[0]
            val to = fromToParts[1]
            val fromStack = stacks[from]
            val toStack = stacks[to]
            for (i in 0 until count) {
                val c = if (!partTwo) {
                    fromStack!!.removeFirst()
                } else {
                    fromStack!!.removeAt(count - 1 - i)
                }
                toStack!!.add(0, c)
            }
        }
    }
    
    private fun parseStacks(inputList: MutableList<String>): MutableMap<Int, MutableList<Char>> {
        val stacks = mutableMapOf<Int, MutableList<Char>>()

        while (inputList.isNotEmpty()) {
            val line = inputList.removeFirst().toList()
            if (line[1] == '1') {
                break
            }
            for (i in 1..line.size step 4) {
                val c = line[i]
                if (c != ' ') {
                    val index = (i / 4) + 1
                    val l = stacks.getOrElse(index) { mutableListOf() }
                    l.add(c)
                    stacks[index] = l
                }
            }
        }
        return stacks
    }
}
