package com.nlp.double_propagation.analysis;

import com.nlp.double_propagation.model.PartOfSpeech;

import java.util.HashMap;

import static com.nlp.double_propagation.model.PartOfSpeech.*;


public class KafTagsetMapper extends AbstractTagsetMapper{

	@Override
	protected void loadMappings() {
		this.postagsMap=new HashMap<PartOfSpeech,String>();
		postagsMap.put(ADJECTIVE, "G");
		postagsMap.put(NOUN, "N");
		postagsMap.put(ADVERB, "A");
		postagsMap.put(VERB, "V");
		
		//MISSING DEPENDENCY TAGMAPPING...
		
	}

}
