/*******************************************************************************
 * Copyright (c) 2010, 2022 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.linking.serializer;

import com.google.inject.Inject;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.linking.services.Bug287988TestLanguageGrammarAccess;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AlternativeAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public class Bug287988TestLanguageSyntacticSequencer extends AbstractSyntacticSequencer {

	protected Bug287988TestLanguageGrammarAccess grammarAccess;
	protected AbstractElementAlias match_Model_ActionsKeyword_0_0_or_InlinedActionsKeyword_5_0_or_Rulecall2Keyword_3_0_or_Rulecall3Keyword_4_0_or_RulecallKeyword_2_0_or_SimpleKeyword_1_0;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (Bug287988TestLanguageGrammarAccess) access;
		match_Model_ActionsKeyword_0_0_or_InlinedActionsKeyword_5_0_or_Rulecall2Keyword_3_0_or_Rulecall3Keyword_4_0_or_RulecallKeyword_2_0_or_SimpleKeyword_1_0 = new AlternativeAlias(false, false, new TokenAlias(false, false, grammarAccess.getModelAccess().getActionsKeyword_0_0()), new TokenAlias(false, false, grammarAccess.getModelAccess().getInlinedActionsKeyword_5_0()), new TokenAlias(false, false, grammarAccess.getModelAccess().getRulecall2Keyword_3_0()), new TokenAlias(false, false, grammarAccess.getModelAccess().getRulecall3Keyword_4_0()), new TokenAlias(false, false, grammarAccess.getModelAccess().getRulecallKeyword_2_0()), new TokenAlias(false, false, grammarAccess.getModelAccess().getSimpleKeyword_1_0()));
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		return "";
	}
	
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if (match_Model_ActionsKeyword_0_0_or_InlinedActionsKeyword_5_0_or_Rulecall2Keyword_3_0_or_Rulecall3Keyword_4_0_or_RulecallKeyword_2_0_or_SimpleKeyword_1_0.equals(syntax))
				emit_Model_ActionsKeyword_0_0_or_InlinedActionsKeyword_5_0_or_Rulecall2Keyword_3_0_or_Rulecall3Keyword_4_0_or_RulecallKeyword_2_0_or_SimpleKeyword_1_0(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * <pre>
	 * Ambiguous syntax:
	 *     (
	  *         'actions' | 
	  *         'simple' | 
	  *         'rulecall' | 
	  *         'rulecall2' | 
	  *         'rulecall3' | 
	  *         'inlinedActions'
	  *     )
	 *
	 * This ambiguous syntax occurs at:
	 *     (rule start) (ambiguity) (rule start)
	 
	 * </pre>
	 */
	protected void emit_Model_ActionsKeyword_0_0_or_InlinedActionsKeyword_5_0_or_Rulecall2Keyword_3_0_or_Rulecall3Keyword_4_0_or_RulecallKeyword_2_0_or_SimpleKeyword_1_0(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
