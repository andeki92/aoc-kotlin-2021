package day13

import helpers.parseInput


fun main() {
    val input = parseInput("/day13.txt")
    val (inputInstructions, inputState) = input.filterNot { it.isEmpty() }.partition { it.startsWith("fold") }

//    println(inputInstructions)
//    println(inputState)

    val initialState = inputState.map { line -> line.split(",").let { it[0].toInt() to it[1].toInt() } }
    val instructions = inputInstructions.map { instruction ->
        instruction.substringAfter("fold along ").split("=").let { it[0] to it[1].toInt() }
    }

    val part1 = part1(initialState, instructions)
    println("Solution to part1: $part1")

    val part2 = part2(initialState, instructions)
    println("Solution to part2:")
    part2.onEach { println(it.joinToString("")) }
}

private fun part1(initialState: List<Pair<Int, Int>>, instructions: List<Pair<String, Int>>): Int =
    instructions.first().let { (foldDirection, coordinate) ->
        when (foldDirection) {
            "x" -> foldVertical(initialState, coordinate)
            "y" -> foldHorizontal(initialState, coordinate)
            else -> error("No instruction for: $foldDirection")
        }
    }.toCoordinateMap().flatten().count { it == '#' }

private fun part2(initialState: List<Pair<Int, Int>>, instructions: List<Pair<String, Int>>): List<List<Char>> =
    instructions.fold(initialState) { state, instruction ->
        when (instruction.first) {
            "x" -> foldVertical(state, instruction.second)
            "y" -> foldHorizontal(state, instruction.second)
            else -> error("No instruction for: ${instruction.first}")
        }
    }.toCoordinateMap()

private fun foldHorizontal(state: List<Pair<Int, Int>>, y: Int): List<Pair<Int, Int>> =
    state.partition { it.second < y }.let { (original, folded) ->
        original.plus(folded.map { it.first to y - (it.second - y) })
    }

private fun foldVertical(state: List<Pair<Int, Int>>, x: Int): List<Pair<Int, Int>> =
    state.partition { it.first < x }.let { (original, folded) ->
        original.plus(folded.map { x - (it.first - x) to it.second })
    }

private fun List<Pair<Int, Int>>.toCoordinateMap(): List<List<Char>> =
    (0..this.maxOf { it.second }).map { y ->
        (0..this.maxOf { it.first }).map { x -> if (this.contains(x to y)) '#' else ' ' }
    }
