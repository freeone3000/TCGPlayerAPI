package com.freeone3000.tcgplayerapi

import com.freeone3000.tcgplayerapi.auth.TCGPlayerAuthenticationInfo
import com.freeone3000.tcgplayerapi.auth.readDataFromResource
import com.freeone3000.tcgplayerapi.data.MtgCard
import com.freeone3000.tcgplayerapi.data.MtgSet
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.test.assertNotEquals

class TCGPlayerAPIKtTest {
    companion object {
        private var authenticationInfo: TCGPlayerAuthenticationInfo? = null

        @BeforeClass
        @JvmStatic //best practices say to not use this; reconsider JUnit5?
        fun initialize() {
            authenticationInfo = readDataFromResource(TCGPlayerAPIKtTest::class.java.getResourceAsStream("auth.ini"))
        }
    }

    @Test
    fun testRequestBearerToken() {
        val authInfo = authenticationInfo!!

        val api = TCGPlayer(authInfo)
        val token = api.requestBearerToken(authInfo)
        print(token)
    }

    @Test
    fun testGetCardPricesForName() {
        val testCard = MtgCard("The Tabernacle at Pendrell Vale", "en", false, MtgSet("Legends", "LEG"))
        val authInfo = authenticationInfo!!

        val api = TCGPlayer(authInfo)
        val items = api.getCardPricesForName(testCard)

        items.forEach { item ->
            assert(!item.first.isBlank())

            val price = item.second
            assertNotEquals(0, price.conditionId)
            assertNotEquals(0.00, price.price)
            assertNotEquals(0.00, price.lowestRange)
            assertNotEquals(0.00, price.highestRange)
            assert(price.lowestRange < price.highestRange);
        }
    }

    @Test
    fun testDateTimeFormat() {
        val format = "E, d MMM yyyy HH:mm:ss z"
        val formatter= DateTimeFormatter.ofPattern(format)!!
        val date = ZonedDateTime.parse("Sat, 16 Jun 2018 20:03:40 GMT", formatter)
    }
}