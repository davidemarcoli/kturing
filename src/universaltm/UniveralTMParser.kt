package universaltm

/**
 * Input parser for Universal Turing Machine.
 */
class UniversalTMParser {
    /**
     * Parses input containing both TM encoding and input string.
     *
     * @param input String in format "tmEncoding111inputString"
     * @return Pair of TM encoding and input string
     */
    fun parse(input: String): Pair<String, String> {
        val separator = "111"
        val parts = input.split(separator, limit = 2)

        if (parts.size < 2) {
            throw IllegalArgumentException("Invalid input format: missing '$separator' separator")
        }

        return Pair(parts[0], parts[1])
    }

    /**
     * Converts a decimal Gödel number to its binary representation.
     */
    fun decimalToBinary(decimal: Long): String {
        return decimal.toString(2)
    }
}
