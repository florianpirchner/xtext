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
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.linking.bug287988Test.Attribute;
import org.eclipse.xtext.linking.bug287988Test.Bug287988TestPackage;
import org.eclipse.xtext.linking.bug287988Test.Master;
import org.eclipse.xtext.linking.bug287988Test.Model;
import org.eclipse.xtext.linking.services.Bug287988TestLanguageGrammarAccess;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class Bug287988TestLanguageSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private Bug287988TestLanguageGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == Bug287988TestPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case Bug287988TestPackage.ATTRIBUTE:
				if (rule == grammarAccess.getActionAttributeRule()) {
					sequence_ActionAttribute(context, (Attribute) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getAttributeRule()) {
					sequence_Attribute(context, (Attribute) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getBaseAttributeRule()) {
					sequence_Attribute_BaseAttribute(context, (Attribute) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getCallMe2Rule()) {
					sequence_CallMe2(context, (Attribute) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getRuleCallAttribute2Rule()) {
					sequence_CallMe2_RuleCallAttribute2(context, (Attribute) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getCallMe3Rule()
						|| rule == grammarAccess.getCallMe4Rule()) {
					sequence_CallMe4(context, (Attribute) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getRuleCallAttribute3Rule()) {
					sequence_CallMe4_RuleCallAttribute3(context, (Attribute) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getCallMeRule()) {
					sequence_CallMe(context, (Attribute) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getRuleCallAttributeRule()) {
					sequence_CallMe_RuleCallAttribute(context, (Attribute) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getSimpleAttributeRule()) {
					sequence_SimpleAttribute(context, (Attribute) semanticObject); 
					return; 
				}
				else break;
			case Bug287988TestPackage.MASTER:
				if (rule == grammarAccess.getActionAttributeRule()) {
					sequence_ActionAttribute(context, (Master) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getBaseAttributeRule()) {
					sequence_BaseAttribute_Master(context, (Master) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getMasterRule()) {
					sequence_Master(context, (Master) semanticObject); 
					return; 
				}
				else break;
			case Bug287988TestPackage.MODEL:
				sequence_Model(context, (Model) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * <pre>
	 * Contexts:
	 *     ActionAttribute returns Attribute
	 *
	 * Constraint:
	 *     ((typeRef=[BaseAttribute|ID] | type=ID) name=ID)
	 * </pre>
	 */
	protected void sequence_ActionAttribute(ISerializationContext context, Attribute semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     ActionAttribute returns Master
	 *
	 * Constraint:
	 *     ((typeRef=[BaseAttribute|ID] | type=ID) name=ID)
	 * </pre>
	 */
	protected void sequence_ActionAttribute(ISerializationContext context, Master semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Attribute returns Attribute
	 *
	 * Constraint:
	 *     {Attribute}
	 * </pre>
	 */
	protected void sequence_Attribute(ISerializationContext context, Attribute semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     BaseAttribute returns Attribute
	 *
	 * Constraint:
	 *     ((typeRef=[BaseAttribute|ID] | type=ID) name=ID)
	 * </pre>
	 */
	protected void sequence_Attribute_BaseAttribute(ISerializationContext context, Attribute semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     BaseAttribute returns Master
	 *
	 * Constraint:
	 *     ((typeRef=[BaseAttribute|ID] | type=ID) name=ID)
	 * </pre>
	 */
	protected void sequence_BaseAttribute_Master(ISerializationContext context, Master semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     CallMe2 returns Attribute
	 *
	 * Constraint:
	 *     name=ID
	 * </pre>
	 */
	protected void sequence_CallMe2(ISerializationContext context, Attribute semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, Bug287988TestPackage.Literals.BASE_ATTRIBUTE__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, Bug287988TestPackage.Literals.BASE_ATTRIBUTE__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getCallMe2Access().getNameIDTerminalRuleCall_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     RuleCallAttribute2 returns Attribute
	 *
	 * Constraint:
	 *     (name=ID (typeRef=[BaseAttribute|ID] | type=ID))
	 * </pre>
	 */
	protected void sequence_CallMe2_RuleCallAttribute2(ISerializationContext context, Attribute semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     CallMe3 returns Attribute
	 *     CallMe4 returns Attribute
	 *
	 * Constraint:
	 *     name=ID
	 * </pre>
	 */
	protected void sequence_CallMe4(ISerializationContext context, Attribute semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, Bug287988TestPackage.Literals.BASE_ATTRIBUTE__NAME) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, Bug287988TestPackage.Literals.BASE_ATTRIBUTE__NAME));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getCallMe4Access().getNameIDTerminalRuleCall_0(), semanticObject.getName());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     RuleCallAttribute3 returns Attribute
	 *
	 * Constraint:
	 *     (name=ID (typeRef=[BaseAttribute|ID] | type=ID))
	 * </pre>
	 */
	protected void sequence_CallMe4_RuleCallAttribute3(ISerializationContext context, Attribute semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     CallMe returns Attribute
	 *
	 * Constraint:
	 *     (typeRef=[BaseAttribute|ID] | type=ID)
	 * </pre>
	 */
	protected void sequence_CallMe(ISerializationContext context, Attribute semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     RuleCallAttribute returns Attribute
	 *
	 * Constraint:
	 *     ((typeRef=[BaseAttribute|ID] | type=ID) name=ID)
	 * </pre>
	 */
	protected void sequence_CallMe_RuleCallAttribute(ISerializationContext context, Attribute semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Master returns Master
	 *
	 * Constraint:
	 *     {Master}
	 * </pre>
	 */
	protected void sequence_Master(ISerializationContext context, Master semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Model returns Model
	 *
	 * Constraint:
	 *     (
	 *         attributes+=BaseAttribute+ | 
	 *         attributes+=SimpleAttribute+ | 
	 *         attributes+=RuleCallAttribute+ | 
	 *         attributes+=RuleCallAttribute2+ | 
	 *         attributes+=RuleCallAttribute3+ | 
	 *         attributes+=ActionAttribute+
	 *     )
	 * </pre>
	 */
	protected void sequence_Model(ISerializationContext context, Model semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     SimpleAttribute returns Attribute
	 *
	 * Constraint:
	 *     ((typeRef=[BaseAttribute|ID] | type=ID) name=ID)
	 * </pre>
	 */
	protected void sequence_SimpleAttribute(ISerializationContext context, Attribute semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
