package com.jpmc.trigram.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jpmc.trigram.exception.InsufficientDataException;
import com.jpmc.trigram.io.FileDataReader;
import com.jpmc.trigram.io.FileDataWriter;
import com.jpmc.trigram.model.WordPair;

public class TrigramServiceTest {
	@InjectMocks
	TrigramService trigramService;
	@Mock
	TrigramAnalyzer trigramAnalyzer;
	@Mock
	StoryGenerator storyGenerator;
	@Mock
	FileDataReader fileDataReader;
	@Mock
	FileDataWriter fileDataWriter;
	
	@BeforeEach
	void init() throws IOException {
		MockitoAnnotations.initMocks(this);
		StringBuilder builder = new StringBuilder("Hello world from UK");
		Set<List<String>> result = new HashSet<>();
		List<String> combination = Arrays.asList("Hello","world","from","UK");
		result.add(combination);
		Map<WordPair, List<String>> map = new HashMap<>();
		map.put(new WordPair("Hello","world"), Arrays.asList("from"));
		map.put(new WordPair("world","from"), Arrays.asList("UK"));
		map.put(new WordPair("from","UK"), Arrays.asList(""));
		map.put(new WordPair("UK",""), null);
		when(fileDataReader.readFile(any())).thenReturn(builder);
		when(trigramAnalyzer.getRandomWord()).thenReturn(new WordPair("Hello","world"));
		when(trigramAnalyzer.getAnalyzedContent()).thenReturn(map);
		when(storyGenerator.getCombinations()).thenReturn(result);
		when(fileDataWriter.writeToFile(any(), any())).thenReturn(true);
	}

	@Test
	void whenGivenValidInput_processShouldNotThrowException() throws IOException, InsufficientDataException {
		assertDoesNotThrow(() -> trigramService.process("dummy1","dummy2"));
	}
	
	@Test
	void whenGivenValidInput_shouldEndWithSuccess() throws IOException, InsufficientDataException {
		assertEquals(true, trigramService.process("dummy1","dummy2"));
	}
	
}
