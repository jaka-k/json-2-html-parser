package com.flawlesscode.model;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;
import java.util.Map;

public class LinkTags {

    private JsonNode link;
    private StringBuffer linkTags = new StringBuffer();
    private Integer indentationDepth = 0;

    public LinkTags(JsonNode link, int indentationDepth) {
        this.link = link;
        this.indentationDepth = indentationDepth;
    }
    public String getLinkTags() {
        writeLinkTags();
        return linkTags.toString();
    }
    public void setLinkTags(String linkTags) {
        this.linkTags.append(linkTags);
    }
    public void writeLinkTags() {
        StringBuffer linkValues = new StringBuffer();
        String tab = "\t";
        for (JsonNode objNode : link) {
            setLinkTags(tab.repeat(indentationDepth));
            setLinkTags("<link");
            Iterator<Map.Entry<String, JsonNode>> nodes = objNode.fields();
            while (nodes.hasNext()) {
                Map.Entry<String, JsonNode> entry = nodes.next();
                linkValues.append(String.format(" %s=%s", entry.getKey(), entry.getValue()));
            }
            setLinkTags(linkValues.toString());
            setLinkTags(">\n");
            linkValues.setLength(0);
        }
    }
}
