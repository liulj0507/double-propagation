package com.nlp.double_propagation.algorithm.rules;

import com.google.common.collect.Lists;
import com.nlp.double_propagation.model.*;

import java.util.List;

/**
 * Opinion joined with a conjunction
 * e.g. the phone is small and nice
 */
public class Rule_4_1 implements DoublePropagationRule{

	public static final String RULE_NAME=Rule_4_1.class.getName();
	
	private static final PartOfSpeech REQUIRED_TARGETWORD_POSTAG=PartOfSpeech.ADJECTIVE;
	public static final DoublePropagationWordType TRIGGERING_WORD_TYPE = DoublePropagationWordType.OPINION;
	
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
				ruleCheckResult.setExtractedWordType(DoublePropagationWordType.OPINION);
				ruleCheckResult.setRelType(RelationTypes.Conjunction);
				ruleCheckResult.setUsedRule(RULE_NAME);
				ruleCheckResults.add(ruleCheckResult);
			}
		}
		
		return ruleCheckResults;
	}
	
	
}
