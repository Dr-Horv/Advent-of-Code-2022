package solutions.day19

import solutions.Solver
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.max

enum class RobotType {
    ORE,
    CLAY,
    OBSIDIAN,
    GEODE
}

data class RobotCost(val ore: Int, val clay: Int = 0, val obsidian: Int = 0)

data class Blueprint(
    val nbr: Int,
    val costs: Map<RobotType, RobotCost>
)

data class State(
    val blueprint: Blueprint,
    val minute: Int,
    val ores: Int,
    val oreRobots: Int,
    val clay: Int,
    val clayRobots: Int,
    val obsidian: Int,
    val obsidianRobots: Int,
    val geodes: Int,
    val geodeRobots: Int,
    val nextRobotToBuild: RobotType
)

fun State.canAffordToBuild(): Boolean {
    val cost = blueprint.costs.getValue(nextRobotToBuild)
    return ores >= cost.ore &&
            clay >= cost.clay &&
            obsidian >= cost.obsidian
}

fun State.build(next: RobotType = this.nextRobotToBuild): State {
    val cost = blueprint.costs.getValue(nextRobotToBuild)
    var oreRobotsNext = oreRobots
    var clayRobotsNext = clayRobots
    var obsidianRobotsNext = obsidianRobots
    var geodeRobotsNext = geodeRobots

    when (nextRobotToBuild) {
        RobotType.ORE -> oreRobotsNext++
        RobotType.CLAY -> clayRobotsNext++
        RobotType.OBSIDIAN -> obsidianRobotsNext++
        RobotType.GEODE -> geodeRobotsNext++
    }

    return this.copy(
        ores = ores - cost.ore,
        clay = clay - cost.clay,
        obsidian = obsidian - cost.obsidian,
        oreRobots = oreRobotsNext,
        clayRobots = clayRobotsNext,
        obsidianRobots = obsidianRobotsNext,
        geodeRobots = geodeRobotsNext,
        nextRobotToBuild = next
    )
}


fun State.gather(n: Int = 1): State = this.copy(
    minute = minute + n,
    ores = ores + (oreRobots * n),
    clay = clay + (clayRobots * n),
    obsidian = obsidian + (obsidianRobots * n),
    geodes = geodes + (geodeRobots * n)
)


class Day19 : Solver {

    override fun solve(input: List<String>, partTwo: Boolean): String {
        val regex =
            "Blueprint (\\d+): Each ore robot costs (\\d+) ore\\. Each clay robot costs (\\d+) ore\\. Each obsidian robot costs (\\d+) ore and (\\d+) clay\\. Each geode robot costs (\\d+) ore and (\\d+) obsidian\\.".toRegex()

        val blueprints = mutableListOf<Blueprint>()
        for (line in input) {
            val result = regex.find(line)
            if (result != null) {
                val (nbrS, orcs, crcs, orocs, orccs, grocs, grobcs) = result.destructured
                val oreRobotCost = RobotCost(orcs.toInt())
                val clayRobotCost = RobotCost(crcs.toInt())
                val obsidianRobotCost = RobotCost(orocs.toInt(), orccs.toInt())
                val geodeRobotCost = RobotCost(ore = grocs.toInt(), obsidian = grobcs.toInt())
                val costs = mapOf(
                    Pair(RobotType.ORE, oreRobotCost),
                    Pair(RobotType.CLAY, clayRobotCost),
                    Pair(RobotType.OBSIDIAN, obsidianRobotCost),
                    Pair(RobotType.GEODE, geodeRobotCost)
                )
                blueprints.add(
                    Blueprint(
                        nbrS.toInt(),
                        costs
                    )
                )
            }

            if (partTwo && blueprints.size == 3) {
                break
            }
        }

        val end = if (!partTwo) {
            24
        } else {
            32
        }

        val blueprintOutcome = mutableListOf<Pair<Blueprint, Int>>()
        for (b in blueprints) {
            val max = findOutcomes(b, end)
            blueprintOutcome += Pair(b, max)
        }

        return if (!partTwo) {
            blueprintOutcome.sumOf { it.first.nbr * it.second }.toString()
        } else {
            blueprintOutcome.fold(1) { acc, pair -> pair.second * acc }.toString()
        }


    }

    private fun findOutcomes(blueprint: Blueprint, end: Int): Int {
        val start1 = State(blueprint, 0, 0, 1, 0, 0, 0, 0, 0, 0, RobotType.ORE)
        val start2 = State(blueprint, 0, 0, 1, 0, 0, 0, 0, 0, 0, RobotType.CLAY)
        val inMemoryCache = mutableMapOf(Pair(end, 0))

        return max(
            findOutcomesFromState(start1, end, inMemoryCache),
            findOutcomesFromState(start2, end, inMemoryCache)
        )

    }


    private fun findOutcomesFromState(state: State, end: Int, cache: MutableMap<Int, Int>): Int {
        val timeLeft = end - state.minute
        if (timeLeft == 0) {
            if (cache.getValue(end) < state.geodes) {
                cache[end] = state.geodes
            }
            return state.geodes
        }

        val estimatedBest = state.geodes + state.geodeRobots * timeLeft + (timeLeft * (timeLeft + 1)) / 2

        if (estimatedBest < cache.getValue(end)) {
            return -1
        }

        if (state.canAffordToBuild()) {
            val potentialRobotsToBuild = RobotType.values().filter { shouldBuildMore(state.build(), it) }
            return potentialRobotsToBuild.maxOf {
                findOutcomesFromState(
                    state.gather().build(it),
                    end,
                    cache
                )
            }
        } else {
            val minutesUntilCanAfford = calculateMinutesUntilCanAfford(state)
            return if (minutesUntilCanAfford < timeLeft) {
                findOutcomesFromState(state.gather(minutesUntilCanAfford), end, cache)
            } else {
                findOutcomesFromState(state.gather(timeLeft), end, cache)
            }
        }
    }


    private fun shouldBuildMore(state: State, type: RobotType): Boolean = when (type) {
        RobotType.ORE -> state.oreRobots < state.blueprint.costs.values.maxOf { it.ore }
        RobotType.CLAY -> state.clayRobots < state.blueprint.costs.values.maxOf { it.clay }
        RobotType.OBSIDIAN -> state.clayRobots > 0 && state.obsidianRobots < state.blueprint.costs.values.maxOf { it.obsidian }
        RobotType.GEODE -> state.obsidianRobots > 0
    }

    fun calculateMinutesUntilCanAfford(state: State): Int {
        val cost = state.blueprint.costs.getValue(state.nextRobotToBuild)
        val oreMinutes = if (state.ores < cost.ore) {
            ceil(abs(state.ores - cost.ore) / state.oreRobots.toDouble()).toInt()
        } else {
            0
        }
        val clayMinutes = if (state.clay < cost.clay) {
            ceil(abs(state.clay - cost.clay) / state.clayRobots.toDouble()).toInt()
        } else {
            0
        }
        val obsidianMinutes = if (state.obsidian < cost.obsidian) {
            ceil(abs(state.obsidian - cost.obsidian) / state.obsidianRobots.toDouble()).toInt()
        } else {
            0
        }

        return listOf(oreMinutes, clayMinutes, obsidianMinutes).max()
    }
}
