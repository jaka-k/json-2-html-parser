package com.flawlesscode.model;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;
import java.util.Map;

public class MetaTags {

    private JsonNode meta;
    private StringBuffer metaTags = new StringBuffer();
    private Integer indentationDepth;
    private String tab = "\t";

    public MetaTags(JsonNode meta, Integer indentationDepth) {
        this.meta = meta;
        this.indentationDepth = indentationDepth;
    }

    public String getMetaTags() {
        writeMetaTags();
        return metaTags.toString();
    }

    public void setMetaTags(String metaTags) {
        this.metaTags.append(metaTags);
    }

    public void writeMetaTags() {
        String metaTagString = "<meta name=\"%s\" content=%s>\n";
        Iterator<Map.Entry<String, JsonNode>> iterator = meta.fields();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> entry = iterator.next();
            setMetaTags(tab.repeat(indentationDepth));
            if(entry.getKey().equalsIgnoreCase("charset")) {
                setMetaTags(String.format("<meta %s=%s>\n", entry.getKey(), entry.getValue().toString()));
            } else if (entry.getKey().equalsIgnoreCase("viewport")) {
                Viewport viewport= new Viewport(entry.getValue());
                setMetaTags(String.format(metaTagString, entry.getKey(), viewport.getParsedViewport()));
            } else {
                setMetaTags(String.format(metaTagString, entry.getKey(), entry.getValue().toString()));
            }
        }
    }

}
