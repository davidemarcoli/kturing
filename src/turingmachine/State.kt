package turingmachine

/**
 * Represents a state in a Turing machine.
 *
 * @property id The unique identifier of the state.
 * @property name The name of the state (default is "q{id}").
 */
data class State(val id: Int, val name: String = "q$id") {
    override fun toString(): String = name
}