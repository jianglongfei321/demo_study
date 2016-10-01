package com.roby.demo.jdk.filter;

/**
 * 业务执行的方法
 * @author jlf
 *
 */
public class HandlerFilter extends AbstractFilter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Response invoke(Request request) {
		if(this.getNext()!=null){
			return this.getNext().invoke(request);
		}
		Response response = new Response();
		
		response.setHead("头信息");
		response.setObject("Handler 方法调用!");
		System.out.println("----------Handler 方法调用!-----------");
		return response;
		
		
	}

}
