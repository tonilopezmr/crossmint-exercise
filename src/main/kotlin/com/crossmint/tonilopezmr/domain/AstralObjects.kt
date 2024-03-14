package com.crossmint.tonilopezmr.domain

sealed class AstralObject(val x: Int, val y: Int)
data class EmptySpace(val row: Int, val column: Int) : AstralObject(row, column)
data class POLYanet(val row: Int, val column: Int) : AstralObject(row, column)
data class SOLoon(val color: Color, val row: Int, val column: Int) : AstralObject(row, column)
data class ComETH(val direction: Direction, val row: Int, val column: Int) : AstralObject(row, column)

enum class Direction {
  UP, DOWN, RIGHT, LEFT;

  override fun toString(): String = super.toString().lowercase()

  companion object {
    fun fromString(direction: String): Direction = try {
      Direction.valueOf(direction.uppercase())
    } catch (e: IllegalArgumentException) {
      throw NoValidDirection(direction)
    }
  }
}

enum class Color {
  BLUE, RED, PURPLE, WHITE;

  override fun toString(): String = super.toString().lowercase()

  companion object {
    fun fromString(color: String): Color = try {
      Color.valueOf(color.uppercase())
    } catch (e: IllegalArgumentException) {
      throw NoValidColor(color)
    }
  }
}

data class Position(val x: Int, val y: Int)
