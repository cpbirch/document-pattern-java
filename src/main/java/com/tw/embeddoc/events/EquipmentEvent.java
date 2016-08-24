package com.tw.embeddoc.events;


public class EquipmentEvent extends Event {

	public String equipmentId = null;
	public String name = null;
	public String type = null;

	public EquipmentEvent() {}

	public EquipmentEvent(String eventType, String equipmentId, String name, String type) {
		this.equipmentId = equipmentId;
		this.name = name;
		this.type = type;
		this.eventType = eventType;
	}

	@Override
	public <T extends Event> T applyTo(T target) {
		EquipmentEvent e = (EquipmentEvent) target;
		e.setEquipmentId(equipmentId)
				.setType(type)
				.setName(name);
		return target;
	}

	public <T extends EquipmentEvent> T setEquipmentId(String equipmentId) {
		if (equipmentId != null)
			this.equipmentId = equipmentId;
		return (T) this;
	}

	public <T extends EquipmentEvent> T setName(String name) {
		if (name != null)
			this.name = name;
		return (T) this;
	}

	public <T extends EquipmentEvent> T setType(String type) {
		if (type != null)
			this.type = type;
		return (T) this;
	}
}
