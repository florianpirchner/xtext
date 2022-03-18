/*******************************************************************************
 * Copyright (c) 2010, 2022 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.serializer.serializer;

import com.google.inject.Inject;
import java.util.Set;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.Action;
import org.eclipse.xtext.Parameter;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.serializer.ISerializationContext;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.assignmentFinderTest.AssignmentFinderTestPackage;
import org.eclipse.xtext.serializer.assignmentFinderTest.ContainmentRef;
import org.eclipse.xtext.serializer.assignmentFinderTest.ContainmentRefN;
import org.eclipse.xtext.serializer.assignmentFinderTest.CrossRef;
import org.eclipse.xtext.serializer.assignmentFinderTest.EnumBool;
import org.eclipse.xtext.serializer.assignmentFinderTest.EnumVal;
import org.eclipse.xtext.serializer.assignmentFinderTest.KeywordBool;
import org.eclipse.xtext.serializer.assignmentFinderTest.KeywordVal;
import org.eclipse.xtext.serializer.assignmentFinderTest.MixedBool;
import org.eclipse.xtext.serializer.assignmentFinderTest.MixedValue;
import org.eclipse.xtext.serializer.assignmentFinderTest.Model;
import org.eclipse.xtext.serializer.assignmentFinderTest.TerminalBool;
import org.eclipse.xtext.serializer.assignmentFinderTest.TerminalVal;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;
import org.eclipse.xtext.serializer.services.AssignmentFinderTestLanguageGrammarAccess;

@SuppressWarnings("all")
public class AssignmentFinderTestLanguageSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private AssignmentFinderTestLanguageGrammarAccess grammarAccess;
	
	@Override
	public void sequence(ISerializationContext context, EObject semanticObject) {
		EPackage epackage = semanticObject.eClass().getEPackage();
		ParserRule rule = context.getParserRule();
		Action action = context.getAssignedAction();
		Set<Parameter> parameters = context.getEnabledBooleanParameters();
		if (epackage == AssignmentFinderTestPackage.eINSTANCE)
			switch (semanticObject.eClass().getClassifierID()) {
			case AssignmentFinderTestPackage.CONTAINMENT_REF:
				sequence_ContainmentRef(context, (ContainmentRef) semanticObject); 
				return; 
			case AssignmentFinderTestPackage.CONTAINMENT_REF_N:
				if (rule == grammarAccess.getContainmentRef1Rule()) {
					sequence_ContainmentRef1(context, (ContainmentRefN) semanticObject); 
					return; 
				}
				else if (rule == grammarAccess.getContainmentRef2Rule()) {
					sequence_ContainmentRef2(context, (ContainmentRefN) semanticObject); 
					return; 
				}
				else break;
			case AssignmentFinderTestPackage.CROSS_REF:
				sequence_CrossRef(context, (CrossRef) semanticObject); 
				return; 
			case AssignmentFinderTestPackage.ENUM_BOOL:
				sequence_EnumBool(context, (EnumBool) semanticObject); 
				return; 
			case AssignmentFinderTestPackage.ENUM_VAL:
				sequence_EnumVal(context, (EnumVal) semanticObject); 
				return; 
			case AssignmentFinderTestPackage.KEYWORD_BOOL:
				sequence_KeywordBool(context, (KeywordBool) semanticObject); 
				return; 
			case AssignmentFinderTestPackage.KEYWORD_VAL:
				sequence_KeywordVal(context, (KeywordVal) semanticObject); 
				return; 
			case AssignmentFinderTestPackage.MIXED_BOOL:
				sequence_MixedBool(context, (MixedBool) semanticObject); 
				return; 
			case AssignmentFinderTestPackage.MIXED_VALUE:
				sequence_MixedValue(context, (MixedValue) semanticObject); 
				return; 
			case AssignmentFinderTestPackage.MODEL:
				sequence_Model(context, (Model) semanticObject); 
				return; 
			case AssignmentFinderTestPackage.TERMINAL_BOOL:
				sequence_TerminalBool(context, (TerminalBool) semanticObject); 
				return; 
			case AssignmentFinderTestPackage.TERMINAL_VAL:
				sequence_TerminalVal(context, (TerminalVal) semanticObject); 
				return; 
			}
		if (errorAcceptor != null)
			errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * <pre>
	 * Contexts:
	 *     ContainmentRef1 returns ContainmentRefN
	 *
	 * Constraint:
	 *     val1=ID
	 * </pre>
	 */
	protected void sequence_ContainmentRef1(ISerializationContext context, ContainmentRefN semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, AssignmentFinderTestPackage.Literals.CONTAINMENT_REF_N__VAL1) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, AssignmentFinderTestPackage.Literals.CONTAINMENT_REF_N__VAL1));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getContainmentRef1Access().getVal1IDTerminalRuleCall_1_0(), semanticObject.getVal1());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     ContainmentRef2 returns ContainmentRefN
	 *
	 * Constraint:
	 *     val2=ID
	 * </pre>
	 */
	protected void sequence_ContainmentRef2(ISerializationContext context, ContainmentRefN semanticObject) {
		if (errorAcceptor != null) {
			if (transientValues.isValueTransient(semanticObject, AssignmentFinderTestPackage.Literals.CONTAINMENT_REF_N__VAL2) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing(semanticObject, AssignmentFinderTestPackage.Literals.CONTAINMENT_REF_N__VAL2));
		}
		SequenceFeeder feeder = createSequencerFeeder(context, semanticObject);
		feeder.accept(grammarAccess.getContainmentRef2Access().getVal2IDTerminalRuleCall_1_0(), semanticObject.getVal2());
		feeder.finish();
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     ContainmentRef returns ContainmentRef
	 *
	 * Constraint:
	 *     (ctx=ContainmentRef1 | ctx=ContainmentRef2)
	 * </pre>
	 */
	protected void sequence_ContainmentRef(ISerializationContext context, ContainmentRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     CrossRef returns CrossRef
	 *
	 * Constraint:
	 *     ((name=Terminal1 | name=Terminal2) (crossRef=[CrossRef|Terminal1] | crossRef=[CrossRef|Terminal2]))
	 * </pre>
	 */
	protected void sequence_CrossRef(ISerializationContext context, CrossRef semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     EnumBool returns EnumBool
	 *
	 * Constraint:
	 *     (en?=Enum1 | en?=Enum2)
	 * </pre>
	 */
	protected void sequence_EnumBool(ISerializationContext context, EnumBool semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     EnumVal returns EnumVal
	 *
	 * Constraint:
	 *     (en=Enum1 | en=Enum2)
	 * </pre>
	 */
	protected void sequence_EnumVal(ISerializationContext context, EnumVal semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     KeywordBool returns KeywordBool
	 *
	 * Constraint:
	 *     (kw?='kw1' | kw?='kw2')
	 * </pre>
	 */
	protected void sequence_KeywordBool(ISerializationContext context, KeywordBool semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     KeywordVal returns KeywordVal
	 *
	 * Constraint:
	 *     (kw='kw1' | kw='kw2')
	 * </pre>
	 */
	protected void sequence_KeywordVal(ISerializationContext context, KeywordVal semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     MixedBool returns MixedBool
	 *
	 * Constraint:
	 *     (val?='kw1' | val=Boolean)
	 * </pre>
	 */
	protected void sequence_MixedBool(ISerializationContext context, MixedBool semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     MixedValue returns MixedValue
	 *
	 * Constraint:
	 *     (val=Enum1 | val=DatEnum)
	 * </pre>
	 */
	protected void sequence_MixedValue(ISerializationContext context, MixedValue semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     Model returns Model
	 *
	 * Constraint:
	 *     (
	 *         keywordVal=KeywordVal | 
	 *         terminalVal=TerminalVal | 
	 *         enumVal=EnumVal | 
	 *         keywordBool=KeywordBool | 
	 *         terminalBool=TerminalBool | 
	 *         enumBool=EnumBool | 
	 *         mixedBool=MixedBool | 
	 *         mixedValue=MixedValue | 
	 *         containmentRef=ContainmentRef | 
	 *         crossRef=CrossRef
	 *     )
	 * </pre>
	 */
	protected void sequence_Model(ISerializationContext context, Model semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     TerminalBool returns TerminalBool
	 *
	 * Constraint:
	 *     (term?=Terminal1 | term?=Terminal2 | term?='%foo')
	 * </pre>
	 */
	protected void sequence_TerminalBool(ISerializationContext context, TerminalBool semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
	/**
	 * <pre>
	 * Contexts:
	 *     TerminalVal returns TerminalVal
	 *
	 * Constraint:
	 *     (term=Terminal1 | term=Terminal2 | term='%foo')
	 * </pre>
	 */
	protected void sequence_TerminalVal(ISerializationContext context, TerminalVal semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
	
	
}
