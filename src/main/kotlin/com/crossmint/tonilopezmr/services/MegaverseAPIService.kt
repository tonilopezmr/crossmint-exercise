package com.crossmint.tonilopezmr.services

import com.crossmint.tonilopezmr.domain.ComETH
import com.crossmint.tonilopezmr.domain.Megaverse
import com.crossmint.tonilopezmr.domain.POLYanet
import com.crossmint.tonilopezmr.domain.SOLoon
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class MegaverseAPIService(
  private val baseUrl: String,
  private val restTemplate: RestTemplate = RestTemplate()
) {

  private val polyanetsEndpoint = "$baseUrl/polyanets"
  private val soloonsEndpoint = "$baseUrl/soloons"
  private val comethsEndpoint = "$baseUrl/comeths"

  fun createPolyanet(candidateId: String, polYanet: POLYanet): Boolean {
    val response = create(polyanetsEndpoint, polYanet.toBody(candidateId))
    return response.statusCode.is2xxSuccessful
  }

  fun deletePolyanet(candidateId: String, polYanet: POLYanet): Boolean {
    val response = delete(polyanetsEndpoint, polYanet.toBody(candidateId))
    return response.statusCode.is2xxSuccessful
  }

  fun createSoloon(candidateId: String, soLoon: SOLoon): Boolean {
    val response = create(soloonsEndpoint, soLoon.toBody(candidateId))
    return response.statusCode.is2xxSuccessful
  }

  fun deleteSoloon(candidateId: String, soLoon: SOLoon): Boolean {
    val response = delete(soloonsEndpoint, soLoon.toBody(candidateId))
    return response.statusCode.is2xxSuccessful
  }

  fun createCometh(candidateId: String, comETH: ComETH): Boolean {
    val response = create(comethsEndpoint, comETH.toBody(candidateId))
    return response.statusCode.is2xxSuccessful
  }

  fun deleteCometh(candidateId: String, comETH: ComETH): Boolean {
    val response = delete(comethsEndpoint, comETH.toBody(candidateId))
    return response.statusCode.is2xxSuccessful
  }

  fun getGoalMap(candidateId: String): Megaverse? {
    val url = "$baseUrl/map/$candidateId/goal"
    val goalResponse = restTemplate.getForObject(url, GoalResponse::class.java)
    return goalResponse?.toMegaverse()
  }

  private fun <A> delete(
    url: String,
    body: A
  ) = restTemplate.exchange(url, HttpMethod.DELETE, toBody(body), String::class.java)

  private fun <A> create(
    url: String,
    body: A
  ) = restTemplate.postForEntity(url, toBody(body), String::class.java)

  private fun <A> toBody(body: A): HttpEntity<A> {
    val headers = HttpHeaders()
    headers.contentType = MediaType.APPLICATION_JSON
    return HttpEntity(body, headers)
  }
}
