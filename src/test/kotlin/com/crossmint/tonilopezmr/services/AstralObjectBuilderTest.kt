package com.crossmint.tonilopezmr.services

import com.crossmint.tonilopezmr.domain.Color
import com.crossmint.tonilopezmr.domain.ComETH
import com.crossmint.tonilopezmr.domain.Direction
import com.crossmint.tonilopezmr.domain.EmptySpace
import com.crossmint.tonilopezmr.domain.POLYanet
import com.crossmint.tonilopezmr.domain.SOLoon
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AstralObjectBuilderTest {

  @Test
  fun `throws UnidentifiedAstralObject error when astral object is not recognized`() {
    val exception: UnidentifiedAstralObject = assertThrows(UnidentifiedAstralObject::class.java) {
      AstralObjectBuilder.build("CLASSIFIED_OBJECT", 0, 0)
    }

    assertEquals("Unidentified Astral Object: CLASSIFIED_OBJECT", exception.message)
  }

  @Test
  fun `build POLYANET Astral Object`() {
    val polYanet = AstralObjectBuilder.build("POLYANET", 0, 0)

    assertEquals(POLYanet(0, 0), polYanet)
  }

  @Test
  fun `build a SPACE Astral Object`() {
    val emptySpace = AstralObjectBuilder.build("SPACE", 0, 0)

    assertEquals(EmptySpace(0, 0), emptySpace)
  }

  @ParameterizedTest(name = "Build {0} Astral Object")
  @ValueSource(strings = ["UP_COMETH", "LEFT_COMETH", "RIGHT_COMETH", "DOWN_COMETH"])
  fun `build a COMETH Astral Object`(cometh: String) {
    val expectedDirection = Direction.fromString(cometh.substringBefore("_"))

    val comETH = AstralObjectBuilder.build(cometh, 0, 0)

    assertEquals(ComETH(expectedDirection, 0, 0), comETH)
  }

  @ParameterizedTest(name = "Build {0} Astral Object")
  @ValueSource(strings = ["BLUE_SOLOON", "PURPLE_SOLOON", "WHITE_SOLOON", "RED_SOLOON"])
  fun `build a SOLoon Astral Object`(soloon: String) {
    val expectedColor = Color.fromString(soloon.substringBefore("_"))

    val soloon = AstralObjectBuilder.build(soloon, 0, 0)

    assertEquals(SOLoon(expectedColor, 0, 0), soloon)
  }
}
