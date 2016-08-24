package com.tw.embeddoc;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class AppConfiguration extends Configuration {

	@JsonProperty
	public int httpPort = 8080;

	@NotEmpty
	@JsonProperty
	public String appName = null;

	public String getAppName() {return appName; }
}
