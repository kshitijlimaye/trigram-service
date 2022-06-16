package com.jpmc.trigram.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.jpmc.trigram.exception.InsufficientDataException;
import com.jpmc.trigram.model.WordPair;

public class StoryGeneratorTest {

	StoryGenerator storyGenerator;
	
	@BeforeEach
	void init() {
		storyGenerator = new StoryGenerator();
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
	void whenSampleInput_resultShouldEndWithLastPairOfWords() throws InsufficientDataException {
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
		assertEquals(true,result.endsWith(" I might "));
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
		StoryGenerator mock = Mockito.spy(storyGenerator);
		doReturn("might").when(mock).getRandomNextWord(Arrays.asList("may","might"));
		mock.generateStory(pair, map);
		String result = String.join(" ",mock.getCombinations().iterator().next());
		assertEquals(true,result.equals("I may I wish I might "));
	}
	
	@Test
	void whenStartWordPairSameAsEndWordPair_shouldReturnStartWordAsOutput() throws InsufficientDataException {
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
		
}
