package solutions.day19

import org.junit.jupiter.api.Test
import solutions.DayTest
import solutions.Solver
import solutions.TestCase
import solutions.createTestCase
import kotlin.test.assertEquals

class Day19Test : DayTest() {

    override fun getPart1TestCases(): List<TestCase> = createTestCase(
        "Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.\n" +
                "Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.\n",
        "33"
    )

    override fun getPart2TestCases(): List<TestCase> = createTestCase(
        "Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.\n" +
                "Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.\n",
        "3472"
    )

    @Test
    fun derp() {
        val res = Day19().solve(
            listOf(
                "Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.",
                "Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.",
            ),
            partTwo = true
        )

        assertEquals(res, "3472")
    }

    override fun getSolver(): Solver = Day19()

    @Test
    fun calculateMinutesUntilCanAfford() {
        val state = State(
            blueprint = Blueprint(
                nbr = 1,
                costs = mapOf(
                    Pair(RobotType.ORE, RobotCost(4)),
                    Pair(RobotType.CLAY, RobotCost(2)),
                    Pair(RobotType.OBSIDIAN, RobotCost(ore = 2, clay = 14)),
                    Pair(RobotType.GEODE, RobotCost(ore = 2, obsidian = 7)),
                )
            ),
            minute = 5,
            ores = 0,
            oreRobots = 1,
            clay = 0,
            clayRobots = 0,
            obsidian = 0,
            obsidianRobots = 0,
            geodes = 0,
            geodeRobots = 0,
            nextRobotToBuild = RobotType.ORE
        )

        val day19 = Day19()

        assertEquals(4, day19.calculateMinutesUntilCanAfford(state))
        assertEquals(2, day19.calculateMinutesUntilCanAfford(state.copy(nextRobotToBuild = RobotType.CLAY)))
        assertEquals(
            6,
            day19.calculateMinutesUntilCanAfford(
                state.copy(
                    clay = 2,
                    clayRobots = 2,
                    nextRobotToBuild = RobotType.OBSIDIAN
                )
            )
        )
        assertEquals(
            state.copy(
                clay = 2,
                clayRobots = 2,
                nextRobotToBuild = RobotType.OBSIDIAN
            ).gather(6), state.copy(
                clay = 2,
                clayRobots = 2,
                nextRobotToBuild = RobotType.OBSIDIAN
            ).copy(minute = state.minute + 6, clay = 14, ores = 6)
        )
    }

}