package com.tw.embeddoc.events;


public class ResearchShipEquipmentEvent extends ShipEquipmentEvent {
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

	public int labWorkshops = 0;
	public int scientificCrew = 0;
	public int helicopters = 0;

	@Override
	public <T extends Event> T applyTo(T target) {
		super.applyTo(target);
		ResearchShipEquipmentEvent rsee = (ResearchShipEquipmentEvent) target;
		rsee.setLabWorkshops(labWorkshops).setHelicopters(helicopters).setScientificCrew(scientificCrew);
		return target;
	}

	public ResearchShipEquipmentEvent setLabWorkshops(int val) {
		if (val != 0)
			this.labWorkshops = val;
		return this;
	}

	public ResearchShipEquipmentEvent setScientificCrew(int val) {
		if (val != 0)
			this.scientificCrew = val;
		return this;
	}

	public ResearchShipEquipmentEvent setHelicopters(int val) {
		if (val != 0)
			this.helicopters = val;
		return this;
	}
}
