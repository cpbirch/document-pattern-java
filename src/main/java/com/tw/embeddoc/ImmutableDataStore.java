package com.tw.embeddoc;

import com.tw.embeddoc.events.EquipmentEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImmutableDataStore<T extends EquipmentEvent> {

	ArrayList<T> data = new ArrayList<>();

	public T addEvent(T event) {
		data.add(event);
		return event;
	}

	private Stream<T> filterById (String equipmentId) {
		return data.stream().filter(e -> e.equipmentId.equals(equipmentId));
	}

	public Collection<T> findEventsById (String equipmentId) {
		return filterById(equipmentId).collect(Collectors.toList());
	}

	public T aggregateEvents (String equipmentId, T targetEquipmentEvent) {
		return filterById(equipmentId)
				.reduce(
						targetEquipmentEvent,
						(result, event) -> event.applyTo(result));
	}

	public int getCount() {
		return data.size();
	}
}
