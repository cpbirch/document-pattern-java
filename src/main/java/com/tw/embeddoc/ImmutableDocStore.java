package com.tw.embeddoc;

import com.tw.embeddoc.docs.DocEquipmentEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImmutableDocStore<T extends DocEquipmentEvent> {
	ArrayList<T> data = new ArrayList<>();

	public T addEvent(T event) {
		data.add(event);
		return event;
	}

	private Stream<T> filterById (String equipmentId) {
		return data.stream().filter(e -> e.getValue("equipmentId").equals(equipmentId));
	}

	public Collection<T> findEventsById (String equipmentId) {
		return filterById(equipmentId).collect(Collectors.toList());
	}

	public T aggregateEvents (String equipmentId, T target) {
		return filterById(equipmentId)
				.reduce(
						target,
						(result, event) -> event.mergeInto(target));
	}

	public int getCount() {
		return data.size();
	}
}
