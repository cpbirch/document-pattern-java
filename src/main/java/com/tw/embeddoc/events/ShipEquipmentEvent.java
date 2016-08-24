package com.tw.embeddoc.events;


public class ShipEquipmentEvent extends EquipmentEvent {
	
	public ShipEquipmentEvent() {}

	
	public String shipType = null;
	public int length = 0;
	public int width = 0;
	public int tonnage = 0;
	public int maxCrew = 30;
	public int cost = 0;

	@Override
	public <T extends Event> T applyTo(T target) {
		super.applyTo(target);
		ShipEquipmentEvent see = (ShipEquipmentEvent) target;
		see.setShipType(shipType)
				.setLength(length)
				.setWidth(width)
				.setTonnage(tonnage)
				.setMaxCrew(maxCrew)
				.setCost(cost);
		return target;
	}

	public <T extends ShipEquipmentEvent> T setShipType(String shipType) {
		if (shipType != null)
			this.shipType = shipType;
		return (T) this;
	}

	public <T extends ShipEquipmentEvent> T setLength(int val) {
		if (val != 0)
			this.length = val;
		return (T) this;
	}

	public <T extends ShipEquipmentEvent> T setWidth(int val) {
		if (val != 0)
			this.width = val;
		return (T) this;
	}

	public <T extends ShipEquipmentEvent> T setTonnage(int val) {
		if (val != 0)
			this.tonnage = val;
		return (T) this;
	}

	public <T extends ShipEquipmentEvent> T setMaxCrew(int val) {
		if (val != 0)
			this.maxCrew = val;
		return (T) this;
	}

	public <T extends ShipEquipmentEvent> T setCost(int val) {
		if (val != 0)
			this.cost = val;
		return (T) this;
	}

	/*
"lab-workshops": 20
"name": "RRS Boaty McBoatface"
"width": 24
"equipment": "2"
"type": "ship"
"sub-type": "research vessel"
"length": 129
"tonnage": 15000
"cost": 200000000
"helicopters": 2
"max-crew": 90
"event-type": "update-event"
	 */

}
