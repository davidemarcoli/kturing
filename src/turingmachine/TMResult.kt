package turingmachine

/**
 * Represents the result of a Turing machine computation.
 *
 * @property accepted Whether the input was accepted
 * @property finalState The final state of the machine
 * @property steps Number of steps performed
 * @property tapeContent Final content of the tape
 * @property headPosition Final position of the tape head
 */
data class TMResult(
    val accepted: Boolean,
    val finalState: State,
    val steps: Int,
    val result: String,
    val tapeContent: String,
    val headPosition: Int
)