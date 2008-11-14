/*******************************************************************************
 * Copyright (c) 2008 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.crossref;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.parsetree.LeafNode;
import org.eclipse.xtext.service.ILanguageService;

/**
 * @author Heiko Behrens - Initial contribution and API
 * @author Sebastian Zarnekow
 */
public interface ILinkingService extends ILanguageService {

	/**
	 * Returns all EObjects referenced by the given link text in the given context.
	 */
	List<EObject> getLinkedObjects(EObject context, CrossReference ref, LeafNode text);

	/**
	 * Returns all possible link matches of a partially provided link text. This could be the starting of a link text or
	 * in case of nested namespaces the fragment.
	 */
	List<EObject> getLinkCandidates(EObject context, CrossReference ref, String textFragment);
}
