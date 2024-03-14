package com.crossmint.tonilopezmr.domain

sealed class AstralObject(val x: Int, val y: Int)
data class EmptySpace(val row: Int, val column: Int) : AstralObject(row, column)
data class POLYanet(val row: Int, val column: Int) : AstralObject(row, column)
data class SOLoon(val color: Color, val row: Int, val column: Int) : AstralObject(row, column)
data class ComETH(val direction: Direction, val row: Int, val column: Int) : AstralObject(row, column)

enum class Direction {
  up, down, right, left;

  companion object {
    fun fromString(direction: String): Direction = try {
      Direction.valueOf(direction.lowercase())
    } catch (e: IllegalArgumentException) {
      throw NoValidDirection(direction)
    }
  }
}

enum class Color {
  blue, red, purple, white;

  companion object {
    fun fromString(color: String): Color = try {
      Color.valueOf(color.lowercase())
    } catch (e: IllegalArgumentException) {
      throw NoValidColor(color)
    }
  }
}

data class Position(val x: Int, val y: Int)
