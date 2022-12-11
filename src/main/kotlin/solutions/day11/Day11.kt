package solutions.day11

import solutions.Solver

data class Monkey(
    val nbr: Long,
    val items: MutableList<Long>,
    val operation: (Long) -> Long,
    val test: (Long) -> Boolean,
    val testNbr: Long,
    val trueTarget: Long,
    val falseTarget: Long,
    var inspections: Long
)

class Day11 : Solver {

    override fun solve(input: List<String>, partTwo: Boolean): String {

        val inputList = input.toMutableList()
        val monkeys = mutableMapOf<Long, Monkey>()
        val monkeyList = mutableListOf<Monkey>()
        while (inputList.isNotEmpty()) {
            val monkeyNbr = inputList.removeFirst().trim().removePrefix("Monkey ").removeSuffix(":").toLong()
            val items = inputList.removeFirst().trim().removePrefix("Starting items: ")
                .split(",")
                .map(String::trim)
                .map(String::toLong)
                .toMutableList()
            val operation = parseOperation(inputList.removeFirst().trim())
            val pair = parseTest(inputList.removeFirst().trim())
            val trueTarget = inputList.removeFirst().trim().removePrefix("If true: throw to monkey ").toLong()
            val falseTarget = inputList.removeFirst().trim().removePrefix("If false: throw to monkey ").toLong()
            val monkey = Monkey(monkeyNbr, items, operation, pair.first, pair.second, trueTarget, falseTarget, 0)
            monkeys[monkeyNbr] = monkey
            monkeyList.add(monkey)
            if (inputList.isNotEmpty() && inputList.first() == "") {
                inputList.removeFirst()
            }
        }

        val worryDivider = if (!partTwo) {
            3
        } else {
            monkeyList.map { it.testNbr }.reduce { a, b -> a * b }
        }

        val rounds = if (!partTwo) {
            20
        } else {
            10_000
        }

        for (r in 1..rounds) {
            for (m in monkeyList) {
                m.inspections += m.items.size
                while (m.items.isNotEmpty()) {
                    val item = m.items.removeFirst()

                    val newWorryLevel = if (!partTwo) {
                        m.operation(item) / worryDivider
                    } else {
                        m.operation(item) % worryDivider
                    }

                    if (m.test(newWorryLevel)) {
                        monkeys[m.trueTarget]!!.items.add(newWorryLevel)
                    } else {
                        monkeys[m.falseTarget]!!.items.add(newWorryLevel)
                    }
                }
            }
        }

        monkeyList.sortBy { it.inspections }
        monkeyList.reverse()

        return monkeyList.take(2).map { it.inspections }.reduce { a, b -> a * b }.toString()
    }

    private fun parseTest(testStr: String): Pair<(Long) -> Boolean, Long> {
        val nbr = testStr.removePrefix("Test: divisible by ").toLong()
        return Pair({ it % nbr == 0L }, nbr)
    }

    private fun parseOperation(operationStr: String): (Long) -> Long {
        val operationStrClean = operationStr.removePrefix("Operation: new = ")
        val parts = operationStrClean.split(" ")
        val operator = when (parts[1]) {
            "*" -> { i1: Long, i2: Long -> i1 * i2 }
            "+" -> { i1: Long, i2: Long -> i1 + i2 }
            else -> throw Error("Unexpected operator ${parts[1]}")
        }


        return if (parts[0] == "old" && parts[2] == "old") {
            { i -> operator.invoke(i, i) }
        } else if (parts[0] == "old") {
            { i -> operator.invoke(parts[2].toLong(), i) }
        } else if (parts[0] == "old") {
            { i -> operator.invoke(i, parts[0].toLong()) }
        } else {
            throw Error("Failed to parse operation: $operationStr")
        }
    }
}
