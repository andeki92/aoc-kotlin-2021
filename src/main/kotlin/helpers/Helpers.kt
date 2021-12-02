package helpers

import kotlin.io.path.Path
import kotlin.io.path.readLines

fun parseInput(filename: String) = Path("src", "main", "resources", filename).readLines()