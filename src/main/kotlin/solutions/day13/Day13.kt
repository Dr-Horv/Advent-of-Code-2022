package solutions.day13

import solutions.Solver

sealed class Package

class ListPackage(val list: List<Package>) : Package() {
    override fun toString(): String = "[${list.joinToString(",")}]"

    companion object
}

fun ListPackage.Companion.of(p: IntegerPackage): Package = ListPackage(listOf(p))


class IntegerPackage(val value: Int) : Package() {
    override fun toString(): String = this.value.toString()
}


class Day13 : Solver {

    private fun parse(s: String): Package {
        val cs = s.toMutableList().subList(1, s.length)
        return parseHelp(cs)
    }

    private fun parseHelp(cs: MutableList<Char>): Package {
        val listPackages = mutableListOf<Package>()
        while (cs.isNotEmpty()) {
            when (cs.first()) {
                '[' -> {
                    cs.removeFirst()
                    val lp = parseHelp(cs)
                    listPackages.add(lp)
                }

                ']' -> {
                    cs.removeFirst()
                    return ListPackage(list = listPackages)
                }

                ',' -> {
                    cs.removeFirst()
                }

                else -> {
                    var nbrs = ""
                    while (cs.first().isDigit()) {
                        nbrs += cs.removeFirst()
                    }
                    listPackages.add(IntegerPackage(value = nbrs.toInt()))

                }
            }
        }

        return ListPackage(list = listPackages)
    }

    override fun solve(input: List<String>, partTwo: Boolean): String {
        val packages = input.map { it.trim() }
            .filter { it.isNotBlank() }
            .map { parse(it) }

        return if (!partTwo) {
            var pairIndex = 1
            val orderedIndices = mutableListOf<Int>()
            val iterator = packages.iterator()
            while (iterator.hasNext()) {
                if (comparePackage(iterator.next(), iterator.next()) < 0) {
                    orderedIndices.add(pairIndex)
                }
                pairIndex++
            }
            orderedIndices.sum().toString()
        } else {
            val divider1 = parse("[[2]]")
            val divider2 = parse("[[6]]")
            val packagesWithDividers = packages + listOf(divider1, divider2)
            val sorted = packagesWithDividers.sortedWith { a, b -> comparePackage(a, b) }
            ((sorted.indexOf(divider1) + 1) * (sorted.indexOf(divider2) + 1)).toString()
        }
    }

    private fun comparePackage(p1: Package, p2: Package): Int = when {
        p1 is IntegerPackage && p2 is IntegerPackage -> compareIntegerPackages(p1, p2)
        p1 is ListPackage && p2 is ListPackage -> compareListPackages(p1, p2)
        p1 is IntegerPackage && p2 is ListPackage -> comparePackage(
            ListPackage.of(p1), p2
        )

        p1 is ListPackage && p2 is IntegerPackage -> comparePackage(
            p1, ListPackage.of(p2)
        )

        else -> throw Error("Unsupported compare: $p1 and $p2")
    }

    private fun compareListPackages(p1: ListPackage, p2: ListPackage): Int {
        val l1 = p1.list.listIterator()
        val l2 = p2.list.listIterator()

        while (l1.hasNext() && l2.hasNext()) {
            val comparison = comparePackage(l1.next(), l2.next())
            if (comparison != 0) {
                return comparison
            }
        }
        return when {
            l1.hasNext().not() && l2.hasNext() -> -1
            l1.hasNext().not() && l2.hasNext().not() -> 0
            l1.hasNext() && l2.hasNext().not() -> 1
            else -> throw Error("Unexpected")
        }
    }

    private fun compareIntegerPackages(p1: IntegerPackage, p2: IntegerPackage): Int = when {
        p1.value < p2.value -> -1
        p1.value == p2.value -> 0
        else -> 1
    }
}
