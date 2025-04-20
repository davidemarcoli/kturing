package godel

import turingmachine.Direction
import turingmachine.Transition
import turingmachine.TuringMachine

/**
 * Encodes a Turing machine's transitions into a Gödel number.
 */
class GodelEncoder(private val tm: TuringMachine) {
    private val symbolMap: Map<Char, Int> by lazy {
        tm.tapeAlphabet.toList().withIndex().associate { (index, symbol) ->
            symbol to (index + 1)
        }
    }

    private fun getSymbolId(symbol: Char): Int {
        return symbolMap[symbol] ?: throw IllegalArgumentException("Symbol '$symbol' ist nicht im Alphabet")
    }

    /**
     * Encodes a single transition into a Gödel number.
     *
     * @param transition The transition to encode.
     * @return The encoded transition as a string.
     */
    fun encodeTransition(transition: Transition): String {
        val i = transition.currentState.id
        val j = getSymbolId(transition.currentSymbol)
        val k = transition.nextState.id
        val l = getSymbolId(transition.writeSymbol)
        val m = if (transition.direction == Direction.LEFT) 1 else 2

        // Format: 0^i 10^j 10^k 10^l 10^m
        return "0".repeat(i) + "1" +
                "0".repeat(j) + "1" +
                "0".repeat(k) + "1" +
                "0".repeat(l) + "1" +
                "0".repeat(m)
    }

    /**
     * Encodes all transitions of the Turing machine into a Gödel number.
     *
     * @return The encoded Gödel number as a string.
     */
    fun encode(): String {
        val sortedTransitions = tm.transitions.sortedWith(compareBy(
            { it.currentState.id },
            { getSymbolId(it.currentSymbol) }
        ))

        return sortedTransitions.joinToString("11") { encodeTransition(it) }
    }

    /**
     * Computes the Gödel number for the Turing machine.
     *
     * @return The Gödel number as a string.
     */
    fun computeGodelNumber(): String = "1${encode()}"

    /**
     * Get the symbol mapping for the Turing machine.
     *
     * @return A map of symbols to their corresponding IDs.
     */
    fun getSymbolMapping(): Map<Char, Int> = symbolMap
}
