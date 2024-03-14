package com.crossmint.tonilopezmr.services

import com.crossmint.tonilopezmr.domain.Color
import com.crossmint.tonilopezmr.domain.ComETH
import com.crossmint.tonilopezmr.domain.Direction
import com.crossmint.tonilopezmr.domain.EmptySpace
import com.crossmint.tonilopezmr.domain.NoValidColor
import com.crossmint.tonilopezmr.domain.NoValidDirection
import com.crossmint.tonilopezmr.domain.POLYanet
import com.crossmint.tonilopezmr.domain.SOLoon
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class AstralObjectProcessorTest {

  @Test
  fun `build POLYANET Astral Object`() {
    val polYanet = AstralObjectBuilder.build("POLYANET", 0, 0)

    assertEquals(POLYanet(0, 0), polYanet)
  }

  @Test
  fun `Process a null POLYANET Astral Object when is not POLYANET`() {
    val processor = POLYanetProcessor()

    val emptySpace = processor.process("DOWN_COMETH", 0, 0)

    assertNull(emptySpace)
  }

  @Test
  fun `Process a POLYANET Astral Object`() {
    val emptySpaceProcessor = POLYanetProcessor()

    val polYanet = emptySpaceProcessor.process("POLYANET", 0, 0)

    assertEquals(POLYanet(0, 0), polYanet)
  }

  @Test
  fun `Process a SPACE Astral Object`() {
    val emptySpaceProcessor = EmptySpaceProcessor()

    val emptySpace = emptySpaceProcessor.process("SPACE", 0, 0)

    assertEquals(EmptySpace(0, 0), emptySpace)
  }

  @Test
  fun `Process a null SPACE Astral Object when is not SPACE`() {
    val emptySpaceProcessor = EmptySpaceProcessor()

    val emptySpace = emptySpaceProcessor.process("POLYANET", 0, 0)

    assertNull(emptySpace)
  }

  @ParameterizedTest(name = "Process a {0} Astral Object")
  @ValueSource(strings = ["UP_COMETH", "LEFT_COMETH", "RIGHT_COMETH", "DOWN_COMETH"])
  fun `Process a COMETH Astral Object`(cometh: String) {
    val expectedDirection = Direction.fromString(cometh.substringBefore("_"))
    val processor = ComETHProcessor()

    val comETH = processor.process(cometh, 0, 0)

    assertEquals(ComETH(expectedDirection, 0, 0), comETH)
  }

  @Test
  fun `Process a null COMETH Astral Object when is not COMETH`() {
    val processor = ComETHProcessor()

    val comETH = processor.process("SPACE", 0, 0)

    assertNull(comETH)
  }

  @Test
  fun `Throws NoValidDirection when there is not Direction on COMETH`() {
    val processor = ComETHProcessor()

    val exception: NoValidDirection = assertThrows(NoValidDirection::class.java) {
      processor.process("COMETH", 0, 0)
    }

    assertEquals("COMETH is NOT a valid Direction", exception.message)
  }

  @ParameterizedTest(name = "Process a {0} Astral Object")
  @ValueSource(strings = ["BLUE_SOLOON", "PURPLE_SOLOON", "WHITE_SOLOON", "RED_SOLOON"])
  fun `Process a SOLoon Astral Object`(soloon: String) {
    val expectedColor = Color.fromString(soloon.substringBefore("_"))
    val processor = SOLoonProcessor()

    val soloon = processor.process(soloon, 0, 0)

    assertEquals(SOLoon(expectedColor, 0, 0), soloon)
  }

  @Test
  fun `Throws NoValidColor when there is not Color on SOLoon`() {
    val processor = SOLoonProcessor()

    val exception: NoValidColor = assertThrows(NoValidColor::class.java) {
      processor.process("SOLOON", 0, 0)
    }

    assertEquals("SOLOON is NOT a valid Color", exception.message)
  }

  @Test
  fun `Process a null SOLOON Astral Object when is not SOLOON`() {
    val processor = SOLoonProcessor()

    val comETH = processor.process("SPACE", 0, 0)

    assertNull(comETH)
  }
}
