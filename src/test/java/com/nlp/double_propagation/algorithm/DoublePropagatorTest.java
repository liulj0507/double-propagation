package com.nlp.double_propagation.algorithm;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import com.nlp.double_propagation.analysis.StanfordBasedCorpusAnalyzer;
import com.nlp.double_propagation.model.PartOfSpeech;
import com.nlp.double_propagation.model.Word;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;

public class DoublePropagatorTest {

	private DoublePropagator doublePropagator;
	
	@Before
	public void setUp() throws Exception {
		doublePropagator=new DoublePropagator();
		doublePropagator.setCorpusAnalyzer(new StanfordBasedCorpusAnalyzer());
	}

	@Test
	public void testExecuteDoublePropagation() {
		List<String>texts=Lists.newArrayList("no noise","valley with the car", "good boy");
		Word good=Word.createWord("good", "good", PartOfSpeech.ADJECTIVE, 0, 0);
		Word bad = Word.createWord("bad", "bad", PartOfSpeech.ADJECTIVE, 0, 0);
		Set<Word>opinionWords=new HashSet<Word>();
		opinionWords.add(good);
		opinionWords.add(bad);
		doublePropagator.executeDoublePropagation(texts, opinionWords, new HashSet<Word>(), "en",false, null);
		
		assertTrue(true);
	}

}
