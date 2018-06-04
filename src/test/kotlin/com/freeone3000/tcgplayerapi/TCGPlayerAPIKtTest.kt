package com.freeone3000.tcgplayerapi

import com.freeone3000.tcgplayerapi.auth.TCGPlayerAuthenticationInfo
import com.freeone3000.tcgplayerapi.auth.readDataFromResource
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test

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
}