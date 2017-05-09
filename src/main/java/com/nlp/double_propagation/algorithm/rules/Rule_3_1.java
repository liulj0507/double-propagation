package com.nlp.double_propagation.algorithm.rules;

import com.google.common.collect.Lists;
import com.nlp.double_propagation.model.*;

import java.util.List;

/**
 * Extracts a target word linked to a seed target with a conjunction
 */
public class Rule_3_1 implements DoublePropagationRule{

	public static final String RULE_NAME=Rule_3_1.class.getName();
	
	private static final PartOfSpeech REQUIRED_TARGETWORD_POSTAG=PartOfSpeech.NOUN;
	public static final DoublePropagationWordType TRIGGERING_WORD_TYPE = DoublePropagationWordType.TARGET;
	
	@Override
	public List<RuleCheckResult> applyRule(Word word, IntraSentenceWordRelations intraSentenceWordRelations) {
		List<RuleCheckResult>ruleCheckResults=Lists.newArrayList();
		List<Word> candidateWords = intraSentenceWordRelations.getWordsGovernedByThis(word, RelationTypes.Conjunction);
		//as the conjunctions can work in the two directions, get also the other way around
		candidateWords.addAll(intraSentenceWordRelations.getWordsGoverningThis(word, RelationTypes.Conjunction));
		for(Word candidateWord:candidateWords){
			if(candidateWord.getPostag()==REQUIRED_TARGETWORD_POSTAG){
				RuleCheckResult ruleCheckResult = new RuleCheckResult();
				ruleCheckResult.setTriggeringWord(word);
				ruleCheckResult.setTriggeringWordType(TRIGGERING_WORD_TYPE);
				ruleCheckResult.setExtractedWord(candidateWord);
				ruleCheckResult.setExtractedWordType(DoublePropagationWordType.TARGET);
				ruleCheckResult.setRelType(RelationTypes.Conjunction);
				ruleCheckResult.setUsedRule(RULE_NAME);
				ruleCheckResults.add(ruleCheckResult);
			}
		}
		
		return ruleCheckResults;
	}

	
	
}
