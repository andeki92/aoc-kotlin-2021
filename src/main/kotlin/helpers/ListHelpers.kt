package helpers


fun <T> List<List<T>>.neighbours(x: Int, y: Int, diagonals: Boolean = false): List<T> =
    neighbourPositions(x, y, diagonals).mapNotNull { (x, y) -> this.getOrNull(y)?.getOrNull(x) }

fun <T> List<List<T>>.neighboursIndexed(x: Int, y: Int, diagonals: Boolean = false): List<Pair<T, Pair<Int, Int>>> =
    neighbourPositions(x, y, diagonals).mapNotNull { (x, y) ->
        this.getOrNull(y)?.getOrNull(x)?.let { it to (x to y) }
    }

/**
 * Find adjacent x,y positions (all unique)
 */
private fun neighbourPositionsWithoutDiagonals(x: Int, y: Int): Set<Pair<Int, Int>> =
    setOfNotNull(
        x to (y - 1),
        (x - 1) to y, (x + 1) to y,
        x to (y + 1),
    )

private fun neighbourPositionsWithDiagonals(x: Int, y: Int): Set<Pair<Int, Int>> =
    setOfNotNull(
        (x - 1) to (y - 1), x to (y - 1), (x + 1) to (y - 1),
        (x - 1) to y, /*               */ (x + 1) to y,
        (x - 1) to (y + 1), x to (y + 1), (x + 1) to (y + 1)
    )

private fun neighbourPositions(x: Int, y: Int, diagonals: Boolean = false): Set<Pair<Int, Int>> =
    if (diagonals) neighbourPositionsWithDiagonals(x, y) else neighbourPositionsWithoutDiagonals(x, y)
