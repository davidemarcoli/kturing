package turingmachine

/**
 * Represents a Turing machine.
 *
 * @property states The set of states in the Turing machine.
 * @property inputAlphabet The input alphabet of the Turing machine.
 * @property tapeAlphabet The tape alphabet of the Turing machine.
 * @property transitions The set of transitions in the Turing machine.
 * @property startState The start state of the Turing machine.
 * @property acceptState The accept state of the Turing machine.
 * @property blankSymbol The symbol representing a blank space on the tape.
 */
class TuringMachine(
    val states: Set<State>,
    val inputAlphabet: Set<Char>,
    val tapeAlphabet: Set<Char>,
    val transitions: Set<Transition>,
    val startState: State,
    val acceptState: State,
    val blankSymbol: Char = '_'
) {
    init {
        require(blankSymbol in tapeAlphabet) { "Blank symbol must be included in the tape alphabet" }
        require(inputAlphabet.all { it in tapeAlphabet }) { "Input alphabet must be a subset of the tape alphabet" }
        require(startState in states) { "Start state must be included in the set of states" }
        require(acceptState in states) { "Accept state must be included in the set of states" }
        require(transitions.all { it.currentState in states }) { "All transition states must be included in the set of states" }
        require(transitions.all { it.nextState in states }) { "All next states of transitions must be included in the set of states" }
        require(startState in states) { "Start state must be included in the set of states" }
        require(acceptState in states) { "Accept state must be included in the set of states" }
    }

    private val transitionTable: Map<Pair<State, Char>, Transition> by lazy {
        transitions.associateBy { Pair(it.currentState, it.currentSymbol) }
    }

    /**
     * Get the transition for a given state and symbol.
     *
     * @param state The current state of the Turing machine.
     * @param symbol The symbol currently under the tape head.
     * @return The transition for the given state and symbol, or null if no transition exists.
     */
    fun getTransition(state: State, symbol: Char): Transition? {
        return transitionTable[Pair(state, symbol)]
    }

    /**
     * Returns if the given state is an accepting state.
     *
     * @param state The state to check.
     * @return True if the state is an accepting state, false otherwise.
     */
    fun isAcceptingState(state: State): Boolean {
        return state == acceptState
    }
}
