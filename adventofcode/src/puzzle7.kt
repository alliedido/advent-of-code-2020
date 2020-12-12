import java.io.File

fun main() {
    val inputPath = "/Users/allison/IdeaProjects/advent-of-code-2020/adventofcode/input/input7.txt"
    val inputFile = File(inputPath)
    var input = inputFile.readLines()
    var parsedInput = convertInput(input)
    println(countBagContents(parsedInput, "shiny gold"))
    println(countBagsInsideTarget(parsedInput, "shiny gold"))

}

fun convertInput(input: List<String>) : Map<String, Map<String, Int>> {
    var result = mutableMapOf<String, Map<String, Int>>()
    for (line in input) {
        var keyHalf = line.split("contain")[0]
        var resultHalf = line.split("contain")[1].removeSuffix(".")
        var keyColor = keyHalf.removeSuffix(" bags ")
        var resultMap = mutableMapOf<String, Int>()
        for (bagCounts in resultHalf.split(",")) {
            if (bagCounts != " no other bags") {
                var splitString = bagCounts.split(" ")
                var count = splitString[1].toInt()
                var color = "${splitString[2]} ${splitString[3]}"
                resultMap[color] = count
            }
        }
        result[keyColor] = resultMap
    }
    return result
}

fun countBagContents(inputMap: Map<String, Map<String, Int>>, target: String) : Int {
    var count = 0
    for (key in inputMap.keys) {
        if (countInnerBagContents(inputMap, key, target) >= 1) {
            count++
        }
    }
    return count
}

fun countInnerBagContents(oMap: Map<String, Map<String, Int>>, startingPoint: String, target: String) : Int {
    var currentMap = oMap[startingPoint]
    var count = 0
    if (!currentMap.isNullOrEmpty()) {
        if (currentMap.containsKey(target)) {
            return 1
        } else {
            for (innerKey in currentMap.keys) {
                count += countInnerBagContents(oMap, innerKey, target)
            }
        }
    }
    return count
}

fun countBagsInsideTarget(oMap: Map<String, Map<String, Int>>, startingPoint: String) : Int {
    var runningCount = 0
    var currentMap = oMap[startingPoint]
    if (!currentMap.isNullOrEmpty()) {
        for (innerKey in currentMap.keys) {
            val innerBags = countBagsInsideTarget(oMap, innerKey)
            val bagsForKey = currentMap.getOrDefault(innerKey, 0)
            val totalBags = bagsForKey + (innerBags * bagsForKey)
            runningCount += totalBags
        }
    }
    return runningCount
}