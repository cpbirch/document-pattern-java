package com.tw.embeddoc;

import com.fasterxml.jackson.databind.JsonNode;
import com.tw.embeddoc.docs.DocEvent;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/equipment/doc")
@Produces(MediaType.APPLICATION_JSON)
public class GeneralEquipmentResource {
	public ImmutableDocStore<DocEvent> docs = new ImmutableDocStore<>();

	@GET
	@Path("/{equipmentId}")
	public JsonNode getEquipment(@PathParam("equipmentId") String equipmentId) {
		return docs.aggregateEvents(equipmentId, new DocEvent()).getAsJson();
	}

	@POST
	public JsonNode postEquipment(JsonNode json) {
		DocEvent e = DocEvent.createFromJson(json);
		docs.addEvent(e);
		return e.getAsJson();
	}

}
