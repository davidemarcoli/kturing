package turingmachine

class TuringMachineRunner(private val tm: TuringMachine) {
    private var currentState: State = tm.startState
    private val tape = Tape(tm.blankSymbol)
    private var steps = 0
    private val history = mutableListOf<TMConfiguration>()

    /**
     * Initializes the Turing machine with the given input string.
     *
     * @param input The input string to initialize the tape.
     */
    fun initialize(input: String) {
        currentState = tm.startState
        tape.initialize(input)
        steps = 0
        history.clear()
        history.add(TMConfiguration(currentState, tape))
    }

    /**
     * Performs a single step of the Turing machine.
     *
     * @return True if the Turing machine is still running, false if it has halted.
     */
    fun step(): Boolean {
        if (tm.isHaltingState(currentState)) return false

        val symbol = tape.read()
        val transition = tm.getTransition(currentState, symbol)

        if (transition == null) {
            return false
        } else {
            tape.write(transition.writeSymbol)
            when (transition.direction) {
                Direction.LEFT -> tape.moveLeft()
                Direction.RIGHT -> tape.moveRight()
            }
            currentState = transition.nextState
        }

        steps++
        history.add(TMConfiguration(currentState, tape))
        return !tm.isHaltingState(currentState)
    }

    /**
     * Runs the Turing machine until it halts or the maximum number of steps is reached.
     *
     * @param maxSteps The maximum number of steps to run.
     * @param verbose If true, prints the state and tape after each step.
     * @return True if the Turing machine accepted the input, false otherwise.
     */
    fun run(maxSteps: Int = 10000, verbose: Boolean = false): Boolean {
        while (step() && steps < maxSteps) {
            if (verbose) {
                println("Schritt $steps: ${history.last()}")
            }
        }

        if (steps >= maxSteps) {
            println("Abbruch nach $maxSteps Schritten - mögliche Endlosschleife")
            return false
        }

        return tm.isAcceptingState(currentState)
    }

    /**
     * Returns the result of the Turing machine run.
     *
     * @return A pair containing a boolean indicating if the input was accepted and the final tape content.
     */
    fun getResult(): Pair<Boolean, String> {
        val accepted = tm.isAcceptingState(currentState)
        val finalTape = history.last().tape.toString()

        return Pair(accepted, finalTape)
    }

    /**
     * Returns the history of the Turing machine run.
     *
     * @return A list of {@link TMConfiguration} objects representing the history of the Turing machine.
     */
    fun getHistory(): List<TMConfiguration> {
        return history.toList()
    }

    /**
     * Returns the amount of steps taken by the Turing machine.
     *
     * @return The number of steps taken.
     */
    fun getStepCount(): Int {
        return steps
    }
}
