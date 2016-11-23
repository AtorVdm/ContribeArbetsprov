package com.atorvdm.contribe.util;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MapToCoupleArraySerializer extends JsonSerializer<LinkedHashMap<?, ?>>{

    @Override
    public void serialize(LinkedHashMap<?, ?> value, JsonGenerator generator,
            SerializerProvider serializers) throws IOException,
            JsonProcessingException {
        generator.writeStartArray();
        for (Entry<?, ?> entry : value.entrySet()){
            generator.writeStartObject();
            generator.writeObjectField("key", entry.getKey());
            generator.writeObjectField("value", entry.getValue());
            generator.writeEndObject();
        }       
        generator.writeEndArray();
    }
}
