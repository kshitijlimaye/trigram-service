package com.jpmc.trigram.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.jpmc.trigram.exception.InsufficientDataException;
import com.jpmc.trigram.model.WordPair;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * This class takes the raw data from input file, carries out pre-processing
 * and then builds a trigram map. It also returns a random word pair required to start
 * story content generation.
 * @author Kshitij_Limaye
 *
 */
@Getter
@Log4j2
public class TrigramAnalyzer {
	private Map<WordPair, List<String>> analyzedContent;
	private List<WordPair> startWords;
	private Random random;
	
	public TrigramAnalyzer() {
		this.analyzedContent = new HashMap<>();
		this.startWords = new ArrayList<>();
		this.random = new Random();
	}
	
	/**
	 * This method builds the trigram map where key is a WordPair and value
	 * is a list of words that follow this WordPair.
	 * A blank entry is added in list for last WordPair to prevent program running indefinitely.
	 * @param input - this is the pre-processed text
	 * @throws InsufficientDataException when there is insufficient data to process
	 */
	public void analyzeContent(String input) throws InsufficientDataException {
		log.debug("Inside analyzeContent - checking if there is enough content to perform trigram analysis");
		if (input == null)
			throw new InsufficientDataException("No content inside the file!");
		input = preProcessContent(input);
		if (input.isEmpty()) {
			throw new InsufficientDataException("No content inside the file!");
		}
		String[] arr = input.split("\\s+");
		log.debug("Inside analyzeContent - total number of words in the file is "+arr.length);
		if (arr.length <= 2) {
			throw new InsufficientDataException(
					"File contains only " + arr.length + " word(s) which is insufficient to initiate trigram analysis");
		}
		log.debug("Inside analyzeContent - starting the trigram analysis");
		for (int i = 0; i < arr.length - 1; i++) {
			WordPair pair = new WordPair(arr[i], arr[i + 1]);
			this.startWords.add(pair);
			List<String> followingWords = this.analyzedContent.get(pair);
			if (followingWords == null) {
				followingWords = new ArrayList<>();
			}
			// for ending word pair in the content, treat empty string as a following word
			if (i == arr.length - 2) {
				followingWords.add("");
			} else {
				followingWords.add(arr[i + 2]);
			}

			this.analyzedContent.put(pair, followingWords);
		}
		log.debug("Inside analyzeContent - trigram analysis completed");
	}
	
	/**
	 * This method replaces new line with single space
	 * and trims multiple spaces to single space
	 * @param raw - raw input text
	 * @return pre-processed text	
	 */
	public String preProcessContent(String raw) {
		String processed = "";
		processed = raw.replace(System.getProperty("line.separator"), " ");
		processed = processed.trim().replaceAll(" +", " ");
		return processed;
	}
	
	/**
	 * This method returns a random WordPair to start story creation
	 * @return a random Word pair
	 */
	public WordPair getRandomWord() {
		log.debug("Inside getRandomWord - picking up a random starting word pair");
		int startWordIndex = (this.random.nextInt() & Integer.MAX_VALUE) % (this.getStartWords().size());
		return this.getStartWords().get(startWordIndex);
	}
	
}
