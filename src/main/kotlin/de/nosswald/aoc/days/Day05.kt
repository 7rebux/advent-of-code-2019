package de.nosswald.aoc.days

import de.nosswald.aoc.Day

object Day05 : Day<Int>(5, "Sunny with a Chance of Asteroids") {
    override fun partOne(input: List<String>) = Program(parse(input), 1)
        .apply(Program::execute)
        .output.last()

    override fun partTwo(input: List<String>) = Program(parse(input), 5)
        .apply(Program::execute)
        .output.last()

    private fun parse(input: List<String>) = input
        .first()
        .split(",")
        .map(String::toInt)
        .toMutableList()

    private class Program(val memory: MutableList<Int>, val systemId: Int) {
        var output = mutableListOf<Int>()

        fun execute() {
            var pointer = 0

            while (pointer != -1)
                pointer = instruction(pointer)
        }

        private fun instruction(pointer: Int): Int {
            var next = pointer
            val instruction = memory[next++]
            val code = instruction % 100
            val mode1 = instruction / 100 % 10 == 0
            val mode2 = instruction / 1000 % 10 == 0

            if (code == 99) return -1

            fun paramByMode(mode: Boolean, address: Int) =
                if (mode) memory[memory[address]] else memory[address]

            when (code) {
                // Addition
                1 -> (paramByMode(mode1, next++) + paramByMode(mode2, next++)).also { memory[memory[next++]] = it }
                // Multiplication
                2 -> (paramByMode(mode1, next++) * paramByMode(mode2, next++)).also { memory[memory[next++]] = it }
                // Set
                3 -> memory[memory[next++]] = systemId
                // Output
                4 -> output.add(paramByMode(mode1, next++))
                // Jump If True
                5 -> next = if (paramByMode(mode1, next) != 0) paramByMode(mode2, next + 1) else pointer + 3
                // Jump If False
                6 -> next = if (paramByMode(mode1, next) == 0) paramByMode(mode2, next + 1) else pointer + 3
                // Less Than
                7 -> (paramByMode(mode1, next++) < paramByMode(mode2, next++)).also { memory[memory[next++]] = if (it) 1 else 0 }
                // Equals
                8 -> (paramByMode(mode1, next++) == paramByMode(mode2, next++)).also { memory[memory[next++]] = if (it) 1 else 0 }
                else -> error("Invalid op code: $code")
            }

            return next
        }
    }

    override val partOneTestExamples: Map<List<String>, Int> = mapOf(
        listOf("3,0,4,0,99") to 1,
    )

    override val partTwoTestExamples: Map<List<String>, Int> = mapOf(
        listOf("3,9,8,9,10,9,4,9,99,-1,8") to 0,
        listOf("3,9,7,9,10,9,4,9,99,-1,8") to 1,
        listOf("3,3,1108,-1,8,3,4,3,99") to 0,
        listOf("3,3,1107,-1,8,3,4,3,99") to 1,
        listOf("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9") to 1,
        listOf("3,3,1105,-1,9,1101,0,0,12,4,12,99,1") to 1,
        listOf("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99") to 999,
    )
}
