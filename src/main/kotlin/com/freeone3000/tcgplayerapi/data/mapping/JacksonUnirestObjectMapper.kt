package com.freeone3000.tcgplayerapi.data.mapping

import com.mashape.unirest.http.ObjectMapper

class JacksonUnirestObjectMapper : ObjectMapper {
    val jacksonObjectMapper = com.fasterxml.jackson.databind.ObjectMapper()

    init {
        jacksonObjectMapper.findAndRegisterModules()
    }

    override fun writeValue(obj: Any?): String {
        return jacksonObjectMapper.writeValueAsString(obj);
    }

    override fun <T : Any?> readValue(value: String?, type: Class<T>?): T {
        return jacksonObjectMapper.readValue(value, type);
    }
}