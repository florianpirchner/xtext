/*******************************************************************************
 * Copyright (c) 2010, 2022 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.linking;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class AbstractIgnoreCaseLinkingTestLanguageStandaloneSetup extends AbstractIgnoreCaseLinkingTestLanguageStandaloneSetupGenerated{

	public static void doSetup() {
		new AbstractIgnoreCaseLinkingTestLanguageStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

