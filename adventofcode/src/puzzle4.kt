import java.io.File

class Passport(private val rawEntry : List<Pair<String, String>>) {
    private val requiredFields = setOf<String>("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid")

    private fun checkValidFields(fields : Set<String>) : Boolean {
        var missingRequiredFields = requiredFields subtract fields
        if ("cid" in missingRequiredFields) {
            missingRequiredFields = missingRequiredFields.minus("cid")
        }
        return missingRequiredFields.isEmpty()
    }

    fun isValid() : Boolean {
        val keys = rawEntry.map{ it.first }.toSet()
        return checkValidFields(keys)
    }

    fun isValid2() : Boolean {
        var validEntries = mutableSetOf<String>()
        for (entry in rawEntry) {
            var isValidEntry = false
            when (entry.first) {
                "byr" -> isValidEntry = (entry.second.length == 4 && entry.second.toInt() in 1920..2002)
                "iyr" -> isValidEntry = (entry.second.length == 4 && entry.second.toInt() in 2010..2020)
                "eyr" -> isValidEntry = (entry.second.length == 4 && entry.second.toInt() in 2020..2030)
                "hgt" -> {
                    if (entry.second.takeLast(2) in setOf<String>("cm", "in")) {
                        var suffix = entry.second.takeLast(2)
                        var prefix = entry.second.removeSuffix(suffix).toIntOrNull()
                        when (suffix) {
                            "cm" -> isValidEntry = prefix in 150..193
                            "in" -> isValidEntry = prefix in 59..76
                        }
                    }
                }
                "hcl" -> isValidEntry = entry.second.matches(Regex("#[0-9a-f]{6}"))
                "ecl" -> isValidEntry = entry.second in setOf<String>("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
                "pid" -> isValidEntry = entry.second.matches(Regex("[0-9]{9}"))
            }
            if (isValidEntry) {
                validEntries.add(entry.first)
            }
        }
        return checkValidFields(validEntries)
    }
}


fun main() {
    val inputPath = "/Users/allison/IdeaProjects/advent-of-code-2020/adventofcode/input/input4.txt"
    val inputFile = File(inputPath)
    var input = inputFile.readLines()
    var parsedInput = parseInput(input)
    println(countValidInput(parsedInput))
}

fun countValidInput(input : List<Passport>) : Int {
    var counter = 0
    for (passport in input) {
        if (passport.isValid2()) {
            counter++
        }
    }
    return counter
}

fun parseInput(input : List<String>) : List<Passport> {
    var parsedInput = mutableListOf<Passport>()
    var currentEntry = mutableListOf<Pair<String, String>>()
    for (line in input) {
        if (line.isNotEmpty()) {
            for (entry in line.split(' ')) {
                var splitEntry = entry.split(':')
                currentEntry.add(Pair(splitEntry[0], splitEntry[1]))
            }
        } else {
            parsedInput.add(Passport(currentEntry))
            currentEntry = mutableListOf<Pair<String, String>>()
        }
    }
    return parsedInput
}