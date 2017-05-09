package com.nlp.double_propagation.algorithm.rules;

import com.google.common.collect.Lists;
import com.nlp.double_propagation.model.*;

import java.util.List;

/**
 * This rule extracts T-nsubj --> H <-- T-dobj
 * This "camera" has a great "lens".   (camera known word, lens extracted word)
 */
public class Rule_3_2 implements DoublePropagationRule{

	public static final String RULE_NAME=Rule_3_2.class.getName();
	
	private static final PartOfSpeech REQUIRED_TARGETWORD_POSTAG=PartOfSpeech.NOUN;
	public static final DoublePropagationWordType TRIGGERING_WORD_TYPE = DoublePropagationWordType.TARGET;
	
	@Override
	public List<RuleCheckResult> applyRule(Word word, IntraSentenceWordRelations intraSentenceWordRelations) {

		List<RuleCheckResult>ruleCheckResults=Lists.newArrayList();
		
		List<Word> intermediateWords = intraSentenceWordRelations.getWordsGoverningThis(word, RelationTypes.NominalSubject);
		for(Word intermediateWord:intermediateWords){
			List<Word> candidateWords = intraSentenceWordRelations.getWordsGovernedByThis(intermediateWord, RelationTypes.DirectObject);
			for(Word candidateWord:candidateWords){
				if(candidateWord.getPostag()==REQUIRED_TARGETWORD_POSTAG){
					RuleCheckResult ruleCheckResult = new RuleCheckResult();
					ruleCheckResult.setTriggeringWord(word);
					ruleCheckResult.setTriggeringWordType(TRIGGERING_WORD_TYPE);
					ruleCheckResult.setExtractedWord(candidateWord);
					ruleCheckResult.setExtractedWordType(DoublePropagationWordType.TARGET);
					ruleCheckResult.setRelType(RelationTypes.DirectObject);
					ruleCheckResult.setUsedRule(RULE_NAME);
					ruleCheckResults.add(ruleCheckResult);
				}
			}
		}
		return ruleCheckResults;
	}

	
	
}
