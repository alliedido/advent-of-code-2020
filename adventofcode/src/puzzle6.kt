import java.io.File

fun main() {
    val inputPath = "/Users/allison/IdeaProjects/advent-of-code-2020/adventofcode/input/input6.txt"
    val inputFile = File(inputPath)
    var input = inputFile.readLines().toMutableList()
    println(countGroups(compileGroupsPart2(input)))
}

fun compileGroups(input: List<String>) : List<Set<Char>> {
    var result = mutableListOf<Set<Char>>()

    var currentEntry = mutableSetOf<Char>()
    for (line in input) {
        if (line.isNotEmpty()) {
            currentEntry.addAll(line.toHashSet())
        } else {
            result.add(currentEntry)
            currentEntry = mutableSetOf<Char>()
        }
    }
    result.add(currentEntry)
    return result
}

fun compileGroupsPart2(input: List<String>) : List<Set<Char>> {
    var result = mutableListOf<Set<Char>>()

    var currentEntry = mutableListOf<Set<Char>>()
    for (line in input) {
        if (line.isNotEmpty()) {
            currentEntry.add(line.toHashSet())
        } else {
            result.add(getResult(currentEntry))
            currentEntry = mutableListOf<Set<Char>>()
        }
    }
    result.add(getResult(currentEntry))
    return result
}

fun getResult(currentEntry: MutableList<Set<Char>>) : Set<Char> {
    var firstEntry = currentEntry.removeAt(0)
    var currentResult = mutableSetOf<Char>()
    firstEntry.forEach { letter ->
        val letterCount = currentEntry.count { it.contains(letter) }
        if (letterCount == currentEntry.size) {
            currentResult.add(letter)
        }
    }
    return currentResult
}

fun countGroups(groups: List<Set<Char>>) : Int {
    var runningCount = 0
    groups.forEach { group -> runningCount += group.size }
    return runningCount
}