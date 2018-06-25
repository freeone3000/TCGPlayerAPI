package com.freeone3000.tcgplayerapi

import com.freeone3000.tcgplayerapi.auth.TCGPlayerAuthenticationInfo
import com.freeone3000.tcgplayerapi.auth.readDataFromResource
import com.freeone3000.tcgplayerapi.data.MtgCard
import com.freeone3000.tcgplayerapi.data.MtgSet
import org.testng.annotations.BeforeClass
import org.testng.annotations.DataProvider
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

    @DataProvider(name = "cardgen")
    fun generateCards(): Array<Array<Any>> {
        return arrayOf(
                arrayOf<Any>(MtgCard("Dispel", "", false, MtgSet("", ""))),
                arrayOf<Any>(MtgCard("The Tabernacle at Pendrell Vale", "en", false, MtgSet("Legends", "LEG")))
        )
    }

    @Test(dataProvider = "cardgen")
    fun testGetCardPricesForName(testCard: MtgCard) {
        val authInfo = authenticationInfo!!

        val api = TCGPlayer(authInfo)
        val items = api.getCardPricesForName(testCard)

        // TODO Test Language matching (no cards from wrong language, if specified)
        // TODO Test Condition matching (no cards from wrong set, if specified)
        // TODO Test Set matching (no cards from outside of set, if specified)

        items.forEach { item ->
            System.out.println("Card: " + item.first + "\nPrice: " + item.second + "\n\n");
            assert(!item.first.isBlank())

            val price = item.second
            assertNotEquals(0, price.conditionId)
            if(price.price.isNaN()) { //either all found or none found
                assert(price.lowestRange.isNaN())
                assert(price.highestRange.isNaN())
            } else {
                assertNotEquals(0.00, price.price)
                assertNotEquals(0.00, price.lowestRange)
                assertNotEquals(0.00, price.highestRange)
                assert(price.lowestRange < price.highestRange)
            }
        }
    }

    @Test(groups = ["debugging"], enabled = false)
    fun testDateTimeFormat() {
        val format = "E, d MMM yyyy HH:mm:ss z"
        val formatter= DateTimeFormatter.ofPattern(format)!!
        val date = ZonedDateTime.parse("Sat, 16 Jun 2018 20:03:40 GMT", formatter)
    }

    @Test(groups = ["debugging"], enabled = false)
    fun testPrintCategories() {
        val authInfo = authenticationInfo!!
        val api = TCGPlayer(authInfo)

        System.out.println(api.listCategories())
    }
}