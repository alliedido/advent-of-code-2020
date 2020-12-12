import java.io.File
import java.lang.Long.max
import java.lang.Long.min

fun main() {
    val inputPath = "/Users/allison/IdeaProjects/advent-of-code-2020/adventofcode/input/input9.txt"
    val inputFile = File(inputPath)
    var input = inputFile.readLines().toMutableList().map {item -> item.toLong()}
    var target = findInvalidSum(input)
    println(findContiguosNums(input, target))
}

fun findInvalidSum(input: List<Long>) : Long {
    val preambleLength = 25
    for (index in preambleLength until input.size) {
        val numbersInQuestion = input.slice(index-preambleLength until index)
        if (!isValidSum(input[index], numbersInQuestion.toHashSet())) {
            return input[index]
        }
    }
    return -1
}

fun isValidSum(number: Long, numbersInQuestion: Set<Long>) : Boolean {
    for (possibility in numbersInQuestion.iterator()) {
        if (numbersInQuestion.contains(number - possibility)) {
            return true
        }
    }
    return false
}

fun findContiguosNums(input: List<Long>, target: Long) : Long {
    for (i in input.indices) {
        var runningSum = input[i]
        var smallest = input[i]
        var largest = input[1]
        for (j in (i + 1) until input.size) {
            runningSum += input[j]
            smallest = min(smallest, input[i])
            smallest = min(smallest, input[j])
            largest = max(largest, input[i])
            largest = max(largest, input[j])
            if (runningSum == target) {
                return smallest + largest
            }
        }
    }
    return -1
}