package helpers


fun <T> List<List<T>>.neighbours(x: Int, y: Int): List<T> =
    neighbourPositions(x, y).mapNotNull { (x, y) -> this.getOrNull(y)?.getOrNull(x) }

fun <T> List<List<T>>.neighboursIndexed(x: Int, y: Int): List<Pair<T, Pair<Int, Int>>> =
    neighbourPositions(x, y).mapNotNull { (x, y) ->
        this.getOrNull(y)?.getOrNull(x)?.let { it to (x to y) }
    }

/**
 * Find adjacent x,y positions (all unique)
 */
private fun neighbourPositions(x: Int, y: Int): Set<Pair<Int, Int>> =
    setOfNotNull(
        x to (y - 1),
        (x - 1) to y, (x + 1) to y,
        x to (y + 1),
    )
