package com.jpmc.trigram.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jpmc.trigram.exception.InsufficientDataException;
import com.jpmc.trigram.model.WordPair;

public class StoryGeneratorTest {
	StoryGenerator storyGenerator;
	
	@BeforeEach
	void init() {
		this.storyGenerator = new StoryGenerator();
	}
	
	@Test
	void whenGivenAPairAndIndex_shouldGenerateValidEntry() {
		WordPair pair = new WordPair("Hello","World");
		String result = storyGenerator.generateComb(pair, 0);
		assertEquals("Hello_World_0", result);
	}
	
	@Test
	void whenInputHas3Words_startWithFirstPair_shouldReturnSameOutput() throws InsufficientDataException {
		WordPair pair = new WordPair("How", "are");
		Map<WordPair, List<String>> map = new HashMap<>();
		map.put(pair, Arrays.asList("you"));
		map.put(new WordPair("are","you"), Arrays.asList(""));
		map.put(new WordPair("you",""), null);
		storyGenerator.generateStory(pair, map);
		assertEquals("How are you ", String.join(" ",storyGenerator.getCombinations().iterator().next()));
	}
	
	@Test
	void whenSampleInput_shouldReturnExpectedOutput() throws InsufficientDataException {
		WordPair pair = new WordPair("I", "may");
		Map<WordPair, List<String>> map = new HashMap<>();
		map.put(new WordPair("I","wish"), Arrays.asList("I","I"));
		map.put(new WordPair("wish","I"), Arrays.asList("may","might"));
		map.put(new WordPair("I","may"), Arrays.asList("I"));
		map.put(new WordPair("may","I"), Arrays.asList("wish"));
		map.put(new WordPair("I","might"), Arrays.asList(""));
		map.put(new WordPair("might",""), null);
		storyGenerator.generateStory(pair, map);
		String result = String.join(" ",storyGenerator.getCombinations().iterator().next());
		assertEquals(true,result.equals("I may I wish I may I wish I might "));
	}
	
	@Test
	void whenSampleInput_shouldReturnExpectedOutput1() throws InsufficientDataException {
		WordPair pair = new WordPair("I", "might");
		Map<WordPair, List<String>> map = new HashMap<>();
		map.put(new WordPair("I","wish"), Arrays.asList("I","I"));
		map.put(new WordPair("wish","I"), Arrays.asList("may","might"));
		map.put(new WordPair("I","may"), Arrays.asList("I"));
		map.put(new WordPair("may","I"), Arrays.asList("wish"));
		map.put(new WordPair("I","might"), Arrays.asList(""));
		map.put(new WordPair("might",""), null);
		storyGenerator.generateStory(pair, map);
		String result = String.join(" ",storyGenerator.getCombinations().iterator().next());
		assertEquals(true,result.equals("I might "));
	}
	
	@Test
	void whenSampleInput_shouldTrackTheCoveredCombinations() throws InsufficientDataException {
		WordPair pair = new WordPair("I", "may");
		Map<WordPair, List<String>> map = new HashMap<>();
		map.put(new WordPair("I","wish"), Arrays.asList("I","I"));
		map.put(new WordPair("wish","I"), Arrays.asList("may","might"));
		map.put(new WordPair("I","may"), Arrays.asList("I"));
		map.put(new WordPair("may","I"), Arrays.asList("wish"));
		map.put(new WordPair("I","might"), Arrays.asList(""));
		map.put(new WordPair("might",""), null);
		storyGenerator.generateStory(pair, map);
		assertEquals(true,this.storyGenerator.getCovered().contains("wish_I_0"));
		assertEquals(true,this.storyGenerator.getCovered().contains("wish_I_1"));
	}
	
}
