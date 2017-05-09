package com.nlp.double_propagation.algorithm.rules;

import com.google.common.collect.Lists;
import com.nlp.double_propagation.model.*;

import java.util.List;

/**
 * Extracts the (noun) term modified by the seed opinion word (e.g. good screen)
 * In the cases of "The screen is big" the relation is nsubj(big,screen) so this
 * is also handled here to extract "screen" as a dependent in the nsubj relation
 */

public class Rule_5_1 implements DoublePropagationRule {

	public static final String RULE_NAME = Rule_5_1.class.getName();
	public static final DoublePropagationWordType TRIGGERING_WORD_TYPE = DoublePropagationWordType.TARGET;

	private static final PartOfSpeech REQUIRED_TARGETWORD_POSTAG = PartOfSpeech.NOUN;
	private static final RelationTypes REQUIRED_RELATION_TYPE_1 = RelationTypes.PartWhole;

	@Override
	public List<RuleCheckResult> applyRule(Word word, IntraSentenceWordRelations intraSentenceWordRelations) {
		List<RuleCheckResult> ruleCheckResults = Lists.newArrayList();

		List<Word> candidateWords = intraSentenceWordRelations.getWordsGovernedByThis(word, REQUIRED_RELATION_TYPE_1);
		for (Word candidateWord : candidateWords) {
			if (candidateWord.getPostag() == REQUIRED_TARGETWORD_POSTAG) {
				RuleCheckResult ruleCheckResult = new RuleCheckResult();
				ruleCheckResult.setTriggeringWord(word);
				ruleCheckResult.setTriggeringWordType(TRIGGERING_WORD_TYPE);
				ruleCheckResult.setExtractedWord(candidateWord);
				ruleCheckResult.setExtractedWordType(DoublePropagationWordType.TARGET);
				ruleCheckResult.setRelType(REQUIRED_RELATION_TYPE_1);
				ruleCheckResult.setUsedRule(RULE_NAME);
				ruleCheckResults.add(ruleCheckResult);
			}
		}

		return ruleCheckResults;
	}

}
