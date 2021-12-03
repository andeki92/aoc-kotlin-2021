package day02

import helpers.parseInput

fun main() {
    val input = parseInput("/day02.txt")
    val movement = input.map { it.split(" ") }.map { Direction.valueOf(it[0]) to it[1].toInt() }

    // test data
//    val movement = listOf(
//        Direction.forward to 5,
//        Direction.down to 5,
//        Direction.forward to 8,
//        Direction.up to 3,
//        Direction.down to 8,
//        Direction.forward to 2,
//    )

    val part1 = part1(movement)
    println("Solution to part1: $part1")

    val part2 = part2(movement)
    println("Solution to part2: $part2")
}

private fun part1(movement: List<Pair<Direction, Int>>): Int {
    val verticalPosition = movement.filterNot { it.first == Direction.forward }
        .sumOf { if (it.first == Direction.up) -it.second else it.second }

    val horizontalPosition = movement.filter { it.first == Direction.forward }
        .sumOf { it.second }

    return verticalPosition * horizontalPosition
}

private fun part2(movement: List<Pair<Direction, Int>>): Int = movement.fold(Submarine()) { sub, (dir, dist) ->
    when (dir) {
        Direction.up -> sub.copy(aim = sub.aim - dist)
        Direction.down -> sub.copy(aim = sub.aim + dist)
        Direction.forward -> sub.copy(vPos = sub.aim * dist, hPos = sub.hPos + dist)
    }
}.let { sub -> sub.vPos * sub.hPos }

@Suppress("EnumEntryName")
private enum class Direction {
    forward,
    up,
    down,
}

/**
 * This class is not strictly required, but makes the part2-code easier
 * to read compared to using a Triple<Int, Int, Int>
 */
private data class Submarine(
    val vPos: Int = 0,
    val hPos: Int = 0,
    val aim: Int = 0
)
