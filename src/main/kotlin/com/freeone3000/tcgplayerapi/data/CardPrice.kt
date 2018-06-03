package com.freeone3000.tcgplayerapi.data

data class CardPrice(val conditionId: CardSku,
                     val price: Double,
                     val lowestRange: Double,
                     val highestRange: Double)