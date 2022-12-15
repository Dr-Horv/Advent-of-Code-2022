package solutions.day14

import solutions.Solver
import utils.Coordinate
import utils.Direction
import utils.step
import java.lang.Integer.max
import java.lang.Integer.min

enum class CaveState {
    SAND,
    AIR,
    ROCK;

    override fun toString(): String {
        return when (this) {
            SAND -> "o"
            AIR -> "."
            ROCK -> "#"
        }
    }
}


class Day14 : Solver {

    override fun solve(input: List<String>, partTwo: Boolean): String {

        var map = mutableMapOf<Coordinate, CaveState>().withDefault { CaveState.AIR }

        for (line in input) {
            val coordinates = line.split("->")
                .map { parseCoordinate(it) }

            coordinates.windowed(2).forEach {
                val xs = min(it[0].x, it[1].x)..max(it[0].x, it[1].x)
                val ys = min(it[0].y, it[1].y)..max(it[0].y, it[1].y)
                for (x in xs) {
                    for (y in ys) {
                        map[Coordinate(x, y)] = CaveState.ROCK
                    }
                }
            }
        }

        val abyssLine = map.keys.maxBy { it.y }.y
        map = if (partTwo) {
            map.withDefault {
                when (it.y) {
                    abyssLine + 2 -> CaveState.ROCK
                    else -> CaveState.AIR
                }
            }
        } else {
            map
        }

        val updatedAbyssLine = if (partTwo) {
            abyssLine + 5
        } else {
            abyssLine
        }

        while (true) {
            val sandsBefore = map.values.count { it == CaveState.SAND }
            dropSand(map, updatedAbyssLine)
            val sandsAfter = map.values.count { it == CaveState.SAND }

            if ((sandsBefore == sandsAfter) || (map.getValue(Coordinate(500, 0)) == CaveState.SAND)) {
                return sandsAfter.toString()
            }
        }

    }

    private fun dropSand(map: MutableMap<Coordinate, CaveState>, abyssline: Int) {
        var sand = Coordinate(500, 0)
        var isMoving = true
        while (isMoving && sand.y < abyssline) {
            val newPos = listOf(
                sand.step(Direction.UP),
                sand.step(Direction.UP).step(Direction.LEFT),
                sand.step(Direction.UP).step(Direction.RIGHT)
            ).find { map.getValue(it) == CaveState.AIR }
            if (newPos != null) {
                sand = newPos
            } else {
                map[sand] = CaveState.SAND
                isMoving = false
            }
        }
    }

    private fun parseCoordinate(s: String): Coordinate {
        val parts = s.split(",")
            .map { it.trim() }
            .map { it.toInt() }
        return Coordinate(x = parts[0], y = parts[1])
    }
}
