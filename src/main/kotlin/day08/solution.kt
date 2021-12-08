package day08

import helpers.parseInput

private const val DISPLAY_1_COUNT = 2
private const val DISPLAY_4_COUNT = 4
private const val DISPLAY_7_COUNT = 3
private const val DISPLAY_8_COUNT = 7

private typealias Display = Pair<List<String>, List<String>>

fun main() {
    val input = parseInput("/day08.txt")
    val initialState: List<Display> = input.map { line ->
        line.split("|").let { it[0].trim().splitOnWhitespace() to it[1].trim().splitOnWhitespace() }
    }

    val part1 = part1(initialState)
    println("Solution to part1: $part1")

    val part2 = part2(initialState)
    println("Solution to part2: $part2")
}

private fun part1(initialState: List<Display>): Int = initialState.flatMap { it.second }.count {
    it.length in listOf(DISPLAY_1_COUNT, DISPLAY_4_COUNT, DISPLAY_7_COUNT, DISPLAY_8_COUNT)
}

private fun part2(initialState: List<Display>): Int =
    initialState.sumOf { (wires, output) ->
        val oneDisplay = wires.first { it.length == DISPLAY_1_COUNT }
        val fourDisplay = wires.first { it.length == DISPLAY_4_COUNT }
        val sevenDisplay = wires.first { it.length == DISPLAY_7_COUNT }
        val eightDisplay = wires.first { it.length == DISPLAY_8_COUNT }

        /* Find the identifying segments for each of the numbers above */
        val rightSegments = oneDisplay.toCharArray()
        val middleSegments = fourDisplay.filterNot { it in sevenDisplay }.toCharArray()
        val bottomSegments = eightDisplay.filterNot { it in sevenDisplay + fourDisplay }.toCharArray()

        output.map { display ->
            when (display.length) {
                DISPLAY_1_COUNT -> 1
                DISPLAY_4_COUNT -> 4
                DISPLAY_7_COUNT -> 7
                DISPLAY_8_COUNT -> 8
                5 -> when {
                    display.containsAll(*middleSegments) -> 5
                    display.containsAll(*bottomSegments) -> 2
                    else -> 3
                }
                6 -> when {
                    display.containsAll(*middleSegments, *rightSegments) -> 9
                    display.containsAll(*middleSegments) -> 6
                    else -> 0
                }
                else -> error("missing mapping for $display")
            }
        }.joinToString("") { it.toString() }.toInt()
    }

private fun String.splitOnWhitespace(): List<String> = this.split(" ")

/**
 * Helper method to check if a string contains all characters
 */
private fun String.containsAll(vararg chars: Char): Boolean = chars.all { it in this }
