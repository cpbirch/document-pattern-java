package com.tw.embeddoc;

import com.fasterxml.jackson.databind.JsonNode;
import com.tw.embeddoc.docs.DocEquipmentEvent;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/insure")
@Produces(MediaType.APPLICATION_JSON)
public class InsureResource {

	@POST
	@Path("ship")
	public JsonNode insureShip(JsonNode json) {
		DocEquipmentEvent e = DocEquipmentEvent.createFromJson(json);
		return e.getAsJson();
	}

	@POST
	@Path("spacecraft")
	public JsonNode insureSpacecraft(JsonNode json) {
		DocEquipmentEvent e = DocEquipmentEvent.createFromJson(json);
		return e.getAsJson();
	}


}
