package com.tw.embeddoc;

import com.fasterxml.jackson.databind.JsonNode;
import com.tw.embeddoc.docs.DocEquipmentClassifier;
import com.tw.embeddoc.docs.DocEquipmentEvent;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/equipment/doc")
@Produces(MediaType.APPLICATION_JSON)
public class GeneralEquipmentResource {
	public static ImmutableDocStore<DocEquipmentEvent> docs = new ImmutableDocStore<>();

	@GET
	@Path("/{equipmentId}")
	public JsonNode getEquipment(@PathParam("equipmentId") String equipmentId) {
		return docs.aggregateEvents(equipmentId, new DocEquipmentEvent()).getAsJson();
	}

	@GET
	@Path("/classify/{equipmentId}")
	public JsonNode classifyEquipment(@PathParam("equipmentId") String equipmentId) {
		DocEquipmentEvent equipment = docs.aggregateEvents(equipmentId, new DocEquipmentEvent());
		DocEquipmentClassifier dec = new DocEquipmentClassifier(equipment);
		return dec.whatIsItAsJson();
	}

	@POST
	public JsonNode postEquipment(JsonNode json) {
		DocEquipmentEvent e = DocEquipmentEvent.createFromJson(json);
		docs.addEvent(e);
		return e.getAsJson();
	}

}
