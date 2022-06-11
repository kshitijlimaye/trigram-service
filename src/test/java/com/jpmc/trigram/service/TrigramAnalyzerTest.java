package com.jpmc.trigram.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jpmc.trigram.exception.InsufficientDataException;
import com.jpmc.trigram.model.WordPair;

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
	void when3WordsInput_shouldCreateValidTrigram() throws InsufficientDataException {
		trigramAnalyzer.analyzeContent("How are you");
		WordPair pair = new WordPair("How","are");
		List<String> expected = Arrays.asList("you");
		assertEquals(expected, trigramAnalyzer.getAnalyzedContent().get(pair));
	}
	
	@Test
	void when3WordsInput_shouldAddEntryInStartWords() throws InsufficientDataException {
		trigramAnalyzer.analyzeContent("How are you");
		WordPair pair = new WordPair("How","are");
		assertEquals(pair, trigramAnalyzer.getStartWords().get(0));
	}
	
	
	@Test
	void whenExtraSpacesInData_shouldBeTrimmedToSingleSpace() {
		String result = trigramAnalyzer.preProcessContent("Hello   How are you?");
		assertEquals("Hello How are you?", result);
	}
}
