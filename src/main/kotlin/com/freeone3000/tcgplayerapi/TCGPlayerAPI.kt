package com.freeone3000.tcgplayerapi

import com.freeone3000.tcgplayerapi.auth.TCGPlayerAuthenticationInfo
import com.freeone3000.tcgplayerapi.data.CardPrice
import com.freeone3000.tcgplayerapi.data.CardSku
import com.freeone3000.tcgplayerapi.data.InternalMtgCard
import com.freeone3000.tcgplayerapi.data.MtgCard
import java.time.Instant

/**
 * A ProductId is a general card, such as "Bayou".
 * A CardSku is a specific card, such as "Scan #198 of LEB Bayou (NM) - English"
 */
typealias ProductId = Long

/**
 * A CategoryId is a type of product, such as "Magic Cards"
 */
typealias CategoryId = Int

//for filtering searches
typealias SearchFilter = Pair<String, String>

/**
 * Creates a TCGPlayerAPI interface.
 * This interface will automatically request and renew BearerTokens as appropriate.
 *
 * @param authenticationInfo The authentication required to request a BearerToken
 * @see https://docs.tcgplayer.com/v1.9.1/reference
 */
class TCGPlayer(private val authenticationInfo: TCGPlayerAuthenticationInfo) {
    private var bearerToken: BearerToken;

    init {
        bearerToken = requestBearerToken(authenticationInfo)
    }

    private fun validateBearerToken() {
        val expiryTime = bearerToken.timeExpires
        if(expiryTime == null || expiryTime >= Instant.now()) {
            bearerToken = requestBearerToken(authenticationInfo)
        }
    }

    private fun getMarketPriceBySku(sku: CardSku): List<CardPrice> {
        validateBearerToken() //look into AOP? seems very rabbit-hole
        TODO("Implement")
    }

    private fun listProductSkusById(id: ProductId): List<InternalMtgCard> {
        validateBearerToken()
        TODO("Implement")
    }

    private fun searchInCategory(categoryId: CategoryId, filers: List<SearchFilter>): List<ProductId> {
        validateBearerToken()
        TODO("Implement")
    }

    /**
     * Gets prices for a simple MTG Card. If multiple cards are matched, returns all prices.
     * While the string is intended to be human readable, additional information can be
     * obtained by querying on the SKU exposed through CardPrice.
     *
     * @param card The card to search for
     * @return A mapping from a human-readable name to the CardPrice
     */
    public fun getCardPricesForName(card: MtgCard): List<Pair<String, CardPrice>> {
        validateBearerToken()
        TODO("Implement")
    }
}

private fun requestBearerToken(authenticationInfo: TCGPlayerAuthenticationInfo): BearerToken {
    TODO("Implement")
}