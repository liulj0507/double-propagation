package com.nlp.double_propagation.multiwords;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import com.nlp.double_propagation.model.PartOfSpeech;
import com.nlp.double_propagation.model.Word;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class MultiwordCheckerTest {

	private MultiwordChecker multiwordChecker;
	
	@Before
	public void setUp() throws Exception {
		multiwordChecker=MultiwordChecker.createMultiworChecker(Lists.newArrayList("train station"));
	}

	@Test
	public void testCheckMultiwords() {
		
		Word word1=Word.createWord("the", "the", PartOfSpeech.OTHER, 0, 3);
		Word word2=Word.createWord("train", "train", PartOfSpeech.OTHER, 0, 3);
		Word word3=Word.createWord("station", "station", PartOfSpeech.OTHER, 0, 3);
		Word word4=Word.createWord("whatever", "whatever", PartOfSpeech.OTHER, 0, 3);
		List<Word>words=Lists.newArrayList(word1,word2,word3,word4);
		List<Word> resultingWords = multiwordChecker.checkMultiwords(words);
		for(Word word:resultingWords){
			System.out.println(word.getLemma()+"  --> "+word.getComposingWords());
		}
		assertTrue(true);
	}
}
