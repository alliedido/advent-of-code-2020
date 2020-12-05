import java.io.File

fun main() {
    val inputPath = "/Users/allison/IdeaProjects/advent-of-code-2020/adventofcode/input/input3.txt"
    val inputFile = File(inputPath)
    val input = inputFile.readLines().map { it -> it.toList() }
    partOne(input)
    partTwo(input)
}

fun partOne(map : List<List<Char>>) {
    println(countTrees(map, 3, 1))
}

fun partTwo(map: List<List<Char>>) {
    val pairs = listOf<Pair<Int, Int>>(Pair(1, 1),
                                       Pair(3, 1),
                                       Pair(5, 1),
                                       Pair(7, 1),
                                       Pair(1, 2))

    for (pair in pairs) {
        println(countTrees(map, pair.first, pair.second))
    }
}

fun countTrees(map : List<List<Char>>, xStep : Int, yStep : Int) : Int {
    var x = 0
    var y = 0
    var counter = 0
    while(y < map.size) {
        if (map[y][x] == '#') {
            counter++
        }
        x = (x + xStep) % map[y].size;
        y += yStep
    }
    return counter
}