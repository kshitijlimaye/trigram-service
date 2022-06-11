package com.jpmc.trigram.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jpmc.trigram.exception.InsufficientDataException;

public class TrigramServiceTest {
	
	TrigramService trigramService;
	
	@BeforeEach
	void init() {
		trigramService = new TrigramService();
	}
	
	@AfterAll
	static void clearUpFiles() {
		File file1 = new File("test_output.txt");
		if(file1.exists()) file1.delete();
	}
	
	@Test
	void whenDefaultConstructorUsed_shouldInstantiateAllVariables() {
		assertNotNull(trigramService.getTrigramAnalyzer().getAnalyzedContent());
		assertNotNull(trigramService.getStoryGenerator().getCombinations());
		assertNotNull(trigramService.getStoryGenerator().getCovered());
		assertNotNull(trigramService.getTrigramAnalyzer().getRandom());
		assertNotNull(trigramService.getTrigramAnalyzer().getStartWords());
		assertNotNull(trigramService.getFileDataReader());
		assertNotNull(trigramService.getFileDataWriter());
	}
					
	@Test
	void whenGivenValidInput_processShouldNotThrowException() throws IOException, InsufficientDataException {
		assertDoesNotThrow(() -> trigramService.process(ClassLoader.getSystemResource("test_read.txt").getFile(), "test_output.txt"));
	}


}
