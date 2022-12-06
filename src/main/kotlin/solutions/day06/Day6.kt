package solutions.day06

import solutions.Solver

class Day6 : Solver {

    override fun solve(input: List<String>, partTwo: Boolean): String {

        val charsToLookFor = if(!partTwo) { 4 } else { 14 }

        val inputChars = input[0].toList()
        for (i in (charsToLookFor-1) until inputChars.size) {
            val chars = mutableSetOf<Char>()
            for(ci in i downTo (i-(charsToLookFor-1))) {
                chars.add(inputChars[ci])
            }
            if(chars.size == charsToLookFor) {
                return (i+1).toString()
            }
        }

        return "FAIL"
    }
}
