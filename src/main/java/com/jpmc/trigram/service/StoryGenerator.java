package com.jpmc.trigram.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.jpmc.trigram.model.WordPair;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public class StoryGenerator {
	
	private Set<List<String>> combinations;
	private Set<String> covered;
	
	public StoryGenerator() {
		this.combinations = new HashSet<>();
		this.covered = new HashSet<>();
	}
	
	/**
	 * This method triggers a recursive function by passing WordPair and initial value of story contents
	 * @param analyzedContent 
	 * @param int startingWordPair - this is the index of the WordPair that starts the story
	 */
	public void generateStory(WordPair pair, Map<WordPair, List<String>> analyzedContent) {
		log.info("Random word pair picked up to start generating output is : " + pair);
		List<String> combination = new ArrayList<>();
		combination.add(pair.getFirst());
		combination.add(pair.getSecond());
		log.debug("Inside generateStory - calling the recursive function to start creating the story");
		this.contentTraversal(pair, combination, analyzedContent);
	}

	/**
	 * This method looks up the map for list of possible words that follow current WordPair
	 * Words are picked up in list order. The word is included in the story and next WordPair is
	 * created and passed to recursion method.
	 * Each WordPair and index of list element combination is stored e.g. Hello_world_1
	 * If a particular element in the list for WordPair is already covered, we skip re-processing it.
	 * Exception to this is only when the list size is 1 for any WordPair.
	 * The break condition for recursion is that when for any WordPair, there is no following word.
	 * @param analyzedContent 
	 * @param WordPair pair - this is the WordPair at the beginning of story
	 * @param List<String> combination - this is the list which stores series of words in the story
	 */
	public void contentTraversal(WordPair pair, List<String> combination, Map<WordPair, List<String>> analyzedContent) {
		Optional<List<String>> list = Optional.ofNullable(analyzedContent.get(pair));
		if (!list.isPresent()) {
			log.debug("Inside contentTraversal - adding a valid story combination to the result");
			this.combinations.add(combination);
			return;
		}
		List<String> followingWords = list.get();
		for (int i = 0; i < followingWords.size(); i++) {
			String word = followingWords.get(i);
			if (!this.covered.contains(generateComb(pair, i)) || followingWords.size() == 1) {
				// creating a new list every time there is a branch
				List<String> newCombination = new ArrayList<>();
				newCombination.addAll(combination);
				newCombination.add(word);
				this.covered.add(generateComb(pair, i));
				contentTraversal(new WordPair(pair.getSecond(), word), newCombination, analyzedContent);
			}
		}

	}

	/**
	 * This method generates a unique string using words in the pair and index
	 * of the element in the word list
	 * Example - Hello_world_1
	 * @param WordPair - current WordPair
	 * @param int - index of element processed
	 * @return String - generated unique string
	 */
	public String generateComb(WordPair pair, int index) {
		return pair.getFirst() + "_" + pair.getSecond() + "_" + index;
	}
}
