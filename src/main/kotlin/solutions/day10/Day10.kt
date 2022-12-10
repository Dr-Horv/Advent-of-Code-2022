package solutions.day10

import solutions.Solver

class Day10 : Solver {

    override fun solve(input: List<String>, partTwo: Boolean): String {

        var crt = ""
        var checkpoint = if (!partTwo) {
            20
        } else {
            40
        }
        var x = 1
        var cycles = 0
        val readings = mutableListOf<Int>()
        for (l in input) {
            if (l.startsWith("addx")) {
                val nbr = l.removePrefix("addx ").toInt()
                crt = printPixel(crt, cycles, x)
                cycles++
                if (cycles == checkpoint) {
                    val pair = performCheckpoint(readings, x, checkpoint, crt)
                    checkpoint = pair.first
                    crt = pair.second
                }
                crt = printPixel(crt, cycles, x)
                cycles++
                if (cycles == checkpoint) {
                    val pair = performCheckpoint(readings, x, checkpoint, crt)
                    checkpoint = pair.first
                    crt = pair.second
                }
                x += nbr
            } else if (l == "noop") {
                crt = printPixel(crt, cycles, x)
                cycles++
                if (cycles == checkpoint) {
                    val pair = performCheckpoint(readings, x, checkpoint, crt)
                    checkpoint = pair.first
                    crt = pair.second
                }
            }
        }

        if (!partTwo) {
            return readings.sum().toString()
        }

        return crt
    }

    private fun performCheckpoint(
        readings: MutableList<Int>,
        x: Int,
        checkpoint: Int,
        crt: String
    ): Pair<Int, String> {
        var checkpoint1 = checkpoint
        var crt1 = crt
        readings.add(x * checkpoint1)
        checkpoint1 += 40
        crt1 += "\n"
        return Pair(checkpoint1, crt1)
    }

    private fun printPixel(crt: String, cycles: Int, x: Int): String {
        var crt1 = crt
        val cyclesRelative = cycles % 40
        crt1 += if ((x - 1) == cyclesRelative || x == cyclesRelative || (x + 1) == cyclesRelative) {
            "#"
        } else {
            "."
        }
        return crt1
    }
}
