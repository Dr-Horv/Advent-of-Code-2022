package solutions.day15

import solutions.Solver
import utils.Coordinate
import utils.manhattanDistance
import kotlin.math.abs

data class SensorBeaconPair(
    val sensor: Coordinate,
    val beacon: Coordinate,
    val distance: Int
)

fun SensorBeaconPair.distance(): Int = sensor.manhattanDistance(beacon)

class Day15(val testInstance: Boolean = false) : Solver {

    override fun solve(input: List<String>, partTwo: Boolean): String {
        val pairs = input.map { parseSensorBeaconPair(it) }

        return if (!partTwo) {
            part1(pairs)
        } else {
            return part2(pairs)
        }
    }

    private fun part2(pairs: List<SensorBeaconPair>): String {
        val bounds = if (testInstance) {
            20
        } else {
            4000000
        }
        val candidates = pairs.flatMap {
            val cs = mutableListOf<Coordinate>()
            for (x in ((it.sensor.x - it.distance - 1)..(it.sensor.x + it.distance + 1))) {
                val yStep = (it.distance + 1) - abs(it.sensor.x - x)
                val y1 = it.sensor.y + yStep
                val y2 = it.sensor.y - yStep
                for (y in listOf(y1, y2)) {
                    val c = Coordinate(x, y)
                    if (c.x in 0..bounds &&
                        c.y in 0..bounds &&
                        c.manhattanDistance(it.sensor) == (it.distance + 1)
                    ) {
                        cs.add(c)
                    }
                }
            }
            cs
        }
        for (c in candidates) {
            if (pairs.all { c.manhattanDistance(it.sensor) > it.distance }) {
                return (c.x * 4000000L + c.y).toString()
            }
        }

        throw Error("Not found")
    }

    private fun part1(pairs: List<SensorBeaconPair>): String {
        val rowToCheck = if (testInstance) {
            10
        } else {
            2000000
        }
        val maxRange = pairs.maxBy { it.distance() }.distance()
        val minX = pairs.flatMap { listOf(it.beacon.x, it.sensor.x) }.min() - maxRange - 10
        val maxX = pairs.flatMap { listOf(it.beacon.x, it.sensor.x) }.max() + maxRange + 10
        return (minX..maxX).count { x ->
            val pos = Coordinate(x = x, y = rowToCheck)
            pairs.any { pos != it.beacon && pos.manhattanDistance(it.sensor) <= it.distance() }
        }.toString()
    }


    private fun parseCoordinateInts(s: String): List<Int> = s.split(",")
        .map { it.trim() }
        .map { it.removeRange(0, 2) }
        .map { it.toInt() }

    private fun parseSensorBeaconPair(s: String): SensorBeaconPair {
        val parts = s.split(":")
        val sensorParts = parseCoordinateInts(parts[0].removePrefix("Sensor at "))
        val beaconParts = parseCoordinateInts(parts[1].removePrefix(" closest beacon is at "))
        val sensor = Coordinate(x = sensorParts[0], y = sensorParts[1])
        val beacon = Coordinate(x = beaconParts[0], y = beaconParts[1])
        return SensorBeaconPair(sensor = sensor, beacon = beacon, distance = sensor.manhattanDistance(beacon))
    }
}
