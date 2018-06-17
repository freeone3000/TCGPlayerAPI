package com.freeone3000.tcgplayerapi

import com.freeone3000.tcgplayerapi.auth.AuthenticationFailureException
import com.freeone3000.tcgplayerapi.auth.TCGPlayerAuthenticationInfo
import com.freeone3000.tcgplayerapi.data.CardPrice
import com.freeone3000.tcgplayerapi.data.CardSku
import com.freeone3000.tcgplayerapi.data.InternalMtgCard
import com.freeone3000.tcgplayerapi.data.MtgCard
import com.freeone3000.tcgplayerapi.data.mapping.JacksonUnirestObjectMapper
import com.mashape.unirest.http.Unirest
import org.json.JSONObject
import java.time.ZonedDateTime

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

//version of TCGPlayerAPI this is written against
const val BASE_API = "https://api.tcgplayer.com/v1.9.1"

//set by TCGPlayer
const val MTG_CATEGORY_ID = 1;

/**
 * Creates a TCGPlayerAPI interface.
 * This interface will automatically request and renew BearerTokens as appropriate.
 * See https://docs.tcgplayer.com/v1.9.1/reference
 *
 * @param authenticationInfo The authentication required to request a BearerToken
 */
class TCGPlayer(private val authenticationInfo: TCGPlayerAuthenticationInfo) {
    companion object {
        init {
            //this needs to be run exactly once
            Unirest.setObjectMapper(JacksonUnirestObjectMapper())
        }
    }

    private var bearerToken: BearerToken

    init {
        bearerToken = requestBearerToken(authenticationInfo)
    }

    private fun validateBearerToken() {
        val expiryTime = bearerToken.timeExpires
        if(expiryTime == null || expiryTime >= ZonedDateTime.now()) {
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
    fun getCardPricesForName(card: MtgCard): List<Pair<String, CardPrice>> {
        validateBearerToken()
        TODO("Implement")
    }

    fun listCategories(): List<JSONObject> {
        validateBearerToken()

        val resultList = ArrayList<JSONObject>();

        //stepwise combine all results (they paginate)
        for(i in 0..Integer.MAX_VALUE step 10) {
            val resp = Unirest.get("$BASE_API/catalog/categories?offset=$i&limit=10")
                    .header("Authorization", bearerToken.header())
                    .asJson()
            val body = resp?.body?.`object` ?: throw RuntimeException("Could not read response")
            if (resp.status != 200) {
                throw RuntimeException("Request failed: $body");
            }
            if (!body.getBoolean("success")) {
                throw RuntimeException("Request failed: $body");
            }

            val resultArray = body.getJSONArray("results")!!
            for(resultIdx in 0..resultArray.length()-1) { //copies into output
                resultList.add(resultArray.getJSONObject(resultIdx))
            }

            if(resultArray.length() < 10) { //last element returns
                break
            }
        }
        return resultList
    }

    fun requestBearerToken(authenticationInfo: TCGPlayerAuthenticationInfo): BearerToken {
        val resp = Unirest.post("$BASE_API/token")
                .header("X-Tcg-Access-Token", authenticationInfo.accessToken)
                .field("grant_type", "client_credentials")
                .field("client_id", authenticationInfo.publicKey)
                .field("client_secret", authenticationInfo.privateKey)
                .asObject(BearerToken::class.java) ?: throw AuthenticationFailureException("Mapped to null response")

        if(resp.status != 200) {
            val responseText = resp.rawBody.bufferedReader().readText()
            throw AuthenticationFailureException("Status code: ${resp.status}\nReason: $responseText")
        }

        return resp.body ?: throw AuthenticationFailureException("Null body on response")
    }
}