package day05

import helpers.parseInput

private typealias Point = Pair<Int, Int>

private data class Vector(
    val x1: Int, val y1: Int,
    val x2: Int, val y2: Int,
) {
    fun isHorizontal(): Boolean = this.x1 != this.x2
    fun isVertical(): Boolean = this.y1 != this.y2
    fun isDiagonal(): Boolean = this.isHorizontal() && this.isVertical()

    fun generatePoints(): List<Point> = when {
        isDiagonal() -> {
            // TODO: This could probably have been a bit simpler
            val multiplier = if (y1 > y2) -1 else 1

            if (x1 > x2) {
                (x1 downTo x2).mapIndexed { i, x -> x to y1 + (i * multiplier) }
            } else {
                (x1..x2).mapIndexed { i, x -> x to y1 + (i * multiplier) }
            }
        }
        isHorizontal() -> (x1.coerceAtMost(x2)..x1.coerceAtLeast(x2)).map { it to y1 /* or y2 */ }
        isVertical() -> (y1.coerceAtMost(y2)..y1.coerceAtLeast(y2)).map { x1 to it /* or x2 */ }
        else -> error(this)
    }
}

fun main() {
    val input = parseInput("/day05.txt")
    val vectors: List<Vector> = input.map { row ->
        row.replace(" ", "")
            .split("->") // split vectors on the ->
            .flatMap { it.split(",") }
            .chunked(2)
            .map { Pair(it[0].toInt(), it[1].toInt()) } // convert to points
            .let {
                Vector(
                    it[0].first, it[0].second,
                    it[1].first, it[1].second
                )
            } // convert to vector
    }

    val part1 = part1(vectors)
    println("Solution to part1: $part1")

    val part2 = part2(vectors)
    println("Solution to part2: $part2")
}

private fun part1(vectors: List<Vector>): Int =
    vectors.filterNot(Vector::isDiagonal)
        .flatMap(Vector::generatePoints)
        .fold(mutableMapOf(), ::accOperation)
        .count { it.value }

private fun part2(vectors: List<Vector>): Int =
    vectors.flatMap(Vector::generatePoints)
        .fold(mutableMapOf(), ::accOperation)
        .count { it.value }

private fun accOperation(acc: MutableMap<Point, Boolean>, pair: Point): MutableMap<Point, Boolean> {
    acc[pair] = acc.containsKey(pair) // set to 'true' if it already exists
    return acc // return map
}
