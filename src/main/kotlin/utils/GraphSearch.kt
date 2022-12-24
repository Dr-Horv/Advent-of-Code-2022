package utils

import java.util.*


private fun <Node> getPath(cameFrom: MutableMap<Node, Node>, goal: Node, start: Node): List<Node> {
    var curr = goal
    val path = mutableListOf(curr)
    while (curr != start) {
        curr = cameFrom[curr]!!
        path.add(curr)
    }
    return path
}

fun <Node> searchBreathFirst(
    start: Node,
    getNeighbours: (n: Node) -> List<Node>,
    isGoal: (n: Node) -> Boolean
): List<Node>? {
    val cameFrom = mutableMapOf<Node, Node>()
    val openSet = mutableListOf(start)
    val explored = mutableSetOf(start)

    while (openSet.isNotEmpty()) {
        val curr = openSet.removeFirst()
        if (isGoal(curr)) {
            return getPath(cameFrom, curr, start)
        }

        for (n in getNeighbours(curr)) {
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


fun <Node> aStar(
    start: Node,
    getNeighbours: (n: Node) -> List<Node>,
    isGoal: (n: Node) -> Boolean,
    h: (n: Node) -> Double,
    dist: (n1: Node, n2: Node) -> Double
): List<Node>? {

    val closedSet: MutableCollection<Node> = mutableSetOf()
    val cameFrom = mutableMapOf<Node, Node>()

    val gScore = mutableMapOf<Node, Double>().withDefault { Double.MAX_VALUE }
    gScore[start] = 0.0

    val fScore = mutableMapOf<Node, Double>().withDefault { Double.MAX_VALUE }
    fScore[start] = h(start)

    val openSet: PriorityQueue<Node> =
        PriorityQueue { e1, e2 -> fScore.getValue(e1).compareTo(fScore.getValue(e2)) }

    openSet.add(start)

    var i = 0
    while (openSet.isNotEmpty()) {
        val current = openSet.poll()!!
        if (isGoal(current)) {
            return getPath(cameFrom, current, start)
        }

        if (i % 1000 == 0) {
            println(openSet.size)
        }

        closedSet.add(current)

        for (neighbor in getNeighbours(current)) {
            if (closedSet.contains(neighbor)) {
                continue
            }

            val tentativeGScore = gScore.getValue(current) + 1
            if (tentativeGScore < gScore.getValue(neighbor)) {
                cameFrom[neighbor] = current
                gScore[neighbor] = tentativeGScore
                fScore[neighbor] = tentativeGScore + h(neighbor)
                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor)
                }
            }
        }
        i++
    }

    return null
}