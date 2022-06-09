package com.jpmc.trigram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import com.jpmc.trigram.exception.InsufficientDataException;
import com.jpmc.trigram.model.WordPair;

import lombok.Getter;
import lombok.Setter;

/**
 * Trigram Service - this service generates a story based on the trigram
 * analysis of the input text content. Algorithm uses random selection
 * technique, hence for same input content, you may get different result every time
 * 
 * Input contents need to be provided through a "input.txt" file 
 * Output story is generated into "output.txt" file
 * 
 * @author Kshitij Limaye
 *
 */
@Getter
@Setter
public class TrigramService {

	private static final Logger LOGGER = Logger.getLogger(TrigramService.class.getName());

	private Map<WordPair, List<String>> analyzedContent;
	private List<WordPair> startWords;
	private Set<List<String>> combinations;
	private Set<String> covered;
	private Random random;

	public TrigramService() {
		analyzedContent = new LinkedHashMap<>();
		startWords = new ArrayList<>();
		combinations = new HashSet<>();
		covered = new HashSet<>();
		random = new Random();
	}

	/**
	 * @return StringBuilder which holds all the contents of the file
	 * @throws IOException if program fails to read the file
	 * This method reads the input file line by line into a StringBuilder
	 */
	public StringBuilder readFile(String inputFile) throws IOException {
		StringBuilder builder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
			String line = br.readLine();
			while (line != null) {
				builder.append(line + " ");
				line = br.readLine();
			}
		} catch (IOException e) {
			LOGGER.severe("Could not read the file!");
			throw e;
		}
		return builder;
	}

	/**
	 * @param String - this is the pre-processed text
	 * @throws InsufficientDataException when there is insufficient data to process
	 * This method builds the trigram map where key is a WordPair and value
	 * is a list of words that follow this WordPair.
	 * A blank entry is added in list for last WordPair to prevent program running indefinitely.
	 * To introduce random selection, the list for each WordPair is shuffled.
	 */
	public void processContent(String input) throws InsufficientDataException {
		if (input == null)
			throw new InsufficientDataException("No content inside the file!");
		input = preProcessContent(input);
		if (input.isEmpty()) {
			throw new InsufficientDataException("No content inside the file!");
		}
		String[] arr = input.split("\\s+");
		if (arr.length <= 2) {
			throw new InsufficientDataException(
					"File contains only " + arr.length + " word(s) which is insufficient to initiate trigram analysis");
		}
		for (int i = 0; i < arr.length - 1; i++) {
			WordPair pair = new WordPair(arr[i], arr[i + 1]);
			startWords.add(pair);
			List<String> followingWords = analyzedContent.get(pair);
			if (followingWords == null) {
				followingWords = new LinkedList<>();
			}
			// for ending word pair in the content, treat empty string as a following word
			if (i == arr.length - 2) {
				followingWords.add("");
			} else {
				followingWords.add(arr[i + 2]);
			}

			Collections.shuffle(followingWords);
			analyzedContent.put(pair, followingWords);
		}
	}

	/**
	 * @param String - input text
	 * @return String - cleaned text	
	 * This method replaces new line with single space
	 * and trims multiple spaces to single space
	 */
	public String preProcessContent(String raw) {
		String processed = "";
		processed = raw.replace(System.getProperty("line.separator"), " ");
		processed = processed.trim().replaceAll(" +", " ");
		return processed;
	}

	/**
	 * @param int - this is the index of the WordPair that starts the story
	 * This method triggers a recursive function by passing WordPair and initial value of story contents
	 */
	public void generateCombinations(int startingWordPair) {
		WordPair pair = startWords.get(startingWordPair);
		List<String> combination = new ArrayList<>();
		combination.add(pair.getFirst());
		combination.add(pair.getSecond());
		recursion(pair, combination);
	}

	/**
	 * @param WordPair - this is the WordPair at the beginning of story
	 * @param List<String> - this is the list which stores series of words in the story
	 * This method looks up the map for list of possible words that follow current WordPair
	 * Words are picked up in list order. The word is included in the story and next WordPair is
	 * created and passed to recursion method.
	 * Each WordPair and index of list element combination is stored e.g. Hello_world_1
	 * If a particular element in the list for WordPair is already covered, we skip re-processing it.
	 * Exception to this is only when the list size is 1 for any WordPair.
	 * The break condition for recursion is that when for any WordPair, there is no following word.
	 */
	public void recursion(WordPair pair, List<String> combination) {
		Optional<List<String>> list = Optional.ofNullable(analyzedContent.get(pair));
		if (!list.isPresent()) {
			combinations.add(combination);
			return;
		}
		List<String> followingWords = list.get();
		for (int i = 0; i < followingWords.size(); i++) {
			String word = followingWords.get(i);
			if (!covered.contains(generateComb(pair, i)) || followingWords.size() == 1) {
				// creating a new list every time there is a branch
				List<String> newCombination = new ArrayList<>();
				newCombination.addAll(combination);
				newCombination.add(word);
				covered.add(generateComb(pair, i));
				recursion(new WordPair(pair.getSecond(), word), newCombination);
			}
		}

	}

	/**
	 * @param WordPair - current WordPair
	 * @param int - index of element processed
	 * @return String - generate the unique string using words in the pair and index
	 * Example - Hello_world_1
	 */
	public String generateComb(WordPair pair, int index) {
		return pair.getFirst() + "_" + pair.getSecond() + "_" + index;
	}

	/**
	 * @throws IOException if program fails to write to file
	 * This method writes the output story into the file
	 */
	public void writeToFile(String outputFile) throws IOException {
		try (BufferedWriter br = new BufferedWriter(new FileWriter(outputFile))) {
			for (List<String> list : this.getCombinations()) {
				br.write(String.join(" ", list));
			}
		} catch (IOException e) {
			LOGGER.severe("Could not write to the file!");
			throw e;
		}
	}

	/**
	 * @throws IOException when there is failure during read/write of the file
	 * @throws InsufficientDataException when the input file contents are insufficient for trigram analysis
	 * This is manager method that sequentially triggers file read, content processing, 
	 * story combination generation and file write methods.
	 */
	public void process(String inputFile, String outputFile) throws IOException, InsufficientDataException {
		LOGGER.info("Starting the trigram program");	
		StringBuilder sb = this.readFile(inputFile);		
		LOGGER.info("File contents read");
		this.processContent(sb.toString());
		LOGGER.info("Trigram analysis completed for the file contents");
		// generate random index to pick up random word
		int startWordIndex = (this.random.nextInt() & Integer.MAX_VALUE) % (this.getStartWords().size());
		LOGGER.info("Random word pair picked up to start generating output is : " + this.getStartWords().get(startWordIndex));
		this.generateCombinations(startWordIndex);
		LOGGER.info("A new story using trigrams has been created");
		this.writeToFile(outputFile);
		LOGGER.info("The output has been written into the file");
	}

	public static void main(String[] args) {
		TrigramService obj = new TrigramService();
		try {
			obj.process("input.txt","output.txt");
		} catch (IOException | InsufficientDataException e) {
			LOGGER.severe(e.getMessage());
		}
	}
}