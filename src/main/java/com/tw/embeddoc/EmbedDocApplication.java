package com.tw.embeddoc;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class EmbedDocApplication extends Application<AppConfiguration> {
	public static final Logger LOGGER = LoggerFactory.getLogger(EmbedDocApplication.class);
	private static final String PICKLIST_SCHEMA_JSON = "/general-equipment-resources.json";
	private JsonSchema jsonSchema;

	public static void main(final String[] args) throws Exception {
		new EmbedDocApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<AppConfiguration> bootstrap) {
		try {
			JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
			JsonNode genEquipSchema = JsonLoader.fromResource(PICKLIST_SCHEMA_JSON);
			jsonSchema = factory.getJsonSchema(genEquipSchema);
		} catch (IOException | ProcessingException e) {
			LOGGER.error("Shitted", e);
		}
	}

	@Override
	public void run(final AppConfiguration configuration, final Environment environment)
			throws Exception {

		LOGGER.info("Application name: {}", configuration.getAppName());
		final EquipmentResource resource = new EquipmentResource();
		final ShipEquipmentResource shipResource = new ShipEquipmentResource();
		final GeneralEquipmentResource ger = new GeneralEquipmentResource(jsonSchema);
		final InsureResource ir = new InsureResource();
		environment.jersey().register(resource);
		environment.jersey().register(shipResource);
		environment.jersey().register(ger);
		environment.jersey().register(ir);
		environment.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}


}
