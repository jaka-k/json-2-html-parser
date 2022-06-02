package com.flawlesscode.model;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;
import java.util.Map;

public class Attributes {

    private JsonNode attributes;
    private StringBuffer parsedAttributes = new StringBuffer();


    public Attributes(JsonNode attributes) {
        this.attributes = attributes;
    }


    public void parseAttributes() {
        StringBuffer midResult = new StringBuffer();
        Iterator<Map.Entry<String, JsonNode>> iterator = attributes.fields();
        while (iterator.hasNext()) {

            Map.Entry<String, JsonNode> entry = iterator.next();
            if (entry.getValue().isValueNode()) {
                parsedAttributes.append(String.format(" %s", entry));
            } else if (entry.getValue().isObject()) {
                parsedAttributes.append(String.format("%s=", entry.getKey()));

                Iterator<Map.Entry<String, JsonNode>> valueIterator = entry.getValue().fields();
                while (valueIterator.hasNext()) {
                    Map.Entry<String, JsonNode> valueIt = valueIterator.next();
                    midResult.append(String.format("%s:%s;", valueIt.getKey(), valueIt.getValue().textValue()));
                }

                parsedAttributes.append(String.format("\"%s\"", midResult));
            }
        }
    }

    public String getParsedAttributes() {
        parseAttributes();

        return parsedAttributes.toString();
    }
}
