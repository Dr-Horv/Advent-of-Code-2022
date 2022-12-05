package generator

import java.io.File
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.file.Files
import java.nio.file.Paths

fun formatDay(day: Int) = "%02d".format(day)

val dayTemplate: (Int) -> String = { day: Int ->
    """package solutions.day${formatDay(day)}

import solutions.Solver

class Day$day : Solver {

    override fun solve(input: List<String>, partTwo: Boolean): String {
        return ""
    }
}
"""
}

val dayTestTemplate: (Int) -> String = { day: Int ->
    """package solutions.day${formatDay(day)}

import solutions.DayTest
import solutions.Solver
import solutions.TestCase
import solutions.createTestCase

class Day${day}Test : DayTest() {
    override fun getPart1TestCases(): List<TestCase> = createTestCase(
        "", ""
    )

    override fun getPart2TestCases(): List<TestCase> = createTestCase(
        "", ""
    )

    override fun getSolver(): Solver = Day$day()

}"""
}

fun fetchInput(day: Int): String {
    val cookie = System.getenv("COOKIE")
    if (cookie.isNullOrBlank()) {
        throw Error("Missing cookie")
    }
    val client = HttpClient.newBuilder().build()
    val request = HttpRequest.newBuilder()
        .uri(URI.create("https://adventofcode.com/2022/day/$day/input"))
        .header(
            "cookie",
            cookie
        )
        .build()

    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
    if (response.statusCode() != 200) {
        throw Error("""Got error when fetching input: ${response.statusCode()} ${response.body()}""")
    }

    return response.body()
}

fun createDay(day: Int) {
    val input = fetchInput(day)

    val dayPath = "src/main/kotlin/solutions/day${formatDay(day)}"
    Files.createDirectories(Paths.get(dayPath))
    File("$dayPath/Day$day.kt").writeText(dayTemplate(day))
    File("$dayPath/input.txt").writeText(input)

    val dayTestPath = "src/test/kotlin/solutions/day${formatDay(day)}"
    Files.createDirectories(Paths.get(dayTestPath))
    File("$dayTestPath/Day${day}Test.kt").writeText(dayTestTemplate(day))
}

