package com.jpmc.trigram.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.jpmc.trigram.model.WordPair;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * This class generates contents for story using starting word pair
 * and trigram analyzed content
 * @author Kshitij_Limaye
 *
 */
@Getter
@Log4j2
public class StoryGenerator {

	private Set<List<String>> combinations;
	private Random random;

	public StoryGenerator() {
		this.combinations = new HashSet<>();
		this.random = new Random();
	}

	/**
	 * This method triggers a recursive function by passing WordPair and initial value of story contents
	 * @param pair - starting WordPair
	 * @param analyzedContent - trigram map
	 */
	public void generateStory(WordPair pair, Map<WordPair, List<String>> analyzedContent) {
		log.info("Random word pair picked up to start generating output is : " + pair);
		List<String> combination = new ArrayList<>();
		combination.add(pair.getFirst());
		combination.add(pair.getSecond());
		log.debug("Inside generateStory - calling the function to start creating the story");
		this.contentTraversal(pair, combination, analyzedContent);
	}

	/**
	 * This method looks up the map for list of possible words that follow current WordPair.
	 * Words are picked up in random order. The word is included in the story and next WordPair is
	 * created.
	 * The loop continues till we reach the end of the story i.e. last word pair in original story.
	 * @param analyzedContent - the trigram map received from TrigramAnalyzer
	 * @param pair - this is the WordPair at the beginning of story
	 * @param combination - this is the list which stores series of words in the story
	 */
	public void contentTraversal(WordPair pair, List<String> combination, Map<WordPair, List<String>> analyzedContent) {
		List<String> followingWords = analyzedContent.get(pair);

		while(followingWords!=null && !followingWords.isEmpty()) {
			String word = getRandomNextWord(followingWords);
			combination.add(word);			
			pair = new WordPair(pair.getSecond(), word);
			followingWords = analyzedContent.get(pair);
		}
		this.combinations.add(combination);
	}

	/**
	 * This method returns a random word from the list of following words
	 * @param followingWords - list of words that follow a Word pair
	 * @return a random word from the list
	 */
	public String getRandomNextWord(List<String> followingWords) {
		int index = 0;
		if(followingWords.size()>1) {
			index = (this.random.nextInt() & Integer.MAX_VALUE) % (followingWords.size());
		}
		return followingWords.get(index);
	}
}
