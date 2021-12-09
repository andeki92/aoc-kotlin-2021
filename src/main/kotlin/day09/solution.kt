package day09

import helpers.neighbours
import helpers.neighboursIndexed
import helpers.parseInput

private typealias Position = Pair<Int, Int>

fun main() {
    val input = parseInput("/day09.txt")
    val initialState: List<List<Int>> = input.map { line -> line.map(Character::getNumericValue) }

//    println("Initial state: $initialState")

    val part1 = part1(initialState)
    println("Solution to part1: $part1")

    val part2 = part2(initialState)
    println("Solution to part2: $part2")
}

private fun part1(initialState: List<List<Int>>): Int = initialState.flatMapIndexed { yIdx, column ->
    column.mapIndexed { xIdx, value ->
        if (initialState.neighbours(xIdx, yIdx).all { it > value }) value + 1 else 0
    }
}.sum()

private fun part2(initialState: List<List<Int>>): Int = initialState.flatMapIndexed { yIdx, column ->
    column.mapIndexed { xIdx, value ->
        if (initialState.neighbours(xIdx, yIdx).all { it > value }) {
            initialState.basinSize(xIdx, yIdx).size
        } else 0
    }
}
    .filterNot { it == 0 }
    .sortedDescending().take(3) // take the 3 largest elements
    .reduce { acc, i -> acc * i } // multiply the 3 remaining elements

/**
 * This probably could've been done easier, but insisting on using recursion, this is where I got.
 *
 * There are definitely duplicates added onto the output, but using a set we omit the real cause of the problem
 */
private fun List<List<Int>>.basinSize(x: Int, y: Int, visited: Set<Position> = emptySet()): Set<Position> {
    val posValue = this.getOrNull(y)?.getOrNull(x) ?: error("No value in $x, $y")
    val newVisited = visited.plus(x to y)
    val largerNeighbours = neighboursIndexed(x, y)
        .filter { (_, position) -> position !in newVisited }
        .filter { (value, _) -> value != 9 }
        .filter { (value, _) -> value > posValue }

    return if (largerNeighbours.isEmpty()) {
        newVisited
    } else {
        largerNeighbours.map { it.second }
            .fold(emptySet()) { acc: Set<Position>, (xNew, yNew): Position ->
                acc + this.basinSize(xNew, yNew, newVisited)
            }
    }
}

