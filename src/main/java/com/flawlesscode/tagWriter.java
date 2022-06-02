package com.flawlesscode;

import com.fasterxml.jackson.databind.JsonNode;
import com.flawlesscode.model.LinkTags;
import com.flawlesscode.model.MetaTags;
import com.flawlesscode.model.HtmlTag;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class tagWriter {
    private Integer recursiveDepth = 0;

    public String recursiveTagWriter(JsonNode node) {
        StringBuffer result = new StringBuffer();
        String[] exemptions = {"attributes", "doctype", "language"};
        if (node.get("doctype") != null) {
            result.append(String.format("<!DOCTYPE %s>\n", node.get("doctype").textValue()));
        }
        if (node.get("language") != null) {
            result.append(String.format("<html lang=\"%s\">\n", node.get("language").asText()));
        }
        recursiveDepth++;
        Iterator<Map.Entry<String, JsonNode>> iterator = node.fields();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> entry = iterator.next();
            if (Arrays.asList(exemptions).contains(entry.getKey())) {
                continue;
            }
            if (entry.getKey().equalsIgnoreCase("meta")) {
                MetaTags meta = new MetaTags(entry.getValue(), recursiveDepth);
                result.append(meta.getMetaTags());
                continue;
            }
            if (entry.getKey().equalsIgnoreCase("link")) {
                LinkTags link = new LinkTags(entry.getValue(), recursiveDepth);
                result.append(link.getLinkTags());
                continue;
            }
            HtmlTag tag = new HtmlTag(entry.getKey(), entry.getValue(), entry.getValue().get("attributes"), recursiveDepth);
            tag.getContent();
            if (tag.getContent().isObject()) {
                result.append(tag.getNestedOpeningTag() + recursiveTagWriter(tag.getContent()) + tag.getNestedClosingTag());
            } else if (tag.getContent().isValueNode()) {
                result.append(tag.getOpeningTag() + tag.getContent().textValue() + tag.getClosingTag());
            }
        }
        recursiveDepth--;
        return result.toString();
    }
}