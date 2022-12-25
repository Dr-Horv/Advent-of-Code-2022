package solutions.day18

import solutions.Solver
import kotlin.math.abs

data class Coordinate3D(val x: Int, val y: Int, val z: Int) {
    override fun toString(): String {
        return "($x, $y, $z)"
    }
}

operator fun Coordinate3D.plus(coordinate: Coordinate3D) =
    Coordinate3D(x + coordinate.x, y + coordinate.y, z + coordinate.z)

fun Coordinate3D.manhattanDistance(coordinate: Coordinate3D): Int =
    abs(coordinate.x - x) + abs(coordinate.y - y) + abs(coordinate.z - z)


val STEPS = (-2..2).flatMap { x ->
    (-2..2).flatMap { y ->
        (-2..2).map { z -> Coordinate3D(x, y, z) }
    }
}.filter { c -> Coordinate3D(0, 0, 0).manhattanDistance(c) == 1 }

class Day18 : Solver {

    override fun solve(input: List<String>, partTwo: Boolean): String {

        val coordinates = input.map {
            val parts = it.split(",").map { c -> c.trim().toInt() }
            Coordinate3D(parts[0], parts[1], parts[2])
        }.toSet()


        if (!partTwo) {
            val exposed = coordinates.map {
                val neighbours = STEPS.map { step -> it.plus(step) }
                    .count { c -> coordinates.contains(c) }
                6 - neighbours
            }

            return exposed.sum().toString()
        }

        val maxX = coordinates.maxOf { it.x } + 1
        val maxY = coordinates.maxOf { it.y } + 1
        val maxZ = coordinates.maxOf { it.z } + 1

        val cube = mutableMapOf<Coordinate3D, Boolean>().withDefault { c ->
            when {
                c.x in -1..maxX &&
                        c.y in -1..maxY &&
                        c.z in -1..maxZ -> true

                else -> false
            }
        }
        coordinates.forEach { cube[it] = false }
        val emptyCubes = searchBreathFirst(
            Coordinate3D(0, 0, 0)
        ) { n ->
            STEPS.map { step -> n.plus(step) }
                .filter { c -> cube.getValue(c) }
        }

        return emptyCubes.flatMap { ec -> STEPS.map { step -> ec.plus(step) }.filter { n -> coordinates.contains(n) } }
            .count().toString()


    }

    fun <Node> searchBreathFirst(
        start: Node,
        getNeighbours: (n: Node) -> List<Node>
    ): Set<Node> {
        val openSet = mutableListOf(start)
        val explored = mutableSetOf(start)

        while (openSet.isNotEmpty()) {
            val curr = openSet.removeFirst()

            for (n in getNeighbours(curr)) {
                if (explored.contains(n)) {
                    continue
                }
                explored.add(n)
                openSet.add(n)
            }
        }

        return explored
    }
}
