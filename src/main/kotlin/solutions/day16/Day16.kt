package solutions.day16

import solutions.Solver
import utils.searchBreathFirst

data class Valve(val name: String, val flowRate: Int, val neighbourValves: MutableList<Valve>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Valve
        return name == other.name
    }

    override fun hashCode(): Int = name.hashCode()
}

data class Node(
    val position: String,
    val minute: Int,
    val openValves: Set<String>,
    val totalFlowRate: Int,
    val released: Int
)

class Day16 : Solver {

    override fun solve(input: List<String>, partTwo: Boolean): String {

        val valves = mutableMapOf<String, Valve>()
        val connectionMap = mutableMapOf<String, List<String>>()
        val regex = "Valve (.*) has flow rate=(\\d*); tunnels? leads? to valves? (.*)".toRegex()
        val start = Node(position = "AA", minute = 0, openValves = emptySet(), totalFlowRate = 0, released = 0)

        for (line in input) {
            val result = regex.find(line)
            if (result != null) {
                val (name, flowRateStr, neighbourNames) = result.destructured
                val valve = Valve(name = name, flowRate = flowRateStr.toInt(), neighbourValves = mutableListOf())
                valves[name] = valve
                connectionMap[name] = neighbourNames.split(",").map { it.trim() }
            }
        }

        connectionMap.forEach { (name, neighbourNames) ->
            val v = valves.getValue(name)
            val neighbours = neighbourNames.map { valves.getValue(it) }
            v.neighbourValves.addAll(neighbours)
        }

        val distances = mutableMapOf<Pair<String, String>, Int>()

        valves.values.forEach { n1 ->
            valves.values.forEach { n2 ->
                if (n1.name != n2.name) {
                    val path = searchBreathFirst(n1, { n -> n.neighbourValves }, { n -> n.name == n2.name })
                    distances[Pair(n1.name, n2.name)] = path!!.size - 1
                } else {
                    distances[Pair(n1.name, n2.name)] = 0
                }
            }
        }

        val paths = mutableListOf<List<Node>>()

        val timeLimit = if (!partTwo) {
            30
        } else {
            26
        }

        println("Find path time")
        findPaths(listOf(start), paths, distances, valves.values.filter { it.flowRate > 0 }.toSet(), timeLimit)
        println("Paths found: ${paths.size}")

        if (!partTwo) {
            val max = paths.maxBy { it.last().released }
            println(max)

            return max.last().released.toString()
        } else {
            val pairs = mutableListOf<Pair<Node, Node>>()
            var currMax = -1
            for ((index, p1) in paths.withIndex()) {
                for (i2 in (index + 1) until paths.size) {
                    val n1 = p1.last()
                    val n2 = paths[i2].last()
                    if (n1.released + n2.released < currMax) {
                        continue
                    }

                    if (n1.openValves.none { n2.openValves.contains(it) }) {
                        currMax = n1.released + n2.released
                        pairs.add(Pair(n1, n2))
                    }
                }
            }

            println("Pairs: " + pairs.size)
            val best = pairs.maxBy { it.first.released + it.second.released }

            return (best.first.released + best.second.released).toString()
        }


    }

    private fun findPaths(
        currPath: List<Node>,
        paths: MutableList<List<Node>>,
        distances: Map<Pair<String, String>, Int>,
        closedVales: Set<Valve>,
        timeLimit: Int
    ) {
        val n = currPath.last()
        val timeLeft = timeLimit - n.minute
        if (timeLeft == 0) {
            paths.add(currPath)
            return
        }
        val nextNodeWaiting = n.copy(minute = timeLimit, released = n.released + (n.totalFlowRate * timeLeft))
        findPaths(currPath + nextNodeWaiting, paths, distances, closedVales, timeLimit)

        for (valve in closedVales) {
            val dist = distances.getValue(Pair(n.position, valve.name))
            val timeToMoveAndOpen = dist + 1
            if (timeLeft > timeToMoveAndOpen) {
                val openedValve = Node(
                    position = valve.name,
                    minute = n.minute + timeToMoveAndOpen,
                    openValves = n.openValves + valve.name,
                    totalFlowRate = n.totalFlowRate + valve.flowRate,
                    released = n.released + (timeToMoveAndOpen * n.totalFlowRate)
                )
                findPaths(currPath + openedValve, paths, distances, closedVales - valve, timeLimit)
            }
        }
    }

}
