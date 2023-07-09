package de.nosswald.aoc.days

import de.nosswald.aoc.Day

object Day02 : Day<Int>(2, "1202 Program Alarm") {
    /**
     * For part one I changed the memory addresses at 1 & 2 manually in the input file
     * since those are being overwritten in part two anyway
     */
    override fun partOne(input: List<String>) = Program(parse(input))
        .apply(Program::execute)
        .memory[0]

    override fun partTwo(input: List<String>): Int {
        for (noun in 0..99) {
            for (verb in 0..99) {
                val memory = parse(input)

                memory[1] = noun
                memory[2] = verb

                val program = Program(memory)

                program.execute()

                if (program.memory[0] == 19690720)
                    return "${noun}${verb}".toInt()
            }
        }

        error("No matching verb + noun")
    }

    private fun parse(input: List<String>) = input
        .first()
        .split(",")
        .map(String::toInt)
        .toMutableList()

    private class Program(val memory: MutableList<Int>) {
        fun execute() = generateSequence(0) { it + 4 }
            .takeWhile(::instruction)
            .last()

        private fun instruction(pointer: Int): Boolean {
            if (memory[pointer] == 99) return false

            val (code, left, right, out) = memory.subList(pointer, pointer + 4)

            when (code) {
                1 -> memory[out] = memory[left] + memory[right]
                2 -> memory[out] = memory[left] * memory[right]
                else -> error("Invalid op code: $code")
            }

            return true
        }
    }

    override val partOneTestExamples: Map<List<String>, Int> = mapOf(
        listOf("1,9,10,3,2,3,11,0,99,30,40,50") to 3500,
        listOf("1,0,0,0,99") to 2,
        listOf("1,1,1,4,99,5,6,0,99") to 30,
    )

    // Can not really test part two
    override val partTwoTestExamples: Map<List<String>, Int> = emptyMap()
}
