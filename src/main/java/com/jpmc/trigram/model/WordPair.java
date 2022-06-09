package com.jpmc.trigram.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
@AllArgsConstructor
public class WordPair {
	private String first;
	private String second;	
}
