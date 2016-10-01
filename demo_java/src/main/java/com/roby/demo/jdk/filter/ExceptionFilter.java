package com.roby.demo.jdk.filter;
/**
 * 异常过滤器
 * @author jlf
 *
 */
public class ExceptionFilter extends AbstractFilter {
	private static final long serialVersionUID = 1L;
	@Override
	public Response invoke(Request request) {
		  // 先调用
		Response response = null;
        try {
        	 System.out.println("------------ExceptionFilter------before------");
            // 调用成功，或者调用返回已经封装的response
        	 response = getNext().invoke(request);
        	 response.setHead(request.getHead());
        	 System.out.println("------------ExceptionFilter------after------");
        } catch (Exception e) {
            // 此时的异常，是由于过滤器没有捕获导致的
            response.setException(e);
        }
		return response;
	}
}
