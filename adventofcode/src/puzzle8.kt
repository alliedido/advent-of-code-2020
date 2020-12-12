import java.io.File
import java.util.*

fun main() {
    val inputPath = "/Users/allison/IdeaProjects/advent-of-code-2020/adventofcode/input/input8.txt"
    val inputFile = File(inputPath)
    var input = inputFile.readLines()
//    println(findInfiniteLoop(processInput(input)))
    println(findTerminatingSolution(processInput(input)))
}

fun processInput(input: List<String>) : MutableList<Pair<String, Int>> {
    var result = mutableListOf<Pair<String, Int>>()
    for (line in input) {
        val instruction = line.split(' ')[0]
        val value = line.split(' ')[1].toInt()
        result.add(Pair<String, Int>(instruction, value))
    }
    return result
}

fun swapInstruction(instruction: Pair<String, Int>) : Pair<String, Int> {
    return when (instruction.first) {
        "jmp" -> {
            Pair<String, Int>("nop", instruction.second)
        }
        "nop" -> {
            Pair<String, Int>("jmp", instruction.second)
        }
        else -> {
            instruction
        }
    }
}

fun findTerminatingSolution(input: MutableList<Pair<String, Int>>) : Int {
    // this is a shit, brute force solution.
    // I imagine there is a better way to do this with a stack or graph
    for (index in input.indices) {
        var instruction = input[index]
        input[index] = swapInstruction(instruction)

        val result = findInfiniteLoop(input)
        if (result != -1) {
            return result
        }

        // change it back
        instruction = input[index]
        input[index] =  swapInstruction(instruction)
    }
    return -2
}

fun findInfiniteLoop(input: List<Pair<String, Int>>) : Int {
    var accumulator = 0
    val seenInstructions = mutableSetOf<Int>()
    val instructionStack = Stack<Int>()
    var index = 0
    while (!seenInstructions.contains(index)) {
        seenInstructions.add(index)
        instructionStack.push(index)
        when(input[index].first) {
            "acc" -> {
                accumulator += input[index].second
                index++
            }
            "jmp" -> {
                index += input[index].second
            }
            "nop" -> {
                index++
            }
        }
        if (index == input.size) {
            // winning condition
            return accumulator
        }
    }

    // we are in an infinite loop
    return -1
}
