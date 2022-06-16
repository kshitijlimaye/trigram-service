package com.jpmc.trigram.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jpmc.trigram.exception.InsufficientDataException;
import com.jpmc.trigram.model.WordPair;

public class TrigramAnalyzerTest {
	TrigramAnalyzer trigramAnalyzer;
	
	@BeforeEach
	void init() {
		this.trigramAnalyzer = new TrigramAnalyzer(new Random());
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
		WordPair pair1 = new WordPair("How","are");
		List<String> expected1 = Arrays.asList("you");
		WordPair pair2 = new WordPair("are","you");
		List<String> expected2 = Arrays.asList("");
		WordPair pair3 = new WordPair("you","");
		List<String> expected3 = null;
		assertEquals(expected1, trigramAnalyzer.getAnalyzedContent().get(pair1));
		assertEquals(expected2, trigramAnalyzer.getAnalyzedContent().get(pair2));
		assertEquals(expected3, trigramAnalyzer.getAnalyzedContent().get(pair3));
	}
	
	@Test
	void when3WordsInput_shouldAddEntryInStartWords() throws InsufficientDataException {
		trigramAnalyzer.analyzeContent("How are you");
		WordPair pair1 = new WordPair("How","are");
		WordPair pair2 = new WordPair("are","you");
		assertEquals(true, trigramAnalyzer.getStartWords().contains(pair1));
		assertEquals(true, trigramAnalyzer.getStartWords().contains(pair2));
	}
	
	
	@Test
	void whenExtraSpacesInData_shouldBeTrimmedToSingleSpace() {
		String result = trigramAnalyzer.preProcessContent("Hello   How are you?");
		assertEquals("Hello How are you?", result);
	}
}
