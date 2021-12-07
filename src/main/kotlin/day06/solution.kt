package day06

import helpers.parseInput

fun main() {
    val input = parseInput("/day06.txt")
    val initialState = input.first().split(",").map(String::toInt)

    val part1 = part1(initialState)
    println("Solution to part1: $part1")

    val part2 = part2(initialState)
    println("Solution to part2: $part2")
}

private fun part1(initialState: List<Int>): Long = fishCounter(initialState, 80)

private fun part2(initialState: List<Int>): Long = fishCounter(initialState, 256)

private fun fishCounter(initialState: List<Int>, days: Int): Long {
    val fishState: List<Pair<Int, Long>> = initialState
        .groupBy { it }
        .map { it.key to it.value.size.toLong() }

    return (1..days).fold(fishState) { acc: List<Pair<Int, Long>>, _ ->
        val day0Fish = acc.firstOrNull { it.first == 0 }?.second ?: 0
        val day7Fish = acc.firstOrNull { it.first == 7 }?.second ?: 0

        (acc.filterNot { it.first == 0 || it.first == 7 }
            .map { it.first - 1 to it.second } + (8 to day0Fish) + (6 to day7Fish + day0Fish))
    }.sumOf { it.second }
}