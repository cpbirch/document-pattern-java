package com.tw.embeddoc;

import com.tw.embeddoc.events.EquipmentEvent;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/equipment")
@Produces(MediaType.APPLICATION_JSON)
public class EquipmentResource {

	public ImmutableDataStore<EquipmentEvent> events = new ImmutableDataStore<>();

	@GET
	@Path("/{equipmentId}")
	public EquipmentEvent getEquipment(@PathParam("equipmentId") String equipmentId) {
		return events.aggregateEvents(equipmentId, new EquipmentEvent());
	}

	@POST
	public EquipmentEvent postEquipment(EquipmentEvent e) {
		events.addEvent(e);
		return e;
	}
}
