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
//    val rejectState: State = State.REJECT,
    val blankSymbol: Char = '_'
) {
    init {
        require(blankSymbol in tapeAlphabet) { "Blank-Symbol muss im Band-Alphabet enthalten sein" }
        require(inputAlphabet.all { it in tapeAlphabet }) { "Eingabe-Alphabet muss Teilmenge des Band-Alphabets sein" }
        require(startState in states) { "Startzustand muss in den Zuständen enthalten sein" }
        require(acceptState in states) { "Akzeptierender Zustand muss in den Zuständen enthalten sein" }
        require(transitions.all { it.currentState in states }) { "Alle Zustände der Übergänge müssen in den Zuständen enthalten sein" }
        require(transitions.all { it.nextState in states }) { "Alle Folgezustände der Übergänge müssen in den Zuständen enthalten sein" }
        require(startState in states) { "Startzustand muss in den Zuständen enthalten sein" }
        require(acceptState in states) { "Akzeptierender Zustand muss in den Zuständen enthalten sein" }
//        require(rejectState in states) { "Ablehnender Zustand muss in den Zuständen enthalten sein" }
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
     * Returns if the given state is a halting state.
     *
     * @param state The state to check.
     * @return True if the state is a halting state, false otherwise.
     */
    fun isHaltingState(state: State): Boolean {
        return state == acceptState /*|| state == rejectState*/
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
