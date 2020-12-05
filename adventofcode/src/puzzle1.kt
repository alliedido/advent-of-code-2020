import java.io.File

const val target : Int = 2020

fun main() {
    val inputPath = "/Users/allison/IdeaProjects/advent-of-code-2020/adventofcode/input/input1.txt"
    val inputFile = File(inputPath)
    var input = inputFile.readLines().toMutableList().map {item -> item.toInt()}
    println(findSumsPart1(input))
    println(findSumsPart2(input))
}

fun findSumsPart1(inputList: List<Int>) : Int {
    val inputSet = inputList.toSet()
    for (num in inputList) {
        val difference = target - num
        if (inputSet.contains(difference)) {
            return num * difference
        }
    }
    return -1
}

fun findSumsPart2(inputList: List<Int>) : Int {
    val inputSet = inputList.toSet()
    for (num in inputList) {
        for (num2 in inputList) {
            val difference = target - num - num2
            if (inputSet.contains(difference)) {
                return num * num2 * difference
            }
        }
    }
    return -1
}