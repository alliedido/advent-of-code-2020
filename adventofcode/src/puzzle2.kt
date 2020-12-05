import java.io.File

class Entry(private val bottomThreshold: Int,
            private val topThreshold: Int,
            private val letter: Char,
            private val password: String) {
    fun print() {
        println("$bottomThreshold-$topThreshold $letter: $password")
    }

    fun isValid() : Boolean {
        val count = password.filter { it == letter }.count()
        return (count in bottomThreshold..topThreshold)
    }

    fun isValidPart2() : Boolean {
        val first = password.toCharArray()[bottomThreshold - 1] == letter
        val second = password.toCharArray()[topThreshold - 1] == letter
        return first.xor(second)
    }

}

fun main() {
    val inputPath = "/Users/allison/IdeaProjects/advent-of-code-2020/adventofcode/input/input2.txt"
    val inputFile = File(inputPath)

    var input = inputFile.readLines().toMutableList().map{item -> parseItem(item)}
    println(countViolations(input))
}

fun parseItem(input: String) : Entry {
    val splitInput = input.split(' ')
    val bottomThreshold = splitInput[0].split('-')[0].toInt()
    val topThreshold = splitInput[0].split('-')[1].toInt()
    val letter = splitInput[1].first().toChar()
    val password = splitInput[2]

    return Entry(bottomThreshold, topThreshold, letter, password)
}

fun countViolations(input: List<Entry>) : Int {
    var counter = 0
    input.forEach {
        when {
            it.isValid() -> {
                counter++
            }
        }
    }
    return counter
}
