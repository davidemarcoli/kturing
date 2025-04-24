package universaltm

import turingmachine.*

/**
 * Decoder for binary-encoded Turing Machines according to the lecture format.
 */
class TuringMachineDecoder {
    /**
     * Decodes a binary string representing a Turing Machine.
     *
     * @param binaryEncoding The binary encoding of the TM
     * @return A TuringMachine object created from the encoding
     */
    fun decode(binaryEncoding: String): TuringMachine {
        val transitions = parseTransitions(binaryEncoding)

        val states = collectStates(transitions)
        val tapeSymbols = collectSymbols(transitions)

        // Input alphabet is all tape symbols except blank
        val inputAlphabet = tapeSymbols.filter { it != '_' }.toSet()

        return TuringMachine(
            states = states,
            inputAlphabet = inputAlphabet,
            tapeAlphabet = tapeSymbols,
            transitions = transitions.toSet(),
            startState = State.START,
            acceptState = State.ACCEPT,
            blankSymbol = '_'
        )
    }

    private fun collectStates(transitions: List<Transition>): Set<State> {
        val states = mutableSetOf<State>()

        // Always include start and accept states
        states.add(State.START)
        states.add(State.ACCEPT)

        // Add states from transitions
        for (transition in transitions) {
            states.add(transition.currentState)
            states.add(transition.nextState)
        }

        return states
    }

    private fun collectSymbols(transitions: List<Transition>): Set<Char> {
        val symbols = mutableSetOf<Char>()

        // Always include blank
        symbols.add('_')

        // Add symbols from transitions
        for (transition in transitions) {
            symbols.add(transition.currentSymbol)
            symbols.add(transition.writeSymbol)
        }

        return symbols
    }

    private fun parseTransitions(binaryEncoding: String): List<Transition> {
        val transitions = mutableListOf<Transition>()
        val transitionEncodings = binaryEncoding.split("11")

        for (encoding in transitionEncodings) {
            if (encoding.isEmpty()) continue

            try {
                transitions.add(parseTransition(encoding))
            } catch (e: Exception) {
                System.err.println("Error parsing transition: $encoding")
                e.printStackTrace()
            }
        }

        return transitions
    }

    private fun parseTransition(encoding: String): Transition {
        val parts = encoding.split("1")
        val parameters = parts.filter { it.isNotEmpty() }.map { it.length }

        // Extract parameters: 0^i 1 0^j 1 0^k 1 0^l 1 0^m
        val i = parameters[0] // Current state id
        val j = parameters[1] // Current symbol id
        val k = parameters[2] // Next state id
        val l = parameters[3] // Write symbol id
        val m = parameters[4] // Direction id

        return Transition(
            currentState = mapToState(i),
            currentSymbol = mapToSymbol(j),
            nextState = mapToState(k),
            writeSymbol = mapToSymbol(l),
            direction = if (m == 1) Direction.LEFT else Direction.RIGHT
        )
    }

    private fun mapToState(stateCode: Int): State {
        return when (stateCode) {
            1 -> State.START  // q1 is the start state
            2 -> State.ACCEPT // q2 is the accept state
            else -> State(stateCode) // q3, q4, etc.
        }
    }

    private fun mapToSymbol(symbolCode: Int): Char {
        return when (symbolCode) {
            1 -> '0'  // X1 is symbol 0
            2 -> '1'  // X2 is symbol 1
            3 -> '_'  // X3 is the blank symbol
            else -> (symbolCode - 4 + 'A'.code).toChar() // X4, X5, etc.
        }
    }
}