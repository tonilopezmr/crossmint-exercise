package com.crossmint.tonilopezmr.usecases

import com.crossmint.tonilopezmr.Coroutines
import com.crossmint.tonilopezmr.domain.Color
import com.crossmint.tonilopezmr.domain.ComETH
import com.crossmint.tonilopezmr.domain.Direction
import com.crossmint.tonilopezmr.domain.EmptySpace
import com.crossmint.tonilopezmr.domain.Megaverse
import com.crossmint.tonilopezmr.domain.NoPOLYanetAdjacentForSOLoonCreation
import com.crossmint.tonilopezmr.domain.POLYanet
import com.crossmint.tonilopezmr.domain.SOLoon
import com.crossmint.tonilopezmr.services.MegaverseAPIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.given
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class CreateMegaverseTest {

  companion object {
    const val CANDIDATE = "candidate-id"
  }

  private val testCoroutines = Coroutines(CoroutineScope(Dispatchers.IO), Dispatchers.IO, Int.MAX_VALUE, 0)
  private val apiService: MegaverseAPIService = mock(MegaverseAPIService::class.java)

  @Test
  fun `Create a megaverse with one POLYanets`() {
    val newPOLYanet = POLYanet(0, 1)
    val megaverse = Megaverse(
      arrayOf(
        arrayOf(EmptySpace(0, 0), newPOLYanet)
      )
    )
    given(apiService.createPolyanet(CANDIDATE, newPOLYanet)).willReturn(true)

    val createMegaverse = CreateMegaverse(apiService, testCoroutines)
    createMegaverse(CANDIDATE, megaverse)

    verify(apiService, times(1)).createPolyanet(CANDIDATE, newPOLYanet)
  }

  @Test
  fun `Create a megaverse with one ComETH`() {
    val newComETH = ComETH(Direction.down, 0, 1)
    val megaverse = Megaverse(
      arrayOf(
        arrayOf(EmptySpace(0, 0), newComETH)
      )
    )
    given(apiService.createCometh(CANDIDATE, newComETH)).willReturn(true)

    val createMegaverse = CreateMegaverse(apiService, testCoroutines)
    createMegaverse(CANDIDATE, megaverse)

    verify(apiService, times(1)).createCometh(CANDIDATE, newComETH)
  }

  @Test
  fun `Create a megaverse with one SOLoons should throw NoPOLYanetAdjacentForSOLoonCreation`() {
    val newSOLoon = SOLoon(Color.red, 0, 1)
    val megaverse = Megaverse(
      arrayOf(
        arrayOf(EmptySpace(0, 0), newSOLoon)
      )
    )

    val createMegaverse = CreateMegaverse(apiService, testCoroutines)
    val exception: NoPOLYanetAdjacentForSOLoonCreation = assertThrows(NoPOLYanetAdjacentForSOLoonCreation::class.java) {
      runBlocking { createMegaverse(CANDIDATE, megaverse) }
    }

    assertEquals("There is NOT a POLYanet adjacent of $newSOLoon", exception.message)
  }

  @Test
  fun `Create a megaverse with SOLoons because has adjacent POLYanet`() {
    val newPOLYanet = POLYanet(0, 2)
    val newSOLoon = SOLoon(Color.red, 0, 1)
    val megaverse = Megaverse(
      arrayOf(
        arrayOf(EmptySpace(0, 0), newSOLoon, newPOLYanet)
      )
    )
    given(apiService.createPolyanet(CANDIDATE, newPOLYanet)).willReturn(true)
    given(apiService.createSoloon(CANDIDATE, newSOLoon)).willReturn(true)

    val createMegaverse = CreateMegaverse(apiService, testCoroutines)
    createMegaverse(CANDIDATE, megaverse)

    verify(apiService, times(1)).createSoloon(CANDIDATE, newSOLoon)
  }
}
