package solutions

import solutions.day01.Day1
import solutions.day02.Day2
import solutions.day03.Day3
import solutions.day04.Day4
import solutions.day05.Day5
import solutions.day06.Day6
import solutions.day07.Day7
import solutions.day08.Day8
import solutions.day09.Day9

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
        val partTwo = true
        val day = Days.Day09
        val input = getInput(day)
        val solver = when (day) {
            Days.Day01 -> Day1()
            Days.Day02 -> Day2()
            Days.Day03 -> Day3()
            Days.Day04 -> Day4()
            Days.Day05 -> Day5()
            Days.Day06 -> Day6()
            Days.Day07 -> Day7()
            Days.Day08 -> Day8()
            Days.Day09 -> Day9()
            else -> {
                throw NotImplementedError("Not implemented yet")
            }
        }

        printAnswer(day.name, solver.solve(input, partTwo))
    }

    println("Took ${time.toSeconds()} seconds")
    println("Took ${time.toMilliseconds()} milliseconds")


}

fun getInput(day: Days): List<String> {
    val solutions = "src/main/kotlin/solutions"
    return File("$solutions/${day.name.lowercase()}/input.txt").readLines()
}


fun printAnswer(msg: String, answer: String) {
    println("$msg: $answer")
}