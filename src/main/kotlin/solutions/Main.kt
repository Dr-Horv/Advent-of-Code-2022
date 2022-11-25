import solutions.day01.Day1
import java.io.File
import kotlin.system.measureNanoTime

enum class Days {
    Day01,
    Day02,
    Day03,
    Day04,
    Day05,
    Day06,
    Day07,
    Day08,
    Day09,
    Day10,
    Day11,
    Day12,
    Day13,
    Day14,
    Day15,
    Day16,
    Day18,
    Day20,
    Day22,
    Day21,
    Day23,
    Day24,
    Day25
}

fun Long.toSeconds(): Double = this / (10e8)
fun Long.toMilliseconds(): Double = this / (10e5)

fun main(args: Array<String>) {

    val time = measureNanoTime {
        val partTwo = false
        val day = Days.Day01
        val input = getInput(day)
        val solver = when (day) {
            Days.Day01 -> Day1()
            else -> { throw NotImplementedError("Not implemented yet") }
        }

        printAnswer(day.name, solver.solve(input, partTwo))
    }

    println("Took ${time.toSeconds()} seconds")
    println("Took ${time.toMilliseconds()} milliseconds")


}

fun getInput(day: Days): List<String> {
    val solutions = "src/main/kotlin/solutions"
    return File("$solutions/${day.name.lowercase()}/input").readLines()
}


fun printAnswer(msg: String, answer: String) {
    println("$msg: $answer")
}