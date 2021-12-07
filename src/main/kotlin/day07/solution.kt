package day07

import helpers.parseInput
import kotlin.math.abs

fun main() {
    val input = parseInput("/day07.txt")
    val initialState = input.first().split(",").map(String::toInt)

    println(initialState)

    val part1 = part1(initialState)
    println("Solution to part1: $part1")

    val part2 = part2(initialState)
    println("Solution to part2: $part2")
}

private fun part1(initialState: List<Int>): Int = initialState.let { positions ->
    val median = positions.median()
    positions.sumOf { abs(it - median) }
}

private fun part2(initialState: List<Int>): Int = initialState.let { positions ->
    val maxPos = positions.maxOrNull() ?: 0
    val minPos = positions.minOrNull() ?: 0

    // Brute force solution
    (minPos..maxPos).map { pos -> positions.sumOf { abs(it - pos) * (abs(it - pos) + 1) / 2 } }.minOrNull() ?: error("No values")
}


private fun List<Int>.median(): Int = this.sorted().let {
    when {
        it.size % 2 == 0 -> (it[it.size / 2] + it[it.size / 2 - 1]) / 2
        else -> it[it.size / 2]
    }
}

