package com.jpmc.trigram;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jpmc.trigram.exception.InsufficientDataException;
import com.jpmc.trigram.model.WordPair;

public class TrigramServiceTest {
	
	TrigramService trigramService;
	
	@BeforeEach
	void init() {
		trigramService = new TrigramService();
	}
	
	@Test
	void whenDefaultConstructorUsed_shouldInstantiateAllVariables() {
		assertNotNull(trigramService.getAnalyzedContent());
		assertNotNull(trigramService.getCombinations());
		assertNotNull(trigramService.getCovered());
		assertNotNull(trigramService.getRandom());
		assertNotNull(trigramService.getStartWords());
	}
	
	@Test
	void whenInputNull_shouldThrowInsufficientDataException() throws InsufficientDataException {
		assertThrows(InsufficientDataException.class, () -> trigramService.analyzeContent(null));
	}
	
	@Test
	void whenInputBlank_shouldThrowInsufficientDataException() throws InsufficientDataException {
		assertThrows(InsufficientDataException.class, () -> trigramService.analyzeContent(""));
	}
	
	@Test
	void whenInputContainsLessThan3Words_shouldThrowInsufficientDataException() throws InsufficientDataException {
		assertThrows(InsufficientDataException.class, () -> trigramService.analyzeContent("Hello world"));
	}
	
	@Test
	void whenExtraSpacesInData_shouldBeTrimmedToSingleSpace() {
		String result = trigramService.preProcessContent("Hello   How are you?");
		assertEquals("Hello How are you?", result);
	}
	
	@Test
	void whenGivenAPairAndIndex_shouldGenerateValidEntry() {
		WordPair pair = new WordPair("Hello","World");
		String result = trigramService.generateComb(pair, 0);
		assertEquals("Hello_World_0", result);
	}
	
	@Test
	void whenInputHas3Words_shouldProcessContents() throws InsufficientDataException {
		String input = "How are you";
		trigramService.analyzeContent(input);
		trigramService.generateStory(0);
		assertEquals("How are you ", String.join(" ",trigramService.getCombinations().iterator().next()));
	}
	
	@Test
	void whenSampleInput_shouldReturnExpectedOutput() throws InsufficientDataException {
		String input = "I wish I may I wish I might";
		trigramService.analyzeContent(input);
		trigramService.generateStory(2);
		String result = String.join(" ",trigramService.getCombinations().iterator().next());
		System.out.println(result);
		assertEquals(true,result.equals("I may I wish I may I wish I might ") || result.equals("I may I wish I might "));
	}
	
	@Test
	void whenReadFileMissing_shouldRaiseIOException() {
		assertThrows(IOException.class, () -> trigramService.readFile("MISSING_FILE_TO_READ.txt"));
	}
	
	@Test
	void whenReadFilePresent_shouldNotThrowIOException() throws IOException {
		assertDoesNotThrow(() -> trigramService.readFile(ClassLoader.getSystemResource("test_read.txt").getFile()));
	}
	
	@Test
	void whenReadFilePresent_shouldReturnStringBuilder() throws IOException {
		StringBuilder result = trigramService.readFile(ClassLoader.getSystemResource("test_read.txt").getFile());
		assertNotEquals(0,result.toString().length());
	}
	
	@Test
	void whenValidInputContents_shouldWriteToFile() throws IOException, InsufficientDataException {
		Set<List<String>> set = new HashSet<List<String>>();
		set.add(Arrays.asList("Hello","world","from","UK"));
		trigramService.setCombinations(set);
		trigramService.writeToFile("test_output.txt");
		
		StringBuilder result = trigramService.readFile("test_output.txt");
		assertNotEquals(0,result.toString().length());
	}
	
	@Test
	void whenGivenValidInput_processShouldNotThrowException() throws IOException, InsufficientDataException {
		assertDoesNotThrow(() -> trigramService.process(ClassLoader.getSystemResource("test_read.txt").getFile(), "test_output.txt"));
	}


}
