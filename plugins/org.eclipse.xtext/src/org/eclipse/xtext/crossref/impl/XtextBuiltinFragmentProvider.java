/*******************************************************************************
 * Copyright (c) 2008 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtext.crossref.impl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.crossref.IFragmentProvider;

/**
 * TODO: think about an efficient way to compute fragments.
 * @author Sven Efftinge - Initial contribution and API
 * @author Peter Friese
 * @author Sebastian Zarnekow
 */
public class XtextBuiltinFragmentProvider implements IFragmentProvider {

	public String getFragment(EObject obj) {
		return null;
	}

	public EObject getEObject(Resource resource, String fragment) {
		return null;
	}

}
