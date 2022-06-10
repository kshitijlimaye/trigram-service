package com.jpmc.trigram.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jpmc.trigram.exception.InsufficientDataException;
import com.jpmc.trigram.service.TrigramAnalyzer;

public class TrigramAnalyzerTest {
	TrigramAnalyzer trigramAnalyzer;
	
	@BeforeEach
	void init() {
		trigramAnalyzer = new TrigramAnalyzer();
	}
	
	@Test
	void whenInputNull_shouldThrowInsufficientDataException() throws InsufficientDataException {
		assertThrows(InsufficientDataException.class, () -> trigramAnalyzer.analyzeContent(null));
	}
	
	@Test
	void whenInputBlank_shouldThrowInsufficientDataException() throws InsufficientDataException {
		assertThrows(InsufficientDataException.class, () -> trigramAnalyzer.analyzeContent(""));
	}
	
	@Test
	void whenInputContainsLessThan3Words_shouldThrowInsufficientDataException() throws InsufficientDataException {
		assertThrows(InsufficientDataException.class, () -> trigramAnalyzer.analyzeContent("Hello world"));
	}
	
	
	@Test
	void whenExtraSpacesInData_shouldBeTrimmedToSingleSpace() {
		String result = trigramAnalyzer.preProcessContent("Hello   How are you?");
		assertEquals("Hello How are you?", result);
	}
}
