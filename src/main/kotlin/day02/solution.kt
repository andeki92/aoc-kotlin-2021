package day02

import helpers.parseInput

@Suppress("EnumEntryName")
private enum class Direction {
    forward,
    up,
    down,
}

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

private fun part2(movement: List<Pair<Direction, Int>>): Int {
    var verticalPosition = 0
    var horizontalPosition = 0
    var aim = 0

    movement.forEach { (direction, distance) ->
        when(direction) {
            Direction.up -> {
                aim -= distance
            }
            Direction.down -> {
                aim += distance
            }
            Direction.forward -> {
                horizontalPosition += distance
                verticalPosition += (aim * distance)
            }
        }
    }

    return verticalPosition * horizontalPosition
}