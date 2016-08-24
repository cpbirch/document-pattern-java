package com.tw.embeddoc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.embeddoc.docs.DocEvent;
import com.tw.embeddoc.events.EquipmentEvent;
import com.tw.embeddoc.events.ResearchShipEquipmentEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class ImmutableDocStoreTest {
	public ImmutableDocStore<DocEvent> data = null;

	ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() throws Exception {
		data = new ImmutableDocStore<>();
	}

	@Test
	public void shouldAddAnEventToTheDataStore() throws Exception {
		// given
		JsonNode json = mapper.readTree("{\"k1\":\"v1\"}");
		DocEvent e = DocEvent.createFromJson(json);
		// when
		data.addEvent(e);
		// then
		Assert.assertEquals(1, data.getCount());
	}

	@Test
	public void shouldReturnTwoEventsMatchingAnEquipmentID() throws Exception {
		// given
		JsonNode json = mapper.readTree("{\"equipmentId\":\"1\"}");
		DocEvent e1 = DocEvent.createFromJson(json);

		JsonNode json2 = mapper.readTree("{\"equipmentId\":\"2\"}");
		DocEvent e2 = DocEvent.createFromJson(json2);

		JsonNode json3 = mapper.readTree("{\"equipmentId\":\"1\", \"name\":\"Boaty\"}");
		DocEvent e3 = DocEvent.createFromJson(json3);

		data.addEvent(e1);
		data.addEvent(e2);
		data.addEvent(e3);
		// when
		Collection<DocEvent> list = data.findEventsById("1");
		// then
		Assert.assertTrue(list.size() == 2);
	}

	@Test
	public void shouldMergeEventsFromTheDatastore() throws Exception {
		// given
		JsonNode json = mapper.readTree("{\"equipmentId\":\"1\", \"type\":\"Ship\"}\"}");
		DocEvent e1 = DocEvent.createFromJson(json);

		JsonNode json2 = mapper.readTree("{\"equipmentId\":\"1\", \"name\":\"Boaty\"}");
		DocEvent e2 = DocEvent.createFromJson(json2);

		JsonNode json3 = mapper.readTree("{\"equipmentId\":\"1\", \"length\":\"129\"}");
		DocEvent e3 = DocEvent.createFromJson(json3);

		data.addEvent(e1);
		data.addEvent(e2);
		data.addEvent(e3);

		// when
		DocEvent result = new DocEvent();

		data.aggregateEvents("1", result);
		// then
		Assert.assertEquals("Boaty", result.getValue("name"));
		Assert.assertEquals("129", result.getValue("length"));
		Assert.assertEquals("Ship", result.getValue("type"));
	}
}
