package com.crossmint.tonilopezmr.services

import com.crossmint.tonilopezmr.domain.AstralObject
import com.crossmint.tonilopezmr.domain.Color
import com.crossmint.tonilopezmr.domain.ComETH
import com.crossmint.tonilopezmr.domain.Direction
import com.crossmint.tonilopezmr.domain.EmptySpace
import com.crossmint.tonilopezmr.domain.Megaverse
import com.crossmint.tonilopezmr.domain.POLYanet
import com.crossmint.tonilopezmr.domain.SOLoon
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class POLYanetBody(
  val candidateId: String,
  val row: Int,
  val column: Int
)

fun POLYanet.toBody(candidateId: String): POLYanetBody = POLYanetBody(
  candidateId = candidateId,
  row = x,
  column = y
)

data class SOLoonBody(
  val candidateId: String,
  val row: Int,
  val column: Int,
  val color: Color
)

fun SOLoon.toBody(candidateId: String): SOLoonBody = SOLoonBody(
  candidateId = candidateId,
  row = x,
  column = y,
  color = color
)

data class ComETHBody(
  val candidateId: String,
  val row: Int,
  val column: Int,
  val direction: Direction
)

fun ComETH.toBody(candidateId: String): ComETHBody = ComETHBody(
  candidateId = candidateId,
  row = x,
  column = y,
  direction = direction
)

data class GoalResponse @JsonCreator constructor(
  @JsonProperty("goal") val goal: Array<Array<String>>
)

fun GoalResponse.toMegaverse(): Megaverse {
  val targetMatrix: Array<Array<AstralObject>> = Array(goal.size) { Array(goal[0].size) { EmptySpace(0, 0) } }

  for ((rowIndex, row) in goal.withIndex()) {
    for ((colIndex, element) in row.withIndex()) {
      targetMatrix[rowIndex][colIndex] = AstralObjectBuilder.build(element, rowIndex, colIndex)
    }
  }

  return Megaverse(targetMatrix)
}
