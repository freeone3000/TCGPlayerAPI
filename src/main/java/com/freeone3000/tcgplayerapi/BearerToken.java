package com.freeone3000.tcgplayerapi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;

import java.time.ZonedDateTime;

/**
 * Data class for encapsulating a TCGPlayer TCGPlayerAPI token.
 *
 * Future plans are to move this to pure Kotlin, but can be in Java for now
 * as this is not intended to be called from the client side.
 */
@SuppressWarnings({"unused", "WeakerAccess"}) //json binding class
public class BearerToken {
	@JsonProperty("access_token")
	@Nullable
	public String accessToken;

	@JsonProperty("token_type")
	@Nullable
	public String tokenType; //"bearer"

	/**
	 * Prefer use of timeIssued and timeExpires. Here for full TCGPlayerAPI compatibility.
	 * @deprecated Prefer use of timeIssued and timeExpires.
	 * @see BearerToken#timeIssued
	 * @see BearerToken#timeExpires
	 */
	@JsonProperty("expires_in")
	@Nullable
	@Deprecated
	public Long expiresIn; //long since the time this object was created

	@JsonProperty("userName")
	@Nullable
	public String userName; //"PUBLIC_KEY"

	@JsonProperty(".issued")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E, d MMM yyyy HH:mm:ss z")
	@Nullable
	public ZonedDateTime timeIssued;

	@JsonProperty(".expires")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E, d MMM yyyy HH:mm:ss z")
	@Nullable
	public ZonedDateTime timeExpires;

	@SuppressWarnings("unused") //used in Jackson
	public BearerToken() {
	}
}
