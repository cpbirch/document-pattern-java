package com.tw.embeddoc;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.tw.embeddoc.events.ShipEquipmentEvent;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbedDocApplication extends Application<AppConfiguration> {
	public static final Logger LOGGER = LoggerFactory.getLogger(EmbedDocApplication.class);

	public static void main(final String[] args) throws Exception {
		new EmbedDocApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<AppConfiguration> bootstrap) {
		// nothing to do yet
	}

	@Override
	public void run(final AppConfiguration configuration, final Environment environment)
			throws Exception {

		LOGGER.info("Application name: {}", configuration.getAppName());
		final EquipmentResource resource = new EquipmentResource();
		final ShipEquipmentResource shipResource = new ShipEquipmentResource();
		final GeneralEquipmentResource ger = new GeneralEquipmentResource();
		environment.jersey().register(resource);
		environment.jersey().register(shipResource);
		environment.jersey().register(ger);
		environment.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

}
