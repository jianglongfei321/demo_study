package com.roby.demo.jdk.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤器链式结构
 * @author jianglongfei
 *
 */
public class FilterChain {
	/**
	 * 执行过滤器
	 */
    private Filter chain;
	public FilterChain(){}
	public FilterChain(List<AbstractFilter> filters,Filter lastFilter){
		chain = lastFilter;
		for(int i = filters.size()-1;i>=0;i--){
			  AbstractFilter filter = filters.get(i);
			  if (filter.getNext() != null) {
                  filter = (AbstractFilter) filter.clone(); // 复制生成一个新对象
              }
              filter.setNext(chain);
              chain = filter;
		}
	}
	public  List<AbstractFilter> buildFilters(){
		List<AbstractFilter> filters = new ArrayList<AbstractFilter>();
		filters.add(new ExceptionFilter());
		filters.add(new ValiationFilter());
		return filters;
	}
	public FilterChain create(Filter lastFilter){
		return new FilterChain(buildFilters(),lastFilter);
	}
    public Response invoke(Request requestMessage) {
        return getChain().invoke(requestMessage);
    }
    /**
     * 得到执行链
     *
     * @return chain
     */
    protected Filter getChain() {
        return chain;
    }
}
