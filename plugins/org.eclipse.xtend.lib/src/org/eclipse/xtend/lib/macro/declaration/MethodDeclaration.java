/*******************************************************************************
 * Copyright (c) 2013 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
/*******************************************************************************
 * Copyright (c) 2013 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.xtend.lib.macro.declaration;

import com.google.common.annotations.Beta;

/**
 * @author Sven Efftinge
 */
@Beta
public interface MethodDeclaration extends ExecutableDeclaration {
	
	/**
	 * @return whether this method is declared <code>final</code>
	 */
	boolean isFinal();

	/**
	 * @return whether this method is declared <code>abstract</code>
	 */
	boolean isAbstract();

	/**
	 * @return whether this method is declared <code>static</code>
	 */
	boolean isStatic();
	
	/**
	 * @return the return type of this method
	 */
	TypeReference getReturnType();
}