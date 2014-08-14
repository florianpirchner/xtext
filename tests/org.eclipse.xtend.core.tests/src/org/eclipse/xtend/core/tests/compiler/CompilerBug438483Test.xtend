/*******************************************************************************
 * Copyright (c) 2014 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtend.core.tests.compiler

import org.junit.Test

/**
 * @author Sebastian Zarnekow - Initial contribution and API
 */
class CompilerBug438483Test extends AbstractXtendCompilerTest {
	
	@Test
	def test_01() {
		assertCompilesTo('''
			import static extension TestExt.$
			class TestStdExt {
				def test() {
					val t = $(5,false)
				}
			}
			class TestExt {
				def static $(int a, boolean b) {
					new Pair<Integer, Boolean>(a,b)
				}
			}
		''', '''
			import org.eclipse.xtext.xbase.lib.Pair;
			
			@SuppressWarnings("all")
			public class TestStdExt {
			  public void test() {
			    final Pair<Integer, Boolean> t = TestExt.$(5, false);
			  }
			}
		''')
	}
	
}