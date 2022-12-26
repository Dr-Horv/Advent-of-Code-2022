package solutions.day20

import solutions.Solver
import kotlin.math.abs

var INDEX = 0

fun getIndex(): Int = INDEX++

data class Chain<T>(
    val value: T,
    var next: Chain<T>?,
    var prev: Chain<T>?
) {
    val index: Int = getIndex()

    fun move(steps: Long) {
        var next = this.next!!
        var prev = this.prev!!
        prev.next = next
        next.prev = prev

        val chainToMove = this
        var curr = chainToMove
        if (steps > 0) {
            for (s in 0 until steps) {
                curr = curr.next!!
            }
        } else {
            for (s in 0..abs(steps)) {
                curr = curr.prev!!
            }
        }

        chainToMove.prev = curr
        chainToMove.next = curr.next
        curr.next = chainToMove
        chainToMove.next!!.prev = chainToMove
    }

    fun toList(): List<Chain<T>> {
        val indexToStopAt = this.index
        var curr = this
        val list = mutableListOf<Chain<T>>()
        do {
            list += curr
            curr = curr.next!!
        } while (curr.index != indexToStopAt)

        return list
    }
}


class Day20 : Solver {
    override fun solve(input: List<String>, partTwo: Boolean): String {
        val nbrs = input.map { it.trim().toLong() }
            .map {
                if (!partTwo) {
                    it
                } else {
                    it * 811589153
                }
            }

        val start: Chain<Long> = chainFromList(nbrs)
        val list = start.toList()
        val mixes = if (!partTwo) {
            1
        } else {
            10
        }
        for (s in 1..mixes) {
            mix(list)
        }
        val zeroLink = start.toList().first { it.value == 0L }
        var curr = zeroLink
        val mixedNbrs = mutableListOf<Long>()
        for (s in 1..3000) {
            curr = curr.next!!
            if (s % 1000 == 0) {
                mixedNbrs += curr.value
            }
        }

        return mixedNbrs.sum().toString()
    }

    fun chainFromList(nbrs: List<Long>): Chain<Long> {
        INDEX = 0
        val start: Chain<Long> = Chain(nbrs.first(), next = null, prev = null)
        var prev: Chain<Long> = start
        for (n in nbrs.drop(1)) {
            val curr = Chain(n, next = null, prev = prev)
            prev.next = curr
            prev = curr
        }
        prev.next = start
        start.prev = prev
        return start
    }

    private fun mix(chain: List<Chain<Long>>) {
        for (n in chain) {
            val rest = abs(n.value) % (chain.size - 1)
            val move = if (n.value < 0) {
                -rest
            } else {
                rest
            }
            n.move(move)
        }
    }

    private fun printChain(start: Chain<Long>) {
        var s = "["
        var curr = start
        do {
            s += "${curr.value},"
            curr = curr.next!!
        } while (curr.index != 0)

        println("${s.removeSuffix(",")}]")
    }
}
