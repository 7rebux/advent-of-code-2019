package de.nosswald.aoc.days

import de.nosswald.aoc.Day

object Day04 : Day<Int>(4, "Secure Container") {
    override fun partOne(input: List<String>) = parse(input).count(::valid)

    override fun partTwo(input: List<String>) = parse(input).count { valid(it, true) }

    private fun parse(input: List<String>): IntRange = input[0]
        .split("-")
        .map(String::toInt)
        .let { IntRange(it[0], it[1]) }

    private fun valid(password: Int, partTwo: Boolean = false): Boolean {
        val pairs = password.toString().zipWithNext()
        val adjacent = pairs.filter { it.first == it.second }
        val nonIncreasing = pairs.all { it.first <= it.second }

        return nonIncreasing && if (partTwo) adjacent.groupBy { it }.any { it.value.size == 1 } else adjacent.isNotEmpty()
    }

    override val partOneTestExamples: Map<List<String>, Int> = mapOf(
        listOf("111111-111111") to 1,
        listOf("223450-223450") to 0,
        listOf("123789-123789") to 0,
    )

    override val partTwoTestExamples: Map<List<String>, Int> = mapOf(
        listOf("112233-112233") to 1,
        listOf("123444-123444") to 0,
        listOf("111122-111122") to 1,
    )
}
