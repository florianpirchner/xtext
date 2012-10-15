package org.eclipse.xtext.purexbase.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.purexbase.PureXbaseInjectorProvider;
import org.eclipse.xtext.util.IAcceptor;
import org.eclipse.xtext.xbase.compiler.CompilationTestHelper;
import org.eclipse.xtext.xbase.compiler.CompilationTestHelper.Result;
import org.eclipse.xtext.xbase.junit.evaluation.AbstractXbaseEvaluationTest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.inject.Inject;


@RunWith(XtextRunner.class)
@InjectWith(PureXbaseInjectorProvider.class)
@SuppressWarnings("restriction")
public class XbaseIntegrationTest extends AbstractXbaseEvaluationTest {
	
	@Inject private CompilationTestHelper helper;

	protected Object invokeXbaseExpression(String expression) throws Exception {
		final Object[] result = new Object[1];
		final InvocationTargetException[] exception = new InvocationTargetException[1];
		helper.compile(expression, new IAcceptor<CompilationTestHelper.Result>() {
			public void accept(Result t) {
				try {
					final Class<?> compiledClass = t.getCompiledClass();
					Object instance = compiledClass.newInstance();
					final Method method = compiledClass.getDeclaredMethod("myMethod");
					result[0] = method.invoke(instance);
				} catch (InvocationTargetException e) {
					exception[0] = e;
				} catch (Exception e) {
					e.printStackTrace();
					fail(e.toString());
				}
			}
		});
		if (exception[0] != null)
			throw exception[0];
		return result[0];
	}
	
//	@Override
//	@Test
//	public void testSwitchExpression_20() throws Exception {
//		// TODO Auto-generated method stub
//		super.testSwitchExpression_20();
//	}
//	
//	@Override
//	@Test
//	@Ignore("Disabled - PureXbase does not support sneaky throw")
//	public void testThrowExpression_01() throws Exception {
//		super.testThrowExpression_01();
//	}
	
	@Override
	@Test
	@Ignore
	public void testImplicitOneArgClosure_01() throws Exception {
		super.testImplicitOneArgClosure_01();
	}
	
	@Override
	@Test
	@Ignore
	public void testClosure_06_1() throws Exception {
		super.testClosure_06_1();
	}
	
	@Override
	@Test
	@Ignore
	public void testClosure_10() throws Exception {
		super.testClosure_10();
	}
	
	@Override
	@Test
	@Ignore
	public void testClosure_10_2() throws Exception {
		super.testClosure_10_2();
	}
	
	@Override
	@Test
	@Ignore
	public void testStaticMethod_02() throws Exception {
		super.testStaticMethod_02();
	}
	
}
