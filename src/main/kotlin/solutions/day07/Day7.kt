package solutions.day07

import solutions.Solver

enum class FileType {
    DIRECTORY,
    FILE
}

data class Node(
    val name: String,
    val type: FileType,
    val children: MutableList<Node>,
    val size: Int,
    var explored: Boolean,
    val parent: Node?
)

class Day7 : Solver {

    private fun isCommand(s: String): Boolean = s.startsWith("\$")

    override fun solve(input: List<String>, partTwo: Boolean): String {

        val terminalOutput = input.toMutableList()
        val root = Node(
            name = "/",
            type = FileType.DIRECTORY,
            children = mutableListOf(),
            size = 0,
            explored = false,
            parent = null
        )

        var curr: Node? = null
        while (terminalOutput.isNotEmpty()) {
            val line = terminalOutput.removeFirst();
            println("output: " + line)
            if (line.startsWith("\$ cd")) {
                val name = line.removePrefix("\$ cd").trim()
                curr = when (name) {
                    ".." -> curr!!.parent
                    "/" -> root
                    else -> curr!!.children.find { it.name == name }
                }
                println("location " + curr!!.name)
            }

            if (line.startsWith("\$ ls") && !curr!!.explored) {
                while (terminalOutput.isNotEmpty() && !isCommand(terminalOutput[0])) {
                    val parts = terminalOutput.removeFirst().split(" ").map(String::trim)
                    val name = parts[1]
                    if (parts[0] == "dir") {
                        val n = Node(
                            name = name,
                            type = FileType.DIRECTORY,
                            children = mutableListOf(),
                            size = 0,
                            explored = false,
                            parent = curr
                        )
                        curr!!.children.add(n)

                    } else {
                        val size = parts[0].toInt()
                        val n = Node(
                            name = name,
                            type = FileType.FILE,
                            children = mutableListOf(),
                            size = size,
                            explored = false,
                            parent = curr
                        )
                        curr!!.children.add(n)
                    }
                }
                println(curr.name + " children: " + curr.children.joinToString(",") { it.name })
                curr.explored = true
            }
        }

        if(!partTwo) {
            val matches = mutableListOf<Node>()
            findMatches(root, matches) { it < 100_000 }
            println(matches.map { it.name })
            return matches.sumOf { calculateSize(it) }.toString()
        } else {
            val AVAILABLE = 70000000
            val NEEDED = 30000000
            val UNUSED = AVAILABLE - calculateSize(root)
            val LEFT = NEEDED - UNUSED

            val matches = mutableListOf<Node>()
            findMatches(root, matches) { it >= LEFT }
            println(matches.map { it.name })
            val smallest = matches.minBy { calculateSize(it) }
            return calculateSize(smallest).toString()
        }
    }

    private fun findMatches(n: Node, matches: MutableList<Node>, sizeCondition: (Int) -> Boolean) {
        if(n.type == FileType.DIRECTORY && sizeCondition(calculateSize(n))) {
            matches.add(n)
        }
        n.children.forEach { findMatches(it, matches, sizeCondition) }
    }

    private fun calculateSize(n: Node): Int = if (n.type === FileType.FILE) {
        n.size
    } else {
        n.children.sumOf { calculateSize(it) }
    }
}
