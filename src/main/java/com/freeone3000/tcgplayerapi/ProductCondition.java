package com.freeone3000.tcgplayerapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductCondition {
	@JsonProperty("isFoil")
	public boolean foil;

	@JsonProperty("productConditionId")
	public Long conditionId;

	@JsonProperty("name")
	public String conditionName;

	@JsonProperty("language")
	public String language;
}
