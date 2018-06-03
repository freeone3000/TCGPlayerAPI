package com.freeone3000.tcgplayerapi.auth

data class TCGPlayerAuthenticationInfo(val publicKey: String,
                                       val privateKey: String,
                                       val accessToken: String)