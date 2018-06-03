package com.freeone3000.tcgplayerapi.data

typealias CardSku = Long

/**
 * MTG Card for querying the API.
 */
data class MtgCard(val name: String,
                   val lang: String,
                   val foil: Boolean,
                   val set: MtgSet?)

/**
 * MTG Card for internal use. Do not guess at CardSku.
 */
data class InternalMtgCard(val productConditionId: CardSku,
                           val name: String,
                           val lang: String,
                           val foil: Boolean)