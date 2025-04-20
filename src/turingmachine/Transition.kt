package turingmachine

/**
 * Represents a transition in a Turing machine.
 *
 * @property currentState The current state of the Turing machine.
 * @property currentSymbol The symbol currently under the tape head.
 * @property nextState The next state of the Turing machine after the transition.
 * @property writeSymbol The symbol to write on the tape.
 * @property direction The direction to move the tape head (LEFT or RIGHT).
 */
data class Transition(
    val currentState: State,
    val currentSymbol: Char,
    val nextState: State,
    val writeSymbol: Char,
    val direction: Direction
) {
    override fun toString(): String =
        "δ($currentState, $currentSymbol) = ($nextState, $writeSymbol, ${direction.name})"
}