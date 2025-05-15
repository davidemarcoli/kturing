package turingmachine

/**
 * Runner for Turing machine execution with functional step tracking.
 */
class TuringMachineRunner(private val tm: TuringMachine) {
    private var currentState: State = tm.startState
    private val tape = Tape(tm.blankSymbol)
    private var steps = 0L

    /**
     * Data class representing the state of a Turing machine at a given step.
     */
    data class StepInfo(
        val state: State,
        val tape: Tape,
        val stepCount: Long,
        val headPosition: Int
    )

    private var stepCallback: ((StepInfo) -> Unit)? = null

    /**
     * Initializes the Turing machine with the given input string.
     */
    fun initialize(input: String): TuringMachineRunner {
        currentState = tm.startState
        tape.initialize(input)
        steps = 0

        // Notify of initial state
        notifyStep()

        return this
    }

    /**
     * Sets a callback function to be called at each step.
     */
    fun onStep(callback: (StepInfo) -> Unit): TuringMachineRunner {
        stepCallback = callback
        // Notify with current state since we may have already initialized
        notifyStep()
        return this
    }

    private fun notifyStep() {
        stepCallback?.invoke(
            StepInfo(
                state = currentState,
                tape = tape,
                stepCount = steps,
                headPosition = tape.getPosition()
            )
        )
    }

    /**
     * Performs a single step of execution.
     */
    fun step(): Boolean {
        val symbol = tape.read()
        val transition = tm.getTransition(currentState, symbol)

        if (transition == null) {
            return false
        } else {
            tape.write(transition.writeSymbol)
            when (transition.direction) {
                Direction.LEFT -> tape.moveLeft()
                Direction.RIGHT -> tape.moveRight()
                Direction.NONE -> { /* Do nothing */ }
            }
            currentState = transition.nextState
        }

        steps++

        // Notify of step completion
        notifyStep()

        return true
    }

    /**
     * Runs the Turing machine until it halts or reaches the maximum steps.
     * Returns a result object with execution details.
     */
    fun run(maxSteps: Long = Long.MAX_VALUE): TMResult {
        while (step() && steps < maxSteps) {
            // Step until halted or max steps reached
        }

        val accepted = tm.isAcceptingState(currentState)

        return TMResult(
            accepted = accepted,
            finalState = currentState,
            steps = steps,
            result = tape.getContent(),
            tapeContent = tape.toString(),
            headPosition = tape.getPosition()
        )
    }

    // Getter methods
    fun getCurrentState(): State = currentState
    fun getTape(): Tape = tape
    fun getStepCount(): Long = steps
}