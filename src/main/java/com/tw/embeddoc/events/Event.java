package com.tw.embeddoc.events;


import org.joda.time.DateTime;

import java.util.Collection;

public abstract class Event {

	public String eventType = null;
	private DateTime createdDate = DateTime.now();

	public DateTime getCreatedDate() {return createdDate; }

	public <T extends Event> T aggregate(Collection<Event> events, T target) {
		for(Event e : events) {
			e.applyTo(target);
		}
		return target;
	}

	public abstract <T extends Event> T applyTo(T target);

	public <T extends Event> T setEventType(String eventType) {
		this.eventType = eventType;
		return (T) this;
	}

	public static <T extends Event> T mergeEvents(Collection<Event> events, T target) {
		for(Event e : events) {
			e.applyTo(target);
		}
		return target;
	}
}
