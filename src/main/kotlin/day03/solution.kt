package day03

import kotlin.math.ceil

fun main() {
//    val input = parseInput("/day03.txt")
    // test data
    val input = listOf(
        "00100",
        "11110",
        "10110",
        "10111",
        "10101",
        "01111",
        "00111",
        "11100",
        "10000",
        "11001",
        "00010",
        "01010",
    )

    val part1 = part1(input)
    println("Solution to part1: $part1")

    val part2 = part2(input)
    println("Solution to part2: $part2")
}

private fun part1(input: List<String>): Int {
    // gamma rating
    val gRating = staticRating(input) { column, nRows ->
        // find the most common char
        if (column.count { it == '1' } >= ceil(nRows / 2.0)) '1' else '0'
    }

    // epsilon rating
    val eRating = staticRating(input) { column, nRows ->
        // find the least common char
        if (column.count { it == '1' } <= ceil(nRows / 2.0)) '1' else '0'
    }

    return gRating * eRating
}

private fun part2(input: List<String>): Int {
    // oxygen rating
    val oRating = dynamicRating(input) { column, nRows ->
        if (column.count { it == '1' } >= ceil(nRows / 2.0)) '1' else '0'
    }

    // co2 scrubber rating
    val sRating = dynamicRating(input) { column, nRows ->
        if (column.count { it == '1' } < ceil(nRows / 2.0)) '1' else '0'
    }

    return oRating * sRating
}

private fun staticRating(input: List<String>, predicate: (column: String, nRows: Int) -> Char) =
    (0 until input.first().length).fold(emptyList()) { acc: List<Char>, i: Int ->
        val column = input.joinToString("") { it[i].toString() }
        val charAtIndex = predicate(column, input.size)
        acc.plus(charAtIndex)
    }.joinToString("").toInt(2)

private fun dynamicRating(input: List<String>, predicate: (column: String, nRows: Int) -> Char): Int =
    (0 until input.first().length).fold(input) { acc: List<String>, i: Int ->
        val column = acc.joinToString("") { it[i].toString() }
        val charAtIndex = predicate(column, acc.size)

        // if the accumulator only has one element left, we're done
        if (acc.size > 1) acc.filter { it[i] == charAtIndex } else acc
    }.first().toInt(2)
