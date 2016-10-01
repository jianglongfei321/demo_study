package com.roby.demo.jdk.filter;

/**
 * 验证过滤器
 * @author jlf
 *
 */
public class ValiationFilter extends AbstractFilter {
	private static final long serialVersionUID = 1L;
	@Override
	public Response invoke(Request request) {
		  // 先调用
		Response response = null;
        try {
        	 System.out.println("------------ValiateFilter-------before-----");
            // 调用成功，或者调用返回已经封装的response
        	 if(request.getHead()==null){
        		 throw new Exception("Head is null !");
        	 }
        } catch (Exception e) {
        	response = new Response();
            // 此时的异常，是由于过滤器没有捕获导致的
            response.setException(e);
            return response;
        }
        response =  getNext().invoke(request);
   	    System.out.println("------------ValiationFilter-------after-----");
        return response;
	}

}
