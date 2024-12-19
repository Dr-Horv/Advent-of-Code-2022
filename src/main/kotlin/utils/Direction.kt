package utils

enum class Direction {
    UP,
    LEFT,
    RIGHT,
    DOWN;

    fun turnRight(): Direction {
        return when(this) {
            UP -> RIGHT
            LEFT -> UP
            RIGHT -> DOWN
            DOWN -> LEFT
        }
    }

    fun turnLeft(): Direction {
        return when(this) {
            UP -> LEFT
            LEFT -> DOWN
            RIGHT -> UP
            DOWN -> RIGHT
        }
    }
}

fun Direction.toTxt() = when (this) {
    Direction.UP -> "^"
    Direction.LEFT -> "<"
    Direction.RIGHT -> ">"
    Direction.DOWN -> "v"
}