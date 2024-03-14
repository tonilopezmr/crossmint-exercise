package com.crossmint.tonilopezmr.services

import com.crossmint.tonilopezmr.domain.AstralObject
import com.crossmint.tonilopezmr.domain.Color
import com.crossmint.tonilopezmr.domain.ComETH
import com.crossmint.tonilopezmr.domain.Direction
import com.crossmint.tonilopezmr.domain.EmptySpace
import com.crossmint.tonilopezmr.domain.POLYanet
import com.crossmint.tonilopezmr.domain.SOLoon

interface AstralObjectProcessor {
  fun process(astralObject: String, row: Int, column: Int): AstralObject?
}

class EmptySpaceProcessor : AstralObjectProcessor {
  companion object {
    private const val EMPTY_SPACE = "SPACE"
  }

  override fun process(astralObject: String, row: Int, column: Int): AstralObject? =
    if (astralObject == EMPTY_SPACE) EmptySpace(row, column) else null
}

class POLYanetProcessor : AstralObjectProcessor {
  companion object {
    private const val POLYANET = "POLYANET"
  }

  override fun process(astralObject: String, row: Int, column: Int): AstralObject? =
    if (astralObject == POLYANET) POLYanet(row, column) else null
}

class SOLoonProcessor : AstralObjectProcessor {
  companion object {
    private const val SOLOON = "SOLOON"
  }

  override fun process(astralObject: String, row: Int, column: Int): AstralObject? =
    if (astralObject.endsWith(SOLOON)) {
      val color = Color.fromString(astralObject.substringBefore("_"))
      SOLoon(color, row, column)
    } else {
      null
    }
}

class ComETHProcessor : AstralObjectProcessor {
  companion object {
    private const val COMETH = "COMETH"
  }

  override fun process(astralObject: String, row: Int, column: Int): AstralObject? =
    if (astralObject.endsWith(COMETH)) {
      val direction = Direction.fromString(astralObject.substringBefore("_"))
      ComETH(direction, row, column)
    } else {
      null
    }
}

object AstralObjectBuilder {

  private val possibleAstralObjects = listOf(
    EmptySpaceProcessor(),
    POLYanetProcessor(),
    SOLoonProcessor(),
    ComETHProcessor()
  )

  fun build(rawAstralObject: String, row: Int, column: Int): AstralObject =
    possibleAstralObjects.firstNotNullOfOrNull { it.process(rawAstralObject, row, column) }
      ?: throw UnidentifiedAstralObject(rawAstralObject)
}
