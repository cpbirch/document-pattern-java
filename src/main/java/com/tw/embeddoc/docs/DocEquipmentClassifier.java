package com.tw.embeddoc.docs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DocEquipmentClassifier {

	public static ObjectMapper mapper = new ObjectMapper();
	private static List<DocClass> classifiers = new ArrayList<>();
	static {
		classifiers.add(
				(equip) -> equip.contains("tonnage") ? Optional.of("This is a SHIP") : Optional.empty());
		classifiers.add(
				(equip) -> equip.contains("helicopters") ? Optional.of("This is a RESEARCH VESSEL") : Optional.empty());
		classifiers.add(
				(equip) -> equip.contains("warpfactor") ? Optional.of("This is a SPACE CRAFT") : Optional.empty());

	}

	public static List<String> classify(DocEquipmentEvent dee) {
		return classifiers.stream()
				.map(docClass -> docClass.optionalyClassify(dee))
				.filter(o -> o.isPresent())
				.map(o -> o.get())
				.collect(Collectors.toList());
	}

	private final List<String> classification;

	public DocEquipmentClassifier (DocEquipmentEvent dee) {
		this.classification = classify(dee);
	}

	public List<String> whatIsIt() {
		return classification;
	}

	public JsonNode whatIsItAsJson() {
		return mapper.convertValue(whatIsIt(), JsonNode.class);
	}
}
