package br.jus.trt6.lib.common_web.action;

import java.lang.reflect.Method;

import javax.enterprise.context.Dependent;

@MyPartialBeanBinding
@Dependent
public class MyPartialBeanHandler implements java.lang.reflect.InvocationHandler {
	
	private int counter = 0;
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// generic handler logic
		counter++;
		return "PARTIAL BEAN " + counter;
	}
	
}