package de.nosswald.aoc.days

import de.nosswald.aoc.Day
import kotlin.math.abs

object Day03 : Day<Int>(3, "Crossed Wires") {
    override fun partOne(input: List<String>) = path(input[0])
        .intersect(path(input[1]).toSet())
        .minOf(Position::distanceToOrigin)

    override fun partTwo(input: List<String>): Int {
        val wire1 = path(input[0])
        val wire2 = path(input[1])

        // every intersection after the first is guaranteed to have more steps
        val intersection = wire1.intersect(wire2.toSet()).first()

        return wire1.indexOf(intersection) + wire2.indexOf(intersection) + 2
    }

    private fun path(input: String): List<Position> {
        val path = mutableListOf(Position(0, 0))

        input.split(",").forEach {
            val direction = it.first()
            val steps = it.substring(1).toInt()

            repeat(steps) {
                path.add(walk(path.last(), direction))
            }
        }

        return path.drop(1)
    }

    private fun walk(start: Position, direction: Char) = when (direction) {
        'R' -> start.right()
        'L' -> start.left()
        'D' -> start.down()
        'U' -> start.up()
        else -> error("Invalid direction: $direction")
    }

    private data class Position(val x: Int, val y: Int) {
        fun right() = Position(this.x + 1, this.y)
        fun left() = Position(this.x - 1, this.y)
        fun down() = Position(this.x, this.y + 1)
        fun up() = Position(this.x, this.y - 1)
        fun distanceToOrigin() = abs(x) + abs(y)
    }

    override val partOneTestExamples: Map<List<String>, Int> = mapOf(
        listOf("R8,U5,L5,D3", "U7,R6,D4,L4") to 6,
        listOf("R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83") to 159,
        listOf("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7") to 135,
    )

    override val partTwoTestExamples: Map<List<String>, Int> = mapOf(
        listOf("R8,U5,L5,D3", "U7,R6,D4,L4") to 30,
        listOf("R75,D30,R83,U83,L12,D49,R71,U7,L72", "U62,R66,U55,R34,D71,R55,D58,R83") to 610,
        listOf("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51", "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7") to 410,
    )
}
