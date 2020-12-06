import java.io.File
import java.lang.Integer.max

fun main() {
    val inputPath = "/Users/allison/IdeaProjects/advent-of-code-2020/adventofcode/input/input5.txt"
    val inputFile = File(inputPath)
    var input = inputFile.readLines()
    println(calculateHighestSeatId(input))  // part 1
    println(calculateSeat(input))  // part 2
}

fun calculateHighestSeatId(input: List<String>) : Int {
    var currentHighest = 0
    input.forEach { entry -> currentHighest = max(currentHighest, calculateSeatId(entry)) }
    return currentHighest
}

fun calculateSeat(input: List<String>) : Int {
    val allSeats = input.map { calculateSeatId(it) }.sorted()
    for (i in allSeats.indices) {
        if (allSeats[i + 1] - allSeats[i] > 1) {
            return allSeats[i] + 1
        }
    }
    return -1
}

fun calculateSeatId(entry: String) : Int {
    val rowEntry = entry.subSequence(0, 7).toString()
    val seatEntry = entry.subSequence(7, entry.length).toString()

    val row = calculateRange('F', 'B', rowEntry, Pair<Int, Int>(0, 127))
    val seat = calculateRange('L', 'R', seatEntry, Pair<Int, Int>(0, 7))
    return (row * 8) + seat
}

fun calculateRange(lowerChar: Char, upperChar: Char, rowEntry: String, range: Pair<Int, Int>) : Int {
    if (rowEntry.length == 1) {
        when (rowEntry.first()) {
            lowerChar -> return range.first
            upperChar -> return range.second
        }
    } else {
        val middle = ((range.second - range.first) / 2).toInt()
        when (rowEntry.first()) {
            lowerChar -> return calculateRange(lowerChar, upperChar, rowEntry.substring(1),
                                               Pair<Int, Int>(range.first, range.first + middle))
            upperChar -> return calculateRange(lowerChar, upperChar, rowEntry.substring(1),
                                               Pair<Int, Int>(range.second - middle, range.second))
        }
    }
    return -1
}