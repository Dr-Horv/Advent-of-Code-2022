package solutions.day08

import solutions.Solver
import utils.Coordinate
import kotlin.math.max

class Day8 : Solver {

    override fun solve(input: List<String>, partTwo: Boolean): String {

        val map = mutableMapOf<Coordinate, Int>()

        val WIDTH = input[0].length
        val HEIGHT = input.size

        for ((y, line) in input.withIndex()) {
            for ((x, char) in line.toList().withIndex()) {
                val coord = Coordinate(x = x, y = y)
                val height = char.digitToInt()
                map[coord] = height
            }
        }


        if (!partTwo) {
            var visible = 0 //WIDTH * 2 + HEIGHT * 2 - 4

            for (y in 0 until HEIGHT) {
                for (x in 0 until WIDTH) {
                    val c = Coordinate(x, y)
                    if (isVisible(c, map, WIDTH, HEIGHT)) {
                        visible++
                    }
                }
            }
            return visible.toString()
        } else {
            var maxScenicScore = 0
            for (y in 0 until HEIGHT) {
                for (x in 0 until WIDTH) {
                    val c = Coordinate(x, y)
                    val score = calculateScenicScore(c, map, WIDTH, HEIGHT)
                    maxScenicScore = max(maxScenicScore, score)
                }
            }
            return maxScenicScore.toString()
        }


    }

    private fun calculateScenicScore(coordinate: Coordinate, map: Map<Coordinate, Int>, width: Int, height: Int): Int {
        val treeHeight = map[coordinate]!!

        var upScore = 0
        for (y in (coordinate.y - 1 downTo 0)) {
            val c = coordinate.copy(y = y)
            val currTreeHeight = map[c]!!
            upScore++

            if (currTreeHeight >= treeHeight) {
                break
            }
        }

        var leftScore = 0
        for (x in (coordinate.x - 1 downTo 0)) {
            val currTreeHeight = map[coordinate.copy(x = x)]!!
            leftScore++
            if (currTreeHeight >= treeHeight) {
                break
            }
        }

        var rightScore = 0
        for (x in (coordinate.x + 1 until width)) {
            val currTreeHeight = map[coordinate.copy(x = x)]!!
            rightScore++
            if (currTreeHeight >= treeHeight) {
                break
            }
        }


        var downScore = 0
        for (y in (coordinate.y + 1 until height)) {
            val currTreeHeight = map[coordinate.copy(y = y)]!!
            downScore++
            if (currTreeHeight >= treeHeight) {
                break
            }
        }


        return leftScore * rightScore * downScore * upScore
    }

    private fun isVisible(coordinate: Coordinate, map: Map<Coordinate, Int>, width: Int, height: Int): Boolean {
        val treeHeight = map[coordinate]!!
        if ((0 until coordinate.x).map {
                map.getOrElse(Coordinate(it, coordinate.y)) { throw Error("Not found: " + it + "," + coordinate.y) }
            }.all { it < treeHeight }) {
            return true
        }

        if ((width - 1 downTo (coordinate.x + 1)).map {
                map.getOrElse(Coordinate(it, coordinate.y)) { throw Error("Not found: " + it + "," + coordinate.y) }
            }.all { it < treeHeight }) {
            return true
        }

        if ((0 until coordinate.y).map {
                map.getOrElse(Coordinate(coordinate.x, it)) { throw Error("Not found: " + it + "," + coordinate.y) }
            }.all { it < treeHeight }) {
            return true
        }

        if ((height - 1 downTo (coordinate.y + 1)).map {
                map.getOrElse(Coordinate(coordinate.x, it)) { throw Error("Not found: " + it + "," + coordinate.y) }
            }.all { it < treeHeight }) {
            return true
        }


        return false
    }
}
