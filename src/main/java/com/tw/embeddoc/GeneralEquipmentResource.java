package com.tw.embeddoc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.tw.embeddoc.docs.DocEquipmentClassifier;
import com.tw.embeddoc.docs.DocEquipmentEvent;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/equipment/doc")
@Produces(MediaType.APPLICATION_JSON)
public class GeneralEquipmentResource {
	public static ImmutableDocStore<DocEquipmentEvent> docs = new ImmutableDocStore<>();
	private final JsonNodeFactory factory = JsonNodeFactory.instance;

	private final JsonSchema validator;

	public GeneralEquipmentResource(JsonSchema equipmentSchema) {
		validator = equipmentSchema;
	}

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
	@Produces("application/json")
	public Response postEquipment(JsonNode json) {
		try {
			ProcessingReport report = validator.validate(json, true);
			if (report.isSuccess()) {
				DocEquipmentEvent e = DocEquipmentEvent.createFromJson(json);
				docs.addEvent(e);
				return OK( e.getAsJson() );
			} else {
				return BAD( asJsonErrorMessage(report.toString()) );
			}
		} catch (ProcessingException e) {
			return SORRY_MY_FAULT( asJsonErrorMessage(e.getMessage()) );
		}
	}

	private static Response OK(JsonNode node) {
		return Response.ok(node).build();
	}

	private static Response BAD(JsonNode node) {
		return Response.status(Response.Status.BAD_REQUEST).entity(node).build();
	}

	private static Response SORRY_MY_FAULT(JsonNode node) {
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(node).build();
	}

	private JsonNode asJsonErrorMessage(String e) {
		return factory
//				.objectNode()
//				.set("error", factory.textNode(e));
				.textNode(e);
	}

}
