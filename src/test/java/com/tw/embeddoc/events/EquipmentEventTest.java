package com.tw.embeddoc.events;

import com.tw.embeddoc.events.EquipmentEvent;
import com.tw.embeddoc.events.Event;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

public class EquipmentEventTest {

	@Test
	public void shouldCopyEquipmentEvents() throws Exception {
		// given
		EquipmentEvent e1 = new EquipmentEvent("create", "1", "A", "Ship");
		// when
		EquipmentEvent target = e1.applyTo(new EquipmentEvent());
		// then
		Assert.assertTrue(e1.name.equals(target.name));
	}

	@Test
	public void shouldMergeTwoEquipmentEvents() throws Exception {
		// given
		EquipmentEvent e1 = new EquipmentEvent("create", "1", "A", "Ship");
		EquipmentEvent e2 = new EquipmentEvent("update", "1", "B", "Ship");
		Collection<Event> events = Arrays.asList(e1, e2);
		// when
		EquipmentEvent target = Event.mergeEvents(events, new EquipmentEvent());
		// then
		Assert.assertEquals("B", target.name);
	}
}
