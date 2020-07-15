package com.jo_no.curencyconversionapp

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CurrencyResponse(
    @JsonProperty("baseCurrency") val baseCurrency: String,
    @JsonProperty("rates") val rates: RateResponse
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class RateResponse(
    @JsonProperty("USD") val usd: Long,
    @JsonProperty("EUR") val eur: Long,
    @JsonProperty("SEK") val sek: Long,
    @JsonProperty("CAD") val cad: Long
)