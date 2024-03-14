package com.crossmint.tonilopezmr.domain

/**
 * 2D Megaverse map.
 */
data class Megaverse(val astralMap: Array<Array<AstralObject>>) {

  val astralObjects: List<AstralObject>
    get() = astralMap.flatten().filter { it !is EmptySpace }

  fun getNeighbours(astralObject: AstralObject): List<AstralObject> {
    val adjacentPositions = mutableListOf<AstralObject>()

    val directions = listOf(
      Position(-1, -1), // Up-left
      Position(-1, 0), // Up
      Position(-1, 1), // Up-right
      Position(0, -1), // Left
      Position(0, 1), // Right
      Position(1, -1), // Down-left
      Position(1, 0), // Down
      Position(1, 1) // Down-right
    )

    for (dir in directions) {
      val newX = astralObject.x + dir.x
      val newY = astralObject.y + dir.y

      if (newX in astralMap.indices && newY in astralMap[newX].indices) {
        adjacentPositions.add(astralMap[newX][newY])
      }
    }

    return adjacentPositions
  }
}
