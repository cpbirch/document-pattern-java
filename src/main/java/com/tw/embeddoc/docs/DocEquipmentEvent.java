package com.tw.embeddoc.docs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;

import java.util.HashMap;
import java.util.Map;


public class DocEquipmentEvent {

	public static ObjectMapper mapper = new ObjectMapper();
	public static final MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, String.class);

	public static DocEquipmentEvent createFromJson(JsonNode node) {
		Map<String, String> data = mapper.convertValue(node, mapType);
		return new DocEquipmentEvent(node, data);
	}

	private JsonNode node;
	private Map<String, String> data = new HashMap<>();

	public DocEquipmentEvent() {}

	private DocEquipmentEvent(JsonNode node, Map<String, String> data) {
		this.node = node;
		this.data = data;
	}

	public JsonNode getAsJson() {
		return mapper.convertValue(data, JsonNode.class);
	}

	public String getValue (String fieldName) {
		return data.get(fieldName);
	}

	public boolean contains (String fieldName) {
		return data.containsKey(fieldName);
	}

	public Map<String, String> getAsMap() {return data; }

	public <T extends DocEquipmentEvent> T mergeInto(T target) {
		target.getAsMap().putAll(this.data);
		return target;
	}
}
