package com.tw.embeddoc;


import com.tw.embeddoc.events.EquipmentEvent;
import com.tw.embeddoc.events.Event;
import com.tw.embeddoc.events.ResearchShipEquipmentEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

public class ImmutableDataStoreTest {

	public ImmutableDataStore<EquipmentEvent> data = null;

	@Before
	public void setUp() throws Exception {
		data = new ImmutableDataStore<>();
	}

	@Test
	public void shouldAddAnEventToTheDataStore() throws Exception {
		// given
		EquipmentEvent e = new EquipmentEvent();
		// when
		data.addEvent(e);
		// then
		Assert.assertEquals(1, data.getCount());
	}

	@Test
	public void shouldReturnTwoEventsMatchingAnEquipmentID() throws Exception {
		// given
		EquipmentEvent e1 = new EquipmentEvent("create", "1", "A", "Ship");
		EquipmentEvent e2 = new EquipmentEvent("update", "1", "B", "Ship");
		EquipmentEvent e3 = new EquipmentEvent("create", "2", "C", "Ship");
		data.addEvent(e1);
		data.addEvent(e2);
		data.addEvent(e3);
		// when
		Collection<EquipmentEvent> list = data.findEventsById("1");
		// then
		Assert.assertTrue(list.size() == 2);
	}

	@Test
	public void shouldMergeEventsFromTheDatastore() throws Exception {
		// given
		EquipmentEvent e1 = new EquipmentEvent("create", "1", "A", "Ship");
		ResearchShipEquipmentEvent e2 = new ResearchShipEquipmentEvent()
				.setScientificCrew(60).setHelicopters(2).setLabWorkshops(20)
				.setCost(200000000).setLength(129).setWidth(24).setTonnage(15000).setMaxCrew(30).setShipType("Research Vessel")
				.setName("Boaty McBoatface")
				.setEquipmentId("1")
				.setEventType("update");
		data.addEvent(e1);
		data.addEvent(e2);
		ResearchShipEquipmentEvent result = new ResearchShipEquipmentEvent();
		// when
		data.aggregateEvents("1", result);
		// then
		Assert.assertEquals(60, result.scientificCrew);
		Assert.assertEquals(30, result.maxCrew);
		Assert.assertEquals("Boaty McBoatface", result.name);
		Assert.assertEquals("Ship", result.type);
	}
}
