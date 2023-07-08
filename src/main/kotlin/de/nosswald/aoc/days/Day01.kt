package de.nosswald.aoc.days

import de.nosswald.aoc.Day

object Day01 : Day<Int>(1, "The Tyranny of the Rocket Equation") {
    override fun partOne(input: List<String>) = input
        .map(String::toInt)
        .sumOf(::calcFuel)

    override fun partTwo(input: List<String>) = input
        .map(String::toInt)
        .sumOf { mass ->
            generateSequence(mass, ::calcFuel)
                .takeWhile { it > 0 }
                .drop(1)
                .sum()
        }

    private fun calcFuel(mass: Int) = mass / 3 - 2

    override val partOneTestExamples: Map<List<String>, Int> = mapOf(
        listOf("12") to 2,
        listOf("14") to 2,
        listOf("1969") to 654,
        listOf("100756") to 33583,
    )

    override val partTwoTestExamples: Map<List<String>, Int> = mapOf(
        listOf("14") to 2,
        listOf("1969") to 966,
        listOf("100756") to 50346,
    )
}
