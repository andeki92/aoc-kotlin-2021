package day11

import helpers.neighboursIndexed
import helpers.parseInput


fun main() {
    val input = parseInput("/day11.txt")
    val initialState = input.map { it.map(Char::digitToInt) }

//    println("Initial state: $initialState")

    val part1 = part1(initialState)
    println("Solution to part1: $part1")

    val part2 = part2(initialState)
    println("Solution to part2: $part2")
}

private fun part1(initialState: List<List<Int>>): Int =
    calculateFlashes(initialState, 100) { false }.first

private fun part2(initialState: List<List<Int>>): Int =
    calculateFlashes(initialState) { state -> state.all { rows -> rows.all { it == 0 } } }.second

/**
 * This is quite horrible, but what gives :/
 */
private fun calculateFlashes(
    initialState: List<List<Int>>,
    steps: Int = Int.MAX_VALUE,
    predicate: (state: List<List<Int>>) -> Boolean
): Pair<Int, Int> =
    (1..steps).fold(Triple(initialState, 0 /* flashes */, 0 /* steps */)) { (state, flashes, steps), _ ->
        if (predicate(state)) return@calculateFlashes flashes to steps // Exit the loop

        // Increment and make the state mutable
        val newState = state.map { row -> row.map { it.inc() }.toMutableList() }.toMutableList()
        var newFlashes = 0

        while (newState.any { row -> row.any { it > 9 } }) {
            val currentState = newState.map { it.toList() } // Lock the list

            // Iterate over the array and increase any flashed members
            currentState.forEachIndexed { y, row ->
                row.withIndex().filter { it.value > 9 }
                    .forEach { (x, _) ->
                        newState.neighboursIndexed(x, y, true)
                            .filter { (value, _) -> value != 0 }
                            .forEach { (value, position) ->
                                newState[position.second][position.first] = value + 1
                            }
                        newFlashes++
                        newState[y][x] = 0
                    }
            }
        }
        Triple(newState, newFlashes + flashes, steps + 1)
    }.let { it.second to it.third } // Return number of flashes and steps to get there
