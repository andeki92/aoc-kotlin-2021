package day04

import helpers.parseInput

private typealias Board = List<Int>

private const val BOARD_MARKER = -1
private const val WINNING_SUM = 5 * BOARD_MARKER // -5

fun main() {
    val input = parseInput("/day04.txt")

    val draws = input.first().split(",").map { it.toInt() }
    val boards = input.drop(2).windowed(size = 5, step = 6)
        .map { board ->
            board.flatMap { row ->
                row.trim().replace("\\s+".toRegex(), ",").split(",").map(String::toInt)
            }
        }

    val part1 = part1(draws, boards)
    println("Solution to part1: $part1")

    val part2 = part2(draws, boards)
    println("Solution to part2: $part2")
}

private fun part1(draws: List<Int>, game: List<Board>): Int {
    draws.fold(game) { acc: List<Board>, draw: Int ->
        val boards = acc.map { board -> board.map { if (it == draw) BOARD_MARKER else it } }
        val winner = boards.associateBy(Board::hasWon).filterKeys { it }.values.firstOrNull()

        if (winner != null) return@part1 winner.filterNot { it == BOARD_MARKER }.sum() * draw else boards
    }
    error("No winning boards")
}


private fun part2(draws: List<Int>, game: List<Board>): Int {
    draws.fold(game) { acc: List<Board>, draw: Int ->
        val boards = acc.map { board -> board.map { if (it == draw) BOARD_MARKER else it } }

        if (boards.size == 1) return@part2 boards.first().filterNot { it == BOARD_MARKER }.sum() * draw

        boards.filterNot(Board::hasWon)
    }
    error("No winning boards")
}

private fun Board.hasWon(): Boolean {
    val hasWinningRows = this.windowed(5, 5).any { it.sum() == WINNING_SUM }
    val hasWinningColumns =
        (0 until 5).any { this.filterIndexed { index, _ -> (index) % 5 == it }.sum() == WINNING_SUM }

    return hasWinningRows || hasWinningColumns
}

