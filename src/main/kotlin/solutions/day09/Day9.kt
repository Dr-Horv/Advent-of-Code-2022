package solutions.day09

import solutions.Solver
import utils.Coordinate
import utils.Direction
import utils.step
import kotlin.math.abs

class Day9 : Solver {

    override fun solve(input: List<String>, partTwo: Boolean): String {
        val rope = mutableListOf<Coordinate>()
        val ropeLength = if (!partTwo) {
            2
        } else {
            10
        }
        for (i in 0 until ropeLength) {
            rope.add(Coordinate(0, 0))
        }
        val visited = mutableSetOf<Coordinate>()

        for (line in input) {
            val parts = line.split(" ")
            val direction = when (parts[0]) {
                "R" -> Direction.RIGHT
                "D" -> Direction.DOWN
                "L" -> Direction.LEFT
                "U" -> Direction.UP
                else -> throw Error("Unsupported direction " + parts[0])
            }
            val steps = parts[1].toInt()


            for (i in 0 until steps) {
                performStep(rope, direction, visited)
            }
        }

        return visited.size.toString()
    }

    private fun performStep(
        rope: MutableList<Coordinate>,
        direction: Direction,
        visited: MutableSet<Coordinate>
    ) {
        rope[0] = rope[0].step(direction)
        for (i in 1 until rope.size) {
            rope[i] = moveTail(rope[i], rope[i - 1])
        }
        visited.add(rope.last())

    }

    private fun moveTail(tail: Coordinate, head: Coordinate): Coordinate {
        if (abs(tail.x - head.x) <= 1 && abs(tail.y - head.y) <= 1) {
            return tail
        }
        if (tail.x == head.x) {
            return if (tail.y < head.y) {
                tail.step(Direction.UP)
            } else {
                tail.step(Direction.DOWN)
            }
        }
        if (tail.y == head.y) {
            return if (tail.x < head.x) {
                tail.step(Direction.RIGHT)
            } else {
                tail.step(Direction.LEFT)
            }
        }

        var newTailPos = tail
        newTailPos = if (tail.y < head.y) {
            newTailPos.step(Direction.UP)
        } else {
            newTailPos.step(Direction.DOWN)
        }

        newTailPos = if (tail.x < head.x) {
            newTailPos.step(Direction.RIGHT)
        } else {
            newTailPos.step(Direction.LEFT)
        }

        return newTailPos
    }
}
