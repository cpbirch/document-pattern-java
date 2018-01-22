package com.tw.embeddoc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tw.embeddoc.docs.DocEquipmentEvent;
import com.tw.embeddoc.docs.InsuranceCertificate;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InsureEquipmentTest {
	ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void shouldInsureAShip() throws Exception {
		// Given
		JsonNode json = mapper.readTree("{\"equipmentId\":\"1\", \"name\":\"Boaty\", \"tonnage\":\"100\"}");
		DocEquipmentEvent dee = DocEquipmentEvent.createFromJson(json);
		String type = "SHIP";

		// When
		InsuranceCertificate cert = new InsuranceCertificate(dee, type);

		// Then
		assertTrue(cert.isValid());

	}

	@Test
	public void shouldNOTInsureAShip() throws Exception {
		// Given
		JsonNode json = mapper.readTree("{\"equipmentId\":\"1\", \"name\":\"Boaty\", \"warpfactor\":\"2\"}");
		DocEquipmentEvent dee = DocEquipmentEvent.createFromJson(json);
		String type = "SHIP";

		// When
		InsuranceCertificate cert = new InsuranceCertificate(dee, type);

		// Then
		assertFalse(cert.isValid());
		assertEquals("Boaty is not a SHIP! It cannot be insured.", cert.getCertificate());
	}

}