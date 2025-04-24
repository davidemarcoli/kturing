package turingmachine

/**
 * Runner for Turing machine execution with detailed tracking.
 */
class TuringMachineRunner(private val tm: TuringMachine) {
    private var currentState: State = tm.startState
    private val tape = Tape(tm.blankSymbol)
    private var steps = 0

    /**
     * Observer interface for tracking the execution of a Turing machine.
     */
    interface StepObserver {
        fun onStep(state: State, tape: Tape, stepCount: Int)
    }

    private var observer: StepObserver? = null

    /**
     * Sets an observer to receive step-by-step execution updates.
     */
    fun setObserver(observer: StepObserver?) {
        this.observer = observer
    }

    fun initialize(input: String) {
        currentState = tm.startState
        tape.initialize(input)
        steps = 0

        // Notify observer of initial state
        observer?.onStep(currentState, tape, steps)
    }

    /**
     * Performs a single step of execution.
     *
     * @return true if the machine can continue, false if it has halted
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

        // Notify observer of the step
        observer?.onStep(currentState, tape, steps)

        return !tm.isHaltingState(currentState)
    }

    /**
     * Runs the Turing machine until it halts or reaches the maximum steps.
     *
     * @param maxSteps Maximum number of steps to run (default 10000)
     * @return A TMResult containing the execution result
     */
    fun run(maxSteps: Int = 10000): TMResult {
        while (step() && steps < maxSteps) {
            // Step until halted or max steps reached
        }

        val accepted = tm.isAcceptingState(currentState)

        return TMResult(
            accepted = accepted,
            finalState = currentState,
            steps = steps,
            tapeContent = tape.toString(),
            headPosition = tape.getPosition()
        )
    }

    // Getter methods
    fun getCurrentState(): State = currentState
    fun getTape(): Tape = tape
    fun getStepCount(): Int = steps
}