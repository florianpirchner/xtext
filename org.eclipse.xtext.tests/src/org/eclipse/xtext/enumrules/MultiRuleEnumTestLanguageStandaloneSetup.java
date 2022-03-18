/*******************************************************************************
 * Copyright (c) 2010, 2022 itemis AG (http://www.itemis.eu) and others.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.xtext.enumrules;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class MultiRuleEnumTestLanguageStandaloneSetup extends MultiRuleEnumTestLanguageStandaloneSetupGenerated{

	public static void doSetup() {
		new MultiRuleEnumTestLanguageStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}

