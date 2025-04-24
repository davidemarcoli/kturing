package universaltm

import turingmachine.TMResult
import turingmachine.TuringMachineRunner

/**
 * Universal Turing Machine that can run any encoded TM.
 */
class UniversalTuringMachine {
    private val decoder = TuringMachineDecoder()
    private val parser = UniversalTMParser()

    /**
     * Runs a TM specified by its binary encoding on the given input.
     *
     * @param tmEncoding Binary encoding of the TM
     * @param input Input string for the TM
     * @param observer Optional observer for step-by-step execution
     * @return The result of the computation
     */
    fun run(tmEncoding: String, input: String, observer: TuringMachineRunner.StepObserver? = null): TMResult {
        val turingMachine = decoder.decode(tmEncoding)
        val runner = TuringMachineRunner(turingMachine)

        // Set observer if provided
        runner.setObserver(observer)

        // Initialize and run
        runner.initialize(input)
        return runner.run()
    }

    /**
     * Runs a TM specified by its Gödel number on the given input.
     *
     * @param godelNumber Decimal Gödel number of the TM
     * @param input Input string for the TM
     * @param observer Optional observer for step-by-step execution
     * @return The result of the computation
     */
    fun runWithGodelNumber(godelNumber: Long, input: String, observer: TuringMachineRunner.StepObserver? = null): TMResult {
        val binaryEncoding = parser.decimalToBinary(godelNumber)
        return run(binaryEncoding, input, observer)
    }

    /**
     * Runs a TM specified by a combined input string (encoding + input).
     *
     * @param combinedInput String in format "tmEncoding111inputString"
     * @param observer Optional observer for step-by-step execution
     * @return The result of the computation
     */
    fun runCombined(combinedInput: String, observer: TuringMachineRunner.StepObserver? = null): TMResult {
        val (tmEncoding, input) = parser.parse(combinedInput)
        return run(tmEncoding, input, observer)
    }
}