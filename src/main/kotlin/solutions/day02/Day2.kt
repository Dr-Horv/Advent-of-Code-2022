package solutions.day02

import solutions.Solver

enum class Moves {
    ROCK,
    PAPER,
    SCISSORS
}

enum class Outcome {
    DRAW,
    LOST,
    WON
}

class Day2 : Solver {

    private fun roundScore(input: String, partTwo: Boolean): Int {
        val moves = input.split(" ")
        val opponentMove = when (moves.first()) {
            "A" -> Moves.ROCK
            "B" -> Moves.PAPER
            "C" -> Moves.SCISSORS
            else -> {
                throw Error("Not expected")
            }
        }

        val yourMove = if (!partTwo) {
            when (moves.last()) {
                "X" -> Moves.ROCK
                "Y" -> Moves.PAPER
                "Z" -> Moves.SCISSORS
                else -> {
                    throw Error("Not expected")
                }
            }
        } else {
            when (moves.last()) {
                "X" -> getLosingMove(opponentMove)
                "Y" -> opponentMove
                "Z" -> getWinningMove(opponentMove)
                else -> {
                    throw Error("Not expected")
                }
            }
        }


        val outcome = getOutcome(yourMove, opponentMove)

        return getMoveScore(yourMove) + getOutcomeScore(outcome)
    }

    private fun getWinningMove(opponentMove: Moves): Moves = when (opponentMove) {
        Moves.ROCK -> Moves.PAPER
        Moves.PAPER -> Moves.SCISSORS
        Moves.SCISSORS -> Moves.ROCK
    }

    private fun getLosingMove(opponentMove: Moves): Moves = when (opponentMove) {
        Moves.ROCK -> Moves.SCISSORS
        Moves.PAPER -> Moves.ROCK
        Moves.SCISSORS -> Moves.PAPER
    }

    private fun getOutcomeScore(outcome: Outcome): Int {
        return when (outcome) {
            Outcome.DRAW -> 3
            Outcome.LOST -> 0
            Outcome.WON -> 6
        }
    }

    private fun getMoveScore(move: Moves): Int = when (move) {
        Moves.ROCK -> 1
        Moves.PAPER -> 2
        Moves.SCISSORS -> 3
    }

    private fun getOutcome(yourMove: Moves, opponentMove: Moves): Outcome = when (yourMove) {
        Moves.ROCK -> when (opponentMove) {
            Moves.ROCK -> Outcome.DRAW
            Moves.PAPER -> Outcome.LOST
            Moves.SCISSORS -> Outcome.WON
        }

        Moves.PAPER -> when (opponentMove) {
            Moves.ROCK -> Outcome.WON
            Moves.PAPER -> Outcome.DRAW
            Moves.SCISSORS -> Outcome.LOST
        }

        Moves.SCISSORS -> when (opponentMove) {
            Moves.ROCK -> Outcome.LOST
            Moves.PAPER -> Outcome.WON
            Moves.SCISSORS -> Outcome.DRAW
        }
    }

    override fun solve(input: List<String>, partTwo: Boolean): String {
        return input.sumOf { roundScore(it, partTwo) }.toString()
    }
}