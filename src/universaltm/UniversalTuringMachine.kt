package universaltm

import turingmachine.TMResult
import turingmachine.TuringMachineRunner


/**
 * Main class for Universal Turing Machine with fluent API.
 */
class UniversalTuringMachine {
    private val decoder = TuringMachineDecoder()
    private val parser = UniversalTMParser()

    /**
     * Runs a TM specified by its binary encoding on the given input.
     * Returns a builder that lets you attach step observers.
     */
    fun prepare(tmEncoding: String, input: String): TMExecutor {
        val turingMachine = decoder.decode(tmEncoding)
        val runner = TuringMachineRunner(turingMachine)
        runner.initialize(input)

        return TMExecutor(runner)
    }

    /**
     * Runs a TM specified by its Gödel number on the given input.
     * Returns a builder that lets you attach step observers.
     */
    fun prepareWithGodelNumber(godelNumber: Long, input: String): TMExecutor {
        val binaryEncoding = parser.decimalToBinary(godelNumber)
        return prepare(binaryEncoding, input)
    }

    /**
     * Runs a TM specified by a combined input string (encoding + input).
     * Returns a builder that lets you attach step observers.
     */
    fun prepareCombined(combinedInput: String): TMExecutor {
        val (tmEncoding, input) = parser.parse(combinedInput)
        return prepare(tmEncoding, input)
    }

    /**
     * Builder class for configuring and executing a TM.
     */
    class TMExecutor(private val runner: TuringMachineRunner) {
        /**
         * Attaches a step observer and returns the executor for chaining.
         */
        fun onStep(callback: (TuringMachineRunner.StepInfo) -> Unit): TMExecutor {
            runner.onStep(callback)
            return this
        }

        /**
         * Executes the TM and returns the result.
         */
        fun execute(maxSteps: Int = 10000): TMResult {
            return runner.run(maxSteps)
        }
    }
}