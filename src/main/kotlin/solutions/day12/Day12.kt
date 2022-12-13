package solutions.day12

import solutions.Solver
import utils.Coordinate
import utils.Direction
import utils.step

class Node(
    val id: Int,
    val char: Char,
    val start: Boolean,
    val goal: Boolean,
    val elevation: Int,
    val neighbours: MutableMap<Direction, Node>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Node

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}

const val ZERO_ELEVATION = 'a'.code

fun Char.toElevation(): Int = this.code - ZERO_ELEVATION

class Day12 : Solver {

    override fun solve(input: List<String>, partTwo: Boolean): String {

        val map = mutableMapOf<Coordinate, Node>()
        var id = 1

        for ((y, line) in input.withIndex()) {
            for ((x, c) in line.toList().withIndex()) {
                val isStart = c == 'S'
                val isGoal = c == 'E'
                val elevation = if (isStart) {
                    'a'.toElevation()
                } else if (isGoal) {
                    'z'.toElevation()
                } else {
                    c.toElevation()
                }
                val n =
                    Node(
                        id = id,
                        char = c,
                        start = isStart,
                        goal = isGoal,
                        elevation = elevation,
                        neighbours = mutableMapOf()
                    )
                id++
                map[Coordinate(x, y)] = n
            }
        }


        map.forEach { (coordinate, node) ->
            Direction.values().forEach { direction ->
                val neighbour = map[coordinate.step(direction)]
                if (neighbour != null) {
                    if (neighbour.elevation <= (node.elevation + 1)) {
                        node.neighbours[direction] = neighbour
                    }
                }
            }
        }

        val path = if (!partTwo) {
            breathFirstSearch(map.values.first { it.start })!!
        } else {
            map.values.filter { it.elevation == 0 }
                .map {
                    breathFirstSearch(it)
                }
                .filterNotNull()
                .minBy { it.size }
        }

        return (path.size - 1).toString()
    }

    private fun getPath(cameFrom: MutableMap<Node, Node>, goal: Node, start: Node): List<Node> {
        var curr = goal
        val path = mutableListOf(curr)
        while (curr != start) {
            curr = cameFrom[curr]!!
            path.add(curr)
        }
        return path
    }

    private fun breathFirstSearch(start: Node): List<Node>? {
        val cameFrom = mutableMapOf<Node, Node>()
        val openSet = mutableListOf(start)
        val explored = mutableSetOf(start)

        while (openSet.isNotEmpty()) {
            val curr = openSet.removeFirst()
            if (curr.goal) {
                return getPath(cameFrom, curr, start)
            }

            for (n in curr.neighbours.values) {
                if (explored.contains(n)) {
                    continue
                }
                explored.add(n)
                cameFrom[n] = curr
                openSet.add(n)
            }
        }

        return null
    }
}
