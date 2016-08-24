package com.tw.embeddoc.docs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;

import java.util.HashMap;
import java.util.Map;


public class DocEvent {

	public static ObjectMapper mapper = new ObjectMapper();
	public static final MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, String.class);

	public static DocEvent createFromJson(JsonNode node) {
		Map<String, String> data = mapper.convertValue(node, mapType);
		return new DocEvent(node, data);
	}

	private JsonNode node;
	private Map<String, String> data = new HashMap<>();

	public DocEvent() {}

	private DocEvent(JsonNode node, Map<String, String> data) {
		this.node = node;
		this.data = data;
	}

	public JsonNode getAsJson() {
		return mapper.convertValue(data, JsonNode.class);
	}

	public String getValue (String fieldName) {
		return data.get(fieldName);
	}

	public Map<String, String> getAsMap() {return data; }

	public <T extends DocEvent> T mergeInto(T target) {
		target.getAsMap().putAll(this.data);
		return target;
	}
}
