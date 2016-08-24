package com.tw.embeddoc;


import com.tw.embeddoc.events.EquipmentEvent;
import com.tw.embeddoc.events.ShipEquipmentEvent;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/equipment/ship")
@Produces(MediaType.APPLICATION_JSON)
public class ShipEquipmentResource {
	public ImmutableDataStore<ShipEquipmentEvent> events = new ImmutableDataStore<>();

	@GET
	@Path("/{equipmentId}")
	public ShipEquipmentEvent getEquipment(@PathParam("equipmentId") String equipmentId) {
		return events.aggregateEvents(equipmentId, new ShipEquipmentEvent());
	}

	@POST
	public EquipmentEvent postEquipment(ShipEquipmentEvent e) {
		events.addEvent(e);
		return e;
	}

}
