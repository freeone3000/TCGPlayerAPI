package com.freeone3000.tcgplayerapi.auth

import org.ini4j.Wini
import java.io.InputStream

data class TCGPlayerAuthenticationInfo(val publicKey: String,
                                       val privateKey: String,
                                       val accessToken: String)

class InvalidAuthenticationSection(reason: String): Exception(reason)

/**
 * Method to read a TCGPlayerAuthenticationInfo from a properly-formatted properties file.
 */
fun readDataFromResource(input: InputStream): TCGPlayerAuthenticationInfo {
    val ini = Wini(input);
    val section = ini["TCGPlayer"] ?: throw InvalidAuthenticationSection("Could not find TCGPlayer section")

    val publicKey = section["PUBLIC_KEY"] ?: throw InvalidAuthenticationSection("Could not find PUBLIC_KEY")
    val privateKey = section["PRIVATE_KEY"] ?: throw InvalidAuthenticationSection("Could not find PRIVATE_SECTION")
    val accessToken = section["ACCESS_TOKEN"] ?: throw InvalidAuthenticationSection("Could not find ACCESS_TOKEN")
    return TCGPlayerAuthenticationInfo(publicKey, privateKey, accessToken)
}