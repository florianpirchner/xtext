/*******************************************************************************
 * Copyright (c) 2011 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtend.core.tests.naming;

import org.eclipse.xtend.core.jvmmodel.IXtendJvmAssociations;
import org.eclipse.xtend.core.tests.AbstractXtendTestCase;
import org.eclipse.xtend.core.xtend.XtendClass;
import org.eclipse.xtend.core.xtend.XtendFile;
import org.eclipse.xtend.core.xtend.XtendFunction;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.conversion.XbaseQualifiedNameValueConverter;
import org.junit.Test;

import com.google.inject.Inject;

/**
 * @author Jan Koehnlein - Initial contribution and API
 */
public class NamingTest extends AbstractXtendTestCase {

	@Inject
	protected IQualifiedNameProvider nameProvider;

	@Inject
	protected IXtendJvmAssociations associations;
	
	@Inject
	protected XbaseQualifiedNameValueConverter converter;

	@Test public void testQualifiedNameProvider_0() throws Exception {
		XtendFile file = file("package foo class Bar {}");
		assertEquals(QualifiedName.create("foo", "Bar"), nameProvider.getFullyQualifiedName(file.getXtendClasses().get(0)));
		assertEquals(QualifiedName.create("foo", "Bar"),
				nameProvider.getFullyQualifiedName(associations.getInferredType(file.getXtendClasses().get(0))));
		assertEquals(QualifiedName.create("foo", "Bar"),
				nameProvider.getFullyQualifiedName(associations.getInferredConstructor(file.getXtendClasses().get(0))));
	}

	@Test public void testQualifiedNameProvider_1() throws Exception {
		XtendFile file = file("package foo class Bar { def baz() {this} }");
		XtendFunction function = (XtendFunction) file.getXtendClasses().get(0).getMembers().get(0);
		assertEquals(QualifiedName.create("foo", "Bar", "baz"), nameProvider.getFullyQualifiedName(function));
		assertEquals(QualifiedName.create("foo", "Bar", "baz"),
				nameProvider.getFullyQualifiedName(associations.getDirectlyInferredOperation(function)));
	}
	
	@Test public void testBug364508_toValue() throws Exception {
		String model = "package foo.create import foo.baz.create class Bar {}";
		XtendFile file = file(model);
		XtendClass xtendClass = file.getXtendClasses().get(0);
		assertEquals(QualifiedName.create("foo", "create", "Bar"), nameProvider.getFullyQualifiedName(xtendClass));
		assertEquals(QualifiedName.create("foo", "create", "Bar"),
				nameProvider.getFullyQualifiedName(associations.getInferredConstructor(xtendClass)));
	}
	
	@Test public void testBug364508_toString() throws Exception {
		String model = "foo.create";
		assertEquals(model, converter.toString(model));
	}

}
