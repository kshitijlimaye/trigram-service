package com.jpmc.trigram.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jpmc.trigram.exception.InsufficientDataException;
import com.jpmc.trigram.model.WordPair;
import com.jpmc.trigram.service.StoryGenerator;
import com.jpmc.trigram.service.TrigramAnalyzer;

public class StoryGeneratorTest {
	StoryGenerator storyGenerator;
	TrigramAnalyzer trigramAnalyzer;
	
	@BeforeEach
	void init() {
		storyGenerator = new StoryGenerator();
		trigramAnalyzer = new TrigramAnalyzer();
	}
	
	@Test
	void whenGivenAPairAndIndex_shouldGenerateValidEntry() {
		WordPair pair = new WordPair("Hello","World");
		String result = storyGenerator.generateComb(pair, 0);
		assertEquals("Hello_World_0", result);
	}
	
	@Test
	void whenInputHas3Words_shouldProcessContents() throws InsufficientDataException {
		String input = "How are you";
		WordPair pair = new WordPair("How", "are");
		trigramAnalyzer.analyzeContent(input);
		storyGenerator.generateStory(pair, trigramAnalyzer.getAnalyzedContent());
		assertEquals("How are you ", String.join(" ",storyGenerator.getCombinations().iterator().next()));
	}
	
	@Test
	void whenSampleInput_shouldReturnExpectedOutput() throws InsufficientDataException {
		String input = "I wish I may I wish I might";
		WordPair pair = new WordPair("I","may");
		trigramAnalyzer.analyzeContent(input);
		storyGenerator.generateStory(pair,trigramAnalyzer.getAnalyzedContent());
		String result = String.join(" ",storyGenerator.getCombinations().iterator().next());
		System.out.println(result);
		assertEquals(true,result.equals("I may I wish I may I wish I might ") || result.equals("I may I wish I might "));
	}
	
}
