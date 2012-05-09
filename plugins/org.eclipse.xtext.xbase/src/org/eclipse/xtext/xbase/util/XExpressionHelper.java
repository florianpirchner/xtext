/*******************************************************************************
 * Copyright (c) 2011 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.xbase.util;

import static org.eclipse.xtext.xbase.XbasePackage.*;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.common.types.JvmAnnotationReference;
import org.eclipse.xtext.common.types.JvmAnnotationTarget;
import org.eclipse.xtext.common.types.JvmExecutable;
import org.eclipse.xtext.common.types.JvmField;
import org.eclipse.xtext.common.types.JvmFormalParameter;
import org.eclipse.xtext.common.types.JvmIdentifiableElement;
import org.eclipse.xtext.common.types.JvmOperation;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.common.types.util.TypeConformanceComputer;
import org.eclipse.xtext.common.types.util.TypeReferences;
import org.eclipse.xtext.xbase.XAbstractFeatureCall;
import org.eclipse.xtext.xbase.XAssignment;
import org.eclipse.xtext.xbase.XBinaryOperation;
import org.eclipse.xtext.xbase.XClosure;
import org.eclipse.xtext.xbase.XConstructorCall;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.XFeatureCall;
import org.eclipse.xtext.xbase.XReturnExpression;
import org.eclipse.xtext.xbase.XThrowExpression;
import org.eclipse.xtext.xbase.XVariableDeclaration;
import org.eclipse.xtext.xbase.XbasePackage;
import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.typing.ITypeProvider;

import com.google.inject.Inject;

/**
 * @author Jan Koehnlein - Initial contribution and API
 */
public class XExpressionHelper {

	@Inject
	private ITypeProvider typeProvider;

	@Inject
	private TypeConformanceComputer conformanceComputer;

	@Inject
	private TypeReferences typeReferences;

	public boolean isLiteral(XExpression expr) {
		if (expr.eClass().getEPackage() != XbasePackage.eINSTANCE)
			return false;
		switch (expr.eClass().getClassifierID()) {
			case XCLOSURE:
			case XBOOLEAN_LITERAL:
			case XNUMBER_LITERAL:
			case XNULL_LITERAL:
			case XSTRING_LITERAL:
			case XTYPE_LITERAL:
				return true;
			default:
				return false;
		}
	}

	public boolean isFieldOrVariableReference(XExpression expr) {
		if (expr instanceof XFeatureCall) {
			XFeatureCall featureCall = (XFeatureCall) expr;
			final JvmIdentifiableElement feature = featureCall.getFeature();
			if (feature == null || feature.eIsProxy())
				return false;
			if (feature instanceof JvmField
				|| feature instanceof JvmFormalParameter
				|| feature instanceof XVariableDeclaration) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasSideEffects(XExpression expr) {
		final boolean result = internalHasSideEffectsRec(expr);
		return result;
	}
	
	protected boolean internalHasSideEffectsRec(EObject element) {
		if (element instanceof XClosure) {
			return false;
		}
		if (element instanceof XVariableDeclaration) {
			return true;
		}
		if (element instanceof XAssignment) {
			return true;
		}
		//TODO return and throw should not be treated as causing side-effects.
		// rather clients of hasSideEffects should also test for 'hasEarlyExit'.
		if (element instanceof XReturnExpression) {
			return true;
		}
		if (element instanceof XThrowExpression) {
			return true;
		}
		if (element instanceof XAbstractFeatureCall) {
			XAbstractFeatureCall featureCall = (XAbstractFeatureCall) element;
			if (featureCall.getFeature() instanceof JvmOperation) {
				JvmOperation jvmOperation = (JvmOperation) featureCall.getFeature();
				if (findPureAnnotation(jvmOperation) == null)
					return true;
			}
		}
		if (element instanceof XConstructorCall) {
			XConstructorCall constrCall = (XConstructorCall) element;
			if (findPureAnnotation(constrCall.getConstructor()) == null)
				return true;
		}
		for (EObject child : element.eContents()) {
			if (internalHasSideEffectsRec(child))
				return true;
		}
		return false;
	}

	public JvmAnnotationReference findInlineAnnotation(XAbstractFeatureCall featureCall) {
		final JvmIdentifiableElement feature = featureCall.getFeature();
		if (feature instanceof JvmAnnotationTarget) {
			return findAnnotation((JvmAnnotationTarget)feature, Inline.class.getName());
		}
		return null;
	}
	
	public JvmAnnotationReference findPureAnnotation(JvmExecutable featureCall) {
		return findAnnotation(featureCall, Pure.class.getName());
	}
	
	protected JvmAnnotationReference findAnnotation(JvmAnnotationTarget feature, String annotationType) {
		if (annotationType == null)
			throw new NullPointerException();
		List<JvmAnnotationReference> annotations = feature.getAnnotations();
		for (JvmAnnotationReference annotation : annotations) {
			if (annotationType.equals(annotation.getAnnotation().getQualifiedName())) {
				return annotation;
			}
		}
		return null;
	}

	public String getAndOperator() {
		return "&&";
	}

	public String getOrOperator() {
		return "||";
	}

	public boolean isShortCircuiteBooleanOperation(XAbstractFeatureCall featureCall) {
		if (featureCall instanceof XBinaryOperation) {
			XExpression leftOperand = ((XBinaryOperation) featureCall).getLeftOperand();
			final String op = featureCall.getConcreteSyntaxFeatureName();
			if (getAndOperator().equals(op) || getOrOperator().equals(op)) {
				JvmTypeReference booleanType = typeReferences.getTypeForName(Boolean.TYPE, leftOperand);
				JvmTypeReference leftOperandType = typeProvider.getType(leftOperand);
				JvmTypeReference operationReturnType = typeProvider.getType(featureCall);
				return (conformanceComputer.isConformant(booleanType, leftOperandType) && conformanceComputer
						.isConformant(booleanType, operationReturnType));
			}
		}
		return false;
	}

	public boolean isInlined(XAbstractFeatureCall call) {
		return findInlineAnnotation(call) != null;
	}
}
