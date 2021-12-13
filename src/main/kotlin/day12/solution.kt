package day12

import helpers.parseInput


fun main() {
    val input = parseInput("/day12.txt")
    val initialState = input.map { line -> line.split("-").let { it[0] to it[1] } }

    val graph = UndirectedGraph()

    initialState.forEach { (source, destination) ->
        graph.addEdge(source, destination)
    }

//    println("Initial state: $initialState")

    val part1 = part1(graph)
    println("Solution to part1: $part1")

    val part2 = part2(graph)
    println("Solution to part2: $part2")
}

private fun part1(graph: UndirectedGraph): Int = graph.paths("start", "end").size

private fun part2(graph: UndirectedGraph): Int = graph.paths("start", "end", 2).size

class UndirectedGraph {
    private val adjacencyMap: HashMap<String, HashSet<String>> = HashMap()

    /**
     * Add the edges both from and to the source and destination vertices respectively
     */
    fun addEdge(sourceVertex: String, destinationVertex: String) {
        adjacencyMap
            .computeIfAbsent(sourceVertex) { HashSet() }
            .add(destinationVertex)

        adjacencyMap
            .computeIfAbsent(destinationVertex) { HashSet() }
            .add(sourceVertex)
    }

    override fun toString(): String = StringBuffer().apply {
        append("undirected graph:\n")

        for (key in adjacencyMap.keys) {
            append("\t$key -> ")
            append(adjacencyMap[key]?.joinToString(", ", "[", "]\n"))
        }
    }.toString()

    fun paths(startNode: String, endNode: String, maxLowercaseVisits: Int = 1): Set<List<String>> =
        (0..Int.MAX_VALUE).fold(setOf(listOf(startNode))) { acc: Set<List<String>>, _: Int ->
            acc.flatMap { path ->
                if (path.last() != endNode) {
                    val sourceNode = path.last()
                    adjacencyMap[sourceNode]?.let { destinationNodes ->
                        destinationNodes
                            .filter { it != startNode }
                            .filter {
                                if (it.isUppercase()) return@filter true
                                if (it !in path) return@filter true
                                if (path.maxLowercaseOccurrences() < maxLowercaseVisits) return@filter true
                                false
                            }
                            .map { destinationNode -> path + destinationNode }
                    } ?: emptyList()
                } else listOf(path)
            }.toSet()
                .also { paths ->
                    if (paths.all { path -> path.last() == endNode }) return@paths paths
                }
        }

    private fun List<String>.maxLowercaseOccurrences(): Int =
        this.filter(String::isLowercase).groupingBy { it }.eachCount().maxOf { it.value }
}

private fun String.isUppercase(): Boolean = this.all(Char::isUpperCase)
private fun String.isLowercase(): Boolean = !this.isUppercase() // Not really true but works as expected here

