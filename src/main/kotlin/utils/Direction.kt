package utils

enum class Direction {
    UP,
    LEFT,
    RIGHT,
    DOWN
}

fun Direction.toTxt() = when (this) {
    Direction.UP -> "^"
    Direction.LEFT -> "<"
    Direction.RIGHT -> ">"
    Direction.DOWN -> "v"
}