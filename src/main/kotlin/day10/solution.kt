package day10

import helpers.parseInput
import java.util.*

private val opens = setOf('(', '[', '{', '<')
private val closes = setOf(')', ']', '}', '>')

fun main() {
    val input = parseInput("/day10.txt")
    val initialState = input.map(String::toCharArray)

//    println("Initial state: $initialState")

    val part1 = part1(initialState)
    println("Solution to part1: $part1")

    val part2 = part2(initialState)
    println("Solution to part2: $part2")
}

private fun part1(initialState: List<CharArray>): Int = getCorruptedLineScores(initialState).sum()

private fun part2(initialState: List<CharArray>): Long = getCorruptedLineScores(initialState)
    .mapIndexedNotNull { index, i -> if (i != 0) null else initialState[index] }
    .map { chars ->
        val queue: ArrayDeque<Char> = ArrayDeque()
        chars.forEach { c ->
            when (c) {
                in opens -> queue.add(c)
                in closes -> queue.removeLast()
                else -> error("Invalid char $c")
            }
        }
        queue.reversed().map(::getIncompleteScore).fold(0L) { acc, i: Int -> 5 * acc + i }
    }.sorted().let { it[it.size / 2] }

/**
 * Retrieve the score for all the corrupted lines.
 */
private fun getCorruptedLineScores(initialState: List<CharArray>) = initialState.map { chars ->
    val queue: ArrayDeque<Char> = ArrayDeque()

    chars.forEach { c ->
        when (c) {
            in opens -> queue.add(c)
            in closes -> {
                if (queue.last() != getOpeningChar(c)) {
                    return@map getCorruptedScore(c)
                } else {
                    queue.removeLast()
                }
            }
            else -> error("Invalid char $c")
        }
    }
    0 // if no values were found we don't care about it
}

private fun getOpeningChar(c: Char) = when (c) {
    ')' -> '('
    ']' -> '['
    '}' -> '{'
    '>' -> '<'
    else -> error("Invalid char $c")
}

private fun getCorruptedScore(c: Char) = when (c) {
    ')' -> 3
    ']' -> 57
    '}' -> 1197
    '>' -> 25137
    else -> error("Invalid char $c")
}

private fun getIncompleteScore(c: Char) = when (c) {
    '(' -> 1
    '[' -> 2
    '{' -> 3
    '<' -> 4
    else -> error("Invalid char $c")
}