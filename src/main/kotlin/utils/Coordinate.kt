package utils

import kotlin.math.abs

data class Coordinate(val x: Int, val y: Int) {
    override fun toString(): String {
        return "($x, $y)"
    }
}

operator fun Coordinate.plus(coordinate: Coordinate) = Coordinate(x + coordinate.x, y + coordinate.y)

fun Coordinate.manhattanDistance(coordinate: Coordinate): Int = abs(coordinate.x - this.x) + abs(coordinate.y - this.y)

fun Coordinate.step(direction: Direction): Coordinate = when (direction) {
    Direction.UP -> Coordinate(x, y + 1)
    Direction.LEFT -> Coordinate(x - 1, y)
    Direction.RIGHT -> Coordinate(x + 1, y)
    Direction.DOWN -> Coordinate(x, y - 1)
}

fun Coordinate.walk(direction: Direction): Coordinate = when (direction) {
    Direction.UP -> Coordinate(x, y - 1)
    Direction.LEFT -> Coordinate(x - 1, y)
    Direction.RIGHT -> Coordinate(x + 1, y)
    Direction.DOWN -> Coordinate(x, y + 1)
}