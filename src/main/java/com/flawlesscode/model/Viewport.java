package com.flawlesscode.model;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;
import java.util.Map;

public class Viewport {

    private JsonNode viewport;
    private StringBuffer parsedViewport = new StringBuffer();


    public Viewport(JsonNode viewport) {
        this.viewport = viewport;
    }


    public void parseViewport() {
        StringBuffer midResult = new StringBuffer();
        Iterator<Map.Entry<String, JsonNode>> iterator = viewport.fields();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> it = iterator.next();
            if (it.getValue().textValue() != null) {
                midResult.append(String.format("%s=%s ", it.getKey(), it.getValue().textValue()));
            } else {
                midResult.append(String.format("%s=%s ", it.getKey(), it.getValue()));
            }
        }
        this.parsedViewport.append(String.format("\"%s\"", midResult));
    }

    public String getParsedViewport() {
        parseViewport();
        return parsedViewport.toString();
    }

}
