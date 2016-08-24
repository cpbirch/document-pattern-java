package com.tw.embeddoc.events;


import com.tw.embeddoc.events.EquipmentEvent;
import com.tw.embeddoc.events.Event;
import com.tw.embeddoc.events.ShipEquipmentEvent;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

public class ShipEquipementEventTest {

	@Test
	public void shouldCopyShipEquipmentEvent() throws Exception {
		// given
		ShipEquipmentEvent e = new ShipEquipmentEvent()
				.setCost(200000000).setLength(129).setWidth(24).setTonnage(15000).setMaxCrew(30).setShipType("Research Vessel")
				.setName("Boaty McBoatface").setType("Ship")
				.setEventType("create");
		// when
		ShipEquipmentEvent target = e.applyTo(new ShipEquipmentEvent());
		// then
		Assert.assertTrue(e.name.equals(target.name));
		Assert.assertTrue(e.maxCrew == target.maxCrew);
	}

	@Test
	public void shouldMergeTwoEventSubTypes() throws Exception {
		// given
		EquipmentEvent e1 = new EquipmentEvent("create", "1", "A", "Ship");
		ShipEquipmentEvent e2 = new ShipEquipmentEvent()
				.setCost(200000000).setLength(129).setWidth(24).setTonnage(15000).setMaxCrew(30).setShipType("Research Vessel")
				.setName("Boaty McBoatface")
				.setEquipmentId("1")
				.setEventType("update");
		Collection<Event> events = Arrays.asList(e1, e2);
		// when
		EquipmentEvent target = Event.mergeEvents(events, new ShipEquipmentEvent());
		// then
		Assert.assertEquals("Boaty McBoatface", target.name);
		Assert.assertEquals("Ship", target.type);
	}

}
