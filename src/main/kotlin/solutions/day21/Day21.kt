package solutions.day21

import solutions.Solver

enum class Operator {
    TIMES,
    PLUS,
    DIV,
    MINUS
}

sealed class MathMonkey {
    abstract val name: String
    abstract var value: Long?

    data class ConstantMonkey(
        override val name: String,
        override var value: Long?
    ) : MathMonkey()

    data class OperationMonkey(
        override val name: String,
        override var value: Long? = null,
        val operand1Name: String,
        val operand2Name: String,
        val operation: (Long, Long) -> Long,
        val operator: Operator
    ) : MathMonkey()
}

class Day21 : Solver {

    override fun solve(input: List<String>, partTwo: Boolean): String {
        val monkeys = input.filter {
            !(partTwo && it.startsWith("humn"))
        }.map { parseMonkey(it) }
        val monkeyMap = mutableMapOf<String, MathMonkey>()
        monkeys.forEach { monkeyMap[it.name] = it }
        do {
            val isUnstable = simplify(monkeyMap, partTwo)
            if (!partTwo) {
                val rootValue = monkeyMap.getValue("root").value
                if (rootValue != null) {
                    return rootValue.toString()
                }
            }
        } while (isUnstable)


        val root = monkeyMap.getValue("root") as MathMonkey.OperationMonkey
        var curr = if (monkeyMap.getValue(root.operand1Name).value != null) {
            monkeyMap.getValue(root.operand2Name) as MathMonkey.OperationMonkey
        } else {
            monkeyMap.getValue(root.operand1Name) as MathMonkey.OperationMonkey
        }

        var rhs = if (monkeyMap.getValue(root.operand1Name).value != null) {
            monkeyMap.getValue(root.operand1Name).value!!
        } else {
            monkeyMap.getValue(root.operand2Name).value!!
        }

        while (true) {
            val opLhs = monkeyMap[curr.operand1Name]
            val opRhs = monkeyMap[curr.operand2Name]
            rhs = reverseOperation(curr, opLhs, rhs, opRhs)
            if (curr.operand1Name == "humn" || curr.operand2Name == "humn") {
                break
            }

            curr = if (opLhs is MathMonkey.OperationMonkey) {
                opLhs
            } else if (opRhs is MathMonkey.OperationMonkey) {
                opRhs
            } else {
                throw Error("Neither LHS or RHS is operator")
            }
        }

        return rhs.toString()

    }

    private fun reverseOperation(
        curr: MathMonkey.OperationMonkey,
        opLhs: MathMonkey?,
        rhs: Long,
        opRhs: MathMonkey?
    ) = when (curr.operator) {
        Operator.TIMES -> if (opLhs is MathMonkey.ConstantMonkey) rhs / opLhs?.value!! else rhs / opRhs?.value!!
        Operator.PLUS -> if (opLhs is MathMonkey.ConstantMonkey) rhs - opLhs?.value!! else rhs - opRhs?.value!!
        Operator.DIV -> if (opLhs is MathMonkey.ConstantMonkey) opLhs.value!! / rhs else opRhs?.value!! * rhs
        Operator.MINUS -> if (opLhs is MathMonkey.ConstantMonkey) -(rhs - opLhs.value!!) else rhs + opRhs?.value!!
    }


    private fun simplify(
        monkeyMap: MutableMap<String, MathMonkey>,
        partTwo: Boolean,

        ): Boolean {
        var isUnstable = false
        monkeyMap.values
            .filter { (it.name != "root" || !partTwo) && it.value == null }
            .forEach {
                when (it) {
                    is MathMonkey.OperationMonkey -> {
                        val operand1 = monkeyMap[it.operand1Name]
                        val operand2 = monkeyMap[it.operand2Name]
                        if (operand1?.value != null && operand2?.value != null) {
                            val value = it.operation(operand1.value!!, operand2.value!!)
                            monkeyMap[it.name] = MathMonkey.ConstantMonkey(name = it.name, value = value)
                            isUnstable = true
                        }
                    }

                    is MathMonkey.ConstantMonkey -> {}
                }

            }
        return isUnstable
    }

    private fun parseMonkey(s: String): MathMonkey {
        val parts = s.split(":")
        val name = parts[0].trim()
        val rhs = parts[1].trim().split(" ")
        return if (rhs.size > 1) {
            val n1 = rhs[0].trim()
            val n2 = rhs[2].trim()
            val op: (Long, Long) -> Long = when (rhs[1].trim()) {
                "*" -> { i1: Long, i2: Long -> i1 * i2 }
                "/" -> { i1: Long, i2: Long -> i1 / i2 }
                "-" -> { i1: Long, i2: Long -> i1 - i2 }
                "+" -> { i1: Long, i2: Long -> i1 + i2 }
                else -> throw Error("Unexpected operator: " + rhs[1].trim())
            }

            val operator = when (rhs[1].trim()) {
                "*" -> Operator.TIMES
                "/" -> Operator.DIV
                "-" -> Operator.MINUS
                "+" -> Operator.PLUS
                else -> throw Error("Unexpected operator: " + rhs[1].trim())
            }

            MathMonkey.OperationMonkey(name, null, operand1Name = n1, operand2Name = n2, op, operator)
        } else {
            MathMonkey.ConstantMonkey(name, value = rhs[0].toLong())
        }
    }
}
