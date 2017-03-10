package com.tw.embeddoc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.embeddoc.docs.DocEquipmentEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class ImmutableDocStoreTest {
	public ImmutableDocStore<DocEquipmentEvent> data = null;

	ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() throws Exception {
		data = new ImmutableDocStore<>();
	}

	@Test
	public void shouldAddAnEventToTheDataStore() throws Exception {
		// given
		JsonNode json = mapper.readTree("{\"k1\":\"v1\"}");
		DocEquipmentEvent e = DocEquipmentEvent.createFromJson(json);
		// when
		data.addEvent(e);
		// then
		Assert.assertEquals(1, data.getCount());
	}

	@Test
	public void shouldReturnTwoEventsMatchingAnEquipmentID() throws Exception {
		// given
		JsonNode json = mapper.readTree("{\"equipmentId\":\"1\"}");
		DocEquipmentEvent e1 = DocEquipmentEvent.createFromJson(json);

		JsonNode json2 = mapper.readTree("{\"equipmentId\":\"2\"}");
		DocEquipmentEvent e2 = DocEquipmentEvent.createFromJson(json2);

		JsonNode json3 = mapper.readTree("{\"equipmentId\":\"1\", \"name\":\"Boaty\"}");
		DocEquipmentEvent e3 = DocEquipmentEvent.createFromJson(json3);

		data.addEvent(e1);
		data.addEvent(e2);
		data.addEvent(e3);
		// when
		Collection<DocEquipmentEvent> list = data.findEventsById("1");
		// then
		Assert.assertTrue(list.size() == 2);
	}

	@Test
	public void shouldMergeEventsFromTheDatastore() throws Exception {
		// given
		JsonNode json = mapper.readTree("{\"equipmentId\":\"1\", \"type\":\"Ship\"}\"}");
		DocEquipmentEvent e1 = DocEquipmentEvent.createFromJson(json);

		JsonNode json2 = mapper.readTree("{\"equipmentId\":\"1\", \"name\":\"Boaty\"}");
		DocEquipmentEvent e2 = DocEquipmentEvent.createFromJson(json2);

		JsonNode json3 = mapper.readTree("{\"equipmentId\":\"1\", \"length\":\"129\"}");
		DocEquipmentEvent e3 = DocEquipmentEvent.createFromJson(json3);

		data.addEvent(e1);
		data.addEvent(e2);
		data.addEvent(e3);

		// when
		DocEquipmentEvent result = new DocEquipmentEvent();

		data.aggregateEvents("1", result);
		// then
		Assert.assertEquals("Boaty", result.getValue("name"));
		Assert.assertEquals("129", result.getValue("length"));
		Assert.assertEquals("Ship", result.getValue("type"));
	}
}
