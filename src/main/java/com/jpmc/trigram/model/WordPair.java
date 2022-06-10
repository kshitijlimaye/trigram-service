package com.jpmc.trigram.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * This is a model class that holds a pair of words
 * @author Kshitij_Limaye
 *
 */
@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
public class WordPair {
	private String first;
	private String second;	
}
