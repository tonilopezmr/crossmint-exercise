package com.crossmint.tonilopezmr.domain

data class NoPOLYanetAdjacentForSOLoonCreation(val soLoon: SOLoon) : RuntimeException("There is NOT a POLYanet adjacent of $soLoon")

data class NoValidColor(val color: String) : RuntimeException("$color is NOT a valid Color")
data class NoValidDirection(val direction: String) : RuntimeException("$direction is NOT a valid Direction")
