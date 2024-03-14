package com.crossmint.tonilopezmr.domain

import com.crossmint.tonilopezmr.domain.Color.PURPLE
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MegaverseTest {

  @Test
  fun `Neighbours of 0,0 in a Megaverse of 3x3`() {
    val megaverse = Megaverse(
      arrayOf(
        arrayOf(EmptySpace(0, 0), EmptySpace(0, 1), POLYanet(0, 2)),
        arrayOf(EmptySpace(1, 0), POLYanet(1, 1), EmptySpace(1, 2)),
        arrayOf(SOLoon(PURPLE, 2, 0), EmptySpace(2, 1), EmptySpace(2, 2))
      )
    )

    val expected = listOf(EmptySpace(0, 1), EmptySpace(1, 0), POLYanet(1, 1))

    assertEquals(expected, megaverse.getNeighbours(megaverse.astralMap[0][0]))
  }

  @Test
  fun `Neighbours of 1,1 (center) in a Megaverse of 3x3`() {
    val megaverse = Megaverse(
      arrayOf(
        arrayOf(EmptySpace(0, 0), EmptySpace(0, 1), POLYanet(0, 2)),
        arrayOf(EmptySpace(1, 0), POLYanet(1, 1), EmptySpace(1, 2)),
        arrayOf(SOLoon(PURPLE, 2, 0), EmptySpace(2, 1), EmptySpace(2, 2))
      )
    )

    val expected = megaverse.astralMap.flatten().filter { it.x != 1 || it.y != 1 }

    assertEquals(expected, megaverse.getNeighbours(megaverse.astralMap[1][1]))
  }

  @Test
  fun `Neighbours of 2,0 in a Megaverse of 3x5`() {
    val megaverse = Megaverse(
      arrayOf(
        arrayOf(EmptySpace(0, 0), EmptySpace(0, 1), POLYanet(0, 2), EmptySpace(0, 3), EmptySpace(0, 4)),
        arrayOf(EmptySpace(1, 0), POLYanet(1, 1), EmptySpace(1, 2), EmptySpace(1, 3), EmptySpace(1, 4)),
        arrayOf(SOLoon(PURPLE, 2, 0), EmptySpace(2, 1), EmptySpace(2, 2), EmptySpace(2, 3), EmptySpace(2, 4))
      )
    )

    val expected = listOf(EmptySpace(1, 0), POLYanet(1, 1), EmptySpace(2, 1))

    assertEquals(expected, megaverse.getNeighbours(megaverse.astralMap[2][0]))
  }
}
