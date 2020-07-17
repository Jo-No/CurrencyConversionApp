package com.jo_no.curencyconversionapp.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class CurrencyResponse(
    @JsonProperty("baseCurrency") val baseCurrency: String,
    @JsonProperty("rates") val rates: Map<String, String>
)