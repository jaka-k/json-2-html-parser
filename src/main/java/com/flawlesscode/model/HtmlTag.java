package com.flawlesscode.model;

import com.fasterxml.jackson.databind.JsonNode;

public class HtmlTag {

    private String tag;
    private JsonNode content;
    private JsonNode attributes;
    private Integer indentationDepth;
    private String tab = "\t";

    public HtmlTag(String tag, JsonNode content, JsonNode attributes, Integer indentationDepth) {
        this.tag = tag;
        this.content = content;
        this.attributes = attributes;
        this.indentationDepth = indentationDepth;
    }

    public String getOpeningTag() {
        if(attributes != null) {
            return String.format("%s<%s%s>", tab.repeat(indentationDepth), this.tag, getParsedAttributes());
        } else
            return String.format("%s<%s>", tab.repeat(indentationDepth), this.tag);
    }

    public String getNestedOpeningTag() {
        if(attributes != null) {
            return String.format("%s<%s%s>\n", tab.repeat(indentationDepth), this.tag, getParsedAttributes());
        } else
            return String.format("%s<%s>\n", tab.repeat(indentationDepth), this.tag);
    }

    public String getClosingTag() {
        return String.format("</%s>\n", this.tag);
    }

    public String getNestedClosingTag() {
        return String.format("%s</%s>\n",tab.repeat(indentationDepth), this.tag);
    }

    public String getParsedAttributes() {
        Attributes attributesObject = new Attributes(this.attributes);
        String result = attributesObject.getParsedAttributes();
        return result;
    }

    public JsonNode getContent() {
        return content;
    }
}
