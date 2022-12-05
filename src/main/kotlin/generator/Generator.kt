package generator


fun main(args: Array<String>) {
    if (args.isEmpty() || args[0].toIntOrNull() == null) {
        throw Error("Missing day as first argument: [${args.joinToString(",")}]")
    }
    createDay(args[0].toInt())
}