package turingmachine

/**
 * Represents the tape of a Turing machine.
 *
 * @property blankSymbol The symbol representing a blank space on the tape.
 */
class Tape(private val blankSymbol: Char) {
    private val leftTape = mutableListOf<Char>()
    private val rightTape = mutableListOf<Char>()

    /**
     * Initializes the tape with the given input string.
     *
     * @param input The input string to initialize the tape.
     */
    fun initialize(input: String) {
        leftTape.clear()
        rightTape.clear()
        if (input.isEmpty()) {
            rightTape.add(blankSymbol)
        } else {
            rightTape.addAll(input.toList())
        }
    }

    /**
     * Returns the current symbol under the tape head.
     *
     * @return The symbol under the tape head.
     */
    fun read(): Char {
        if (rightTape.isEmpty()) {
            rightTape.add(blankSymbol)
        }
        return rightTape.first()
    }

    /**
     * Writes a symbol at the current tape head position.
     *
     * @param symbol The symbol to write.
     */
    fun write(symbol: Char) {
        if (rightTape.isEmpty()) {
            rightTape.add(symbol)
        } else {
            rightTape[0] = symbol
        }
    }

    /**
     * Moves the tape head to the left.
     */
    fun moveLeft() {
        if (leftTape.isEmpty()) {
            leftTape.add(blankSymbol)
        }
        rightTape.add(0, leftTape.removeAt(leftTape.lastIndex))
    }

    /**
     * Moves the tape head to the right.
     */
    fun moveRight() {
        if (rightTape.size <= 1) {
            rightTape.add(blankSymbol)
        }
        leftTape.add(rightTape.removeAt(0))
    }

    override fun toString(): String {
        val sb = StringBuilder()

        for (i in leftTape.lastIndex downTo 0) {
            sb.append(leftTape[i])
        }

        sb.append("[${if (rightTape.isNotEmpty()) rightTape[0] else blankSymbol}]")

        for (i in 1..<rightTape.size) {
            sb.append(rightTape[i])
        }

        return sb.toString()
    }
}