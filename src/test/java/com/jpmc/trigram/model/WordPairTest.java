package com.jpmc.trigram.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class WordPairTest {
	
	@Test
	void whenSameWords_shouldHaveSameHashCode() {
		WordPair pair1 = new WordPair("Hello","World");
		WordPair pair2 = new WordPair("Hello","World");
		assertEquals(pair1.hashCode(), pair2.hashCode());
	}
	
	@Test
	void whenDifferentOrderOfWords_shouldNotMatchOnHashcode() {
		WordPair pair1 = new WordPair("Hello","World");
		WordPair pair2 = new WordPair("World","Hello");
		assertNotEquals(pair1.hashCode(), pair2.hashCode());
	}
	
	@Test
	void whenSameWords_shouldMatchOnEquals() {
		WordPair pair1 = new WordPair("Hello","World");
		WordPair pair2 = new WordPair("Hello","World");
		assertEquals(true,pair1.equals(pair2));
	}
	
	@Test
	void whenDifferentOrderOfWords_shouldNotMatchOnEquals() {
		WordPair pair1 = new WordPair("Hello","World");
		WordPair pair2 = new WordPair("World","Hello");
		assertEquals(false,pair1.equals(pair2));
	}
	
	@Test
	void whenOneIsNull_shouldNotMatchOnEquals() {
		WordPair pair1 = null;
		WordPair pair2 = new WordPair("World","Hello");
		assertEquals(false,pair2.equals(pair1));
	}
}
