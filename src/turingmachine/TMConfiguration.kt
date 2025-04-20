package turingmachine

/**
 * Represents the configuration of a Turing machine at a given moment.
 *
 * @property state The current state of the Turing machine.
 * @property tape The tape of the Turing machine.
 */
data class TMConfiguration(
    val state: State,
    val tape: Tape
) {
    override fun toString(): String {
        return "($state, ${tape})"
    }
}
