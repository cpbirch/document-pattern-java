package com.tw.embeddoc.events;

import com.tw.embeddoc.events.EquipmentEvent;
import com.tw.embeddoc.events.Event;
import com.tw.embeddoc.events.ResearchShipEquipmentEvent;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

public class ResearchShipEquipmentEventTest {
	@Test
	public void shouldCopyResearchShipEquipmentEvent() throws Exception {
		// given
		ResearchShipEquipmentEvent e = new ResearchShipEquipmentEvent()
				.setScientificCrew(60).setHelicopters(2).setLabWorkshops(20)
				.setCost(200000000).setLength(129).setWidth(24).setTonnage(15000).setMaxCrew(30).setShipType("Research Vessel")
				.setName("Boaty McBoatface").setType("Ship")
				.setEventType("create");
		// when
		ResearchShipEquipmentEvent target = e.applyTo(new ResearchShipEquipmentEvent());
		// then
		Assert.assertTrue(e.name.equals(target.name));
		Assert.assertTrue(e.scientificCrew == target.scientificCrew);
	}

	@Test
	public void shouldMergeTwoEventSubTypes() throws Exception {
		// given
		EquipmentEvent e1 = new EquipmentEvent("create", "1", "A", "Ship");
		ResearchShipEquipmentEvent e2 = new ResearchShipEquipmentEvent()
				.setScientificCrew(60).setHelicopters(2).setLabWorkshops(20)
				.setCost(200000000).setLength(129).setWidth(24).setTonnage(15000).setMaxCrew(30).setShipType("Research Vessel")
				.setName("Boaty McBoatface")
				.setEquipmentId("1")
				.setEventType("update");
		Collection<Event> events = Arrays.asList(e1, e2);
		// when
		ResearchShipEquipmentEvent target = Event.mergeEvents(events, new ResearchShipEquipmentEvent());
		// then
		Assert.assertEquals(2, target.helicopters);
		Assert.assertEquals(30, target.maxCrew);
		Assert.assertEquals("Ship", target.type);
	}

}
