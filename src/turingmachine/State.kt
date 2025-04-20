package turingmachine

/**
 * Represents a state in a Turing machine.
 *
 * @property id The unique identifier of the state.
 * @property name The name of the state (default is "q{id}").
 */
data class State(val id: Int, val name: String = "q$id") {
    companion object {
        val START = State(1, "START")
        val ACCEPT = State(2, "ACCEPT")
//        val REJECT = State(3, "REJECT")
    }

    override fun toString(): String = name
}