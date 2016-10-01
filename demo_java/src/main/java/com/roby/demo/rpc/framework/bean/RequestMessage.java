package com.roby.demo.rpc.framework.bean;


public class RequestMessage extends BaseMessage{
	
	private static final long serialVersionUID = 1L;
	
	private String methodName;  
     private  Class<?>[] parameterTypes ;  
     private  Object[] arguments ;
     
	public RequestMessage(String methodName, Class<?>[] parameterTypes,
			Object[] arguments) {
		super();
		this.methodName = methodName;
		this.parameterTypes = parameterTypes;
		this.arguments = arguments;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}
	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}
	public Object[] getArguments() {
		return arguments;
	}
	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}
	
}
