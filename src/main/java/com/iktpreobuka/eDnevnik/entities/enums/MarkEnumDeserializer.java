package com.iktpreobuka.eDnevnik.entities.enums;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class MarkEnumDeserializer extends JsonDeserializer<MarkEnum> {

    @Override
    public MarkEnum deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        Integer value = parser.getIntValue();
        switch (value) {
            case 1:
                return MarkEnum.INSUFFICIENT;
            case 2:
                return MarkEnum.SUFFICIENT;
            case 3:
                return MarkEnum.GOOD;
            case 4:
                return MarkEnum.VERY_GOOD;
            case 5:
                return MarkEnum.EXCELLENT;
            default:
                throw new JsonParseException(parser, "Invalid mark value: " + value);
        }
    }
}