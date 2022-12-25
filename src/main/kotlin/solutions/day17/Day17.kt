package solutions.day17

import solutions.Solver
import utils.Coordinate
import utils.Direction
import utils.plus
import utils.step
import kotlin.math.abs

data class Rock(val position: Coordinate, val parts: List<Coordinate>) {
    fun partsAbsolutePositions(): List<Coordinate> = parts.map { position.plus(it) }
}

val FIRST_SHAPE = listOf(
    Coordinate(0, 0), Coordinate(1, 0), Coordinate(2, 0), Coordinate(3, 0)
)
val SECOND_SHAPE = listOf(
    Coordinate(1, 0),
    Coordinate(0, 1), Coordinate(1, 1), Coordinate(2, 1),
    Coordinate(1, 2)
)
val THIRD_SHAPE = listOf(
    Coordinate(2, 0),
    Coordinate(2, 1),
    Coordinate(0, 2),
    Coordinate(1, 2),
    Coordinate(2, 2),
)

val FOURTH_SHAPE = listOf(
    Coordinate(0, 0),
    Coordinate(0, 1),
    Coordinate(0, 2),
    Coordinate(0, 3),
)

val FIFTH_SHAPE = listOf(
    Coordinate(0, 0), Coordinate(1, 0),
    Coordinate(0, 1), Coordinate(1, 1),
)

data class CircularList<T>(val list: List<T>) {
    private var index = 0
    fun next(): T = list[(index++) % list.size]
}


enum class RoomTile {
    ROCK,
    EMPTY
}

fun RoomTile.toPrint(): String = when (this) {
    RoomTile.ROCK -> "#"
    RoomTile.EMPTY -> "."
}

class Day17 : Solver {

    override fun solve(input: List<String>, partTwo: Boolean): String {
        val shapes = CircularList(listOf(FIRST_SHAPE, SECOND_SHAPE, THIRD_SHAPE, FOURTH_SHAPE, FIFTH_SHAPE))


        val jetstream = CircularList(input.first().map {
            when (it) {
                '<' -> Direction.LEFT
                '>' -> Direction.RIGHT
                else -> throw Error("Unexpected Stream char: '$it'")
            }
        })

        var highestRockInRoom = 0
        val room = mutableMapOf<Coordinate, RoomTile>().withDefault { coordinate ->
            when {
                coordinate.x < 0 || coordinate.x >= 7 -> RoomTile.ROCK
                else -> RoomTile.EMPTY
            }
        }
        for (x in 0 until 7) {
            room[Coordinate(x, 0)] = RoomTile.ROCK
        }

        val rocksToSimulate = if (!partTwo) {
            2022
        } else {
            1000000000000
        }

        val diffs = mutableListOf<Int>()

        for (r in 1..6_850) {
            val shape = shapes.next()
            var fallingRock = Rock(Coordinate(2, highestRockInRoom - 4 - shape.maxOf { it.y }), shape)
            while (true) {
                fallingRock = jetstreamPush(fallingRock, jetstream, room)
                val beforePos = fallingRock.position
                fallingRock = fall(fallingRock, room)

                if (beforePos == fallingRock.position) {
                    fallingRock.partsAbsolutePositions().forEach { room[it] = RoomTile.ROCK }
                    val oldHighest = highestRockInRoom
                    highestRockInRoom = room.keys.minOf { it.y }
                    val diff = abs(highestRockInRoom - oldHighest)
                    diffs += diff
                    break
                }

            }
        }

        val cycle = findCycle(diffs)!!
        val diffsBeforeFirstCycle = diffs.slice(IntRange(0, cycle.first - 1))
        val heightBeforeFirstCycle = diffsBeforeFirstCycle.sum()
        val rocksLeft = rocksToSimulate - diffsBeforeFirstCycle.size
        val cycleHeight = diffs.slice(cycle).sum()
        val completeCycles = rocksLeft / cycle.count()
        val rocksLeftAfterCompleteCycles = rocksLeft % cycle.count()
        val rest = diffs.slice(IntRange(cycle.first, cycle.first + rocksLeftAfterCompleteCycles.toInt() - 1)).sum()

        val totalHeight = heightBeforeFirstCycle + (cycleHeight * completeCycles) + rest

        return totalHeight.toString()
    }

    fun findCycle(diffs: List<Int>): IntRange? {
        val startPoint = diffs.size / 2
        for (size in 1 until diffs.size / 4) {
            val rangeToCheck = (0 until size).map { Pair(it, it + size) }
            if (rangeToCheck.all { diffs[startPoint + it.first] == diffs[startPoint + it.second] }) {
                val foundSize = rangeToCheck.size
                for (i in diffs.indices) {
                    val rangeToCheckAgain = (0 until foundSize).map { Pair(it, it + foundSize) }
                    if (rangeToCheckAgain.all { diffs[i + it.first] == diffs[i + it.second] }) {
                        return IntRange(i, i + foundSize - 1)
                    }
                }
            }
        }

        return null
    }

    private fun printRoom(rock: Rock, room: Map<Coordinate, RoomTile>) {
        val currMap = room.toMutableMap().withDefault { coordinate ->
            when {
                coordinate.x < 0 || coordinate.x >= 7 -> RoomTile.ROCK
                else -> RoomTile.EMPTY
            }
        }
        rock.partsAbsolutePositions().forEach { currMap[it] = RoomTile.ROCK }
        val falling = rock.partsAbsolutePositions().toSet()
        val minY = currMap.keys.minOf { it.y }
        val maxY = currMap.keys.maxOf { it.y }

        var s = ""
        for (y in minY until maxY) {
            for (x in -1..7) {
                s += if (x == -1 || x == 7) {
                    "|"
                } else if (falling.contains(Coordinate(x, y))) {
                    "@"
                } else {
                    currMap.getValue(Coordinate(x, y)).toPrint()
                }
            }
            s += "\n"
        }

        s += "+-------+\n"

        println(s)

    }

    private fun fall(fallingRock: Rock, room: Map<Coordinate, RoomTile>): Rock {
        val nextState = fallingRock.copy(position = fallingRock.position.plus(Coordinate(0, 1)))
        return if (nextState.partsAbsolutePositions().all { room.getValue(it) == RoomTile.EMPTY }) {
            nextState
        } else {
            fallingRock
        }
    }

    private fun jetstreamPush(
        fallingRock: Rock,
        jetstream: CircularList<Direction>,
        room: Map<Coordinate, RoomTile>
    ): Rock {
        val d = jetstream.next()
        val nextState = fallingRock.copy(position = fallingRock.position.step(d))
        return if (nextState.partsAbsolutePositions().all { room.getValue(it) == RoomTile.EMPTY }) {
            nextState
        } else {
            fallingRock
        }
    }
}
