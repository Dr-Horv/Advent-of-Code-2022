package solutions

fun createTestCase(string: String, expected: String): List<TestCase> {
    return listOf(
        TestCase(
            string.split("\n").dropLast(1),
            expected
        )
    )
}