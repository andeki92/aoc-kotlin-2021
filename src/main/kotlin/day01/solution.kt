package day01

import helpers.parseInput

fun main() {
    val input = parseInput("/day01.txt")
    val numbers = input.map { it.toInt() }

    // test data
//    val numbers = listOf(199,200,208,210,200,207,240,269,260,263)
    
    val increments = part1(numbers)
    println("Solution to part1: $increments")

    val windowedIncrements = part2(numbers)
    println("Solution to part2: $windowedIncrements")
}

private fun part1(input: List<Int>): Int = input.zipWithNext { a: Int, b: Int -> if (b > a) 1 else 0 }.sum()

private fun part2(input: List<Int>): Int = input.windowed(size = 3, step = 1).map { it.sum() }.let { part1(it) }