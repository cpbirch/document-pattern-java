package com.tw.embeddoc.docs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

public class InsuranceCertificate extends DocEquipmentClassifier {

	final private JsonNodeFactory factory = JsonNodeFactory.instance;

	final private String resourceType;
	final private DocEquipmentEvent dee;

	public InsuranceCertificate(DocEquipmentEvent dee, String resourceType) {
		super(dee);
		this.dee = dee;
		this.resourceType = resourceType;
	}

	public boolean isValid() {
		return whatIsIt().stream()
				.filter(classification -> classification.matches(resourceType))
				.count() > 0;
	}


	public String getCertificate () {
		if (isValid()) {
			return "Certificate of " + resourceType + " Insurance";
		} else {
			return String.format("%s is not a %s! It cannot be insured.", dee.getValue("name"), resourceType);
		}
	}

	public JsonNode getAsJson() {
		JsonNode json = factory.textNode(getCertificate());
		return json;
	}
}
