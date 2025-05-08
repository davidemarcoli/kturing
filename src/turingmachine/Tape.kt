package turingmachine

/**
 * Represents the tape of a Turing machine.
 *
 * @property blankSymbol The symbol representing a blank space on the tape.
 */
class Tape(private val blankSymbol: Char) {
    private val leftTape = mutableListOf<Char>()
    private val rightTape = mutableListOf<Char>()
    private var position = 0  // Track absolute position

    fun initialize(input: String) {
        leftTape.clear()
        rightTape.clear()
        position = 0

        if (input.isEmpty()) {
            rightTape.add(blankSymbol)
        } else {
            rightTape.addAll(input.toList())
        }
    }

    fun read(): Char {
        ensureReadable()
        return rightTape.first()
    }

    fun write(symbol: Char) {
        ensureReadable()
        rightTape[0] = symbol
    }

    fun moveLeft() {
        if (leftTape.isEmpty()) {
            leftTape.add(blankSymbol)
        }
        rightTape.add(0, leftTape.removeAt(leftTape.lastIndex))
        position--
    }

    fun moveRight() {
        ensureReadable()
        if (rightTape.size <= 1) {
            rightTape.add(blankSymbol)
        }
        leftTape.add(rightTape.removeAt(0))
        position++
    }

    private fun ensureReadable() {
        if (rightTape.isEmpty()) {
            rightTape.add(blankSymbol)
        }
    }

    fun getPosition(): Int = position

    /**
     * Returns a view of the tape with specified context around the head.
     */
    fun getView(context: Int): String {
        val sb = StringBuilder()

        // Add left context
        val leftContext = leftTape.takeLast(context)
        repeat(context - leftContext.size) { sb.append(blankSymbol) }
        leftContext.forEach { sb.append(it) }

        // Add head position with brackets
        sb.append("[${read()}]")

        // Add right context
        val rightContext = rightTape.drop(1).take(context)
        rightContext.forEach { sb.append(it) }
        repeat(context - rightContext.size) { sb.append(blankSymbol) }

        return sb.toString()
    }

    fun getContent(): String {
        return (leftTape.reversed().joinToString("") + read() + rightTape.drop(1).joinToString("")).trim(blankSymbol)
    }

    override fun toString(): String {
        return getView(50)  // Default view size
    }
}