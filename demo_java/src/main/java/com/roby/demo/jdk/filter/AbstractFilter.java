package com.roby.demo.jdk.filter;
import java.io.Serializable;
public abstract class AbstractFilter implements Filter, Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Filter next;
	
	public abstract Response invoke(Request request);
	  public Filter getNext() {
		return next;
	}
	public void setNext(Filter next) {
		this.next = next;
	}
	/**
     * 浅复制（字段值指向同一个对象）
     *
     * @return 过滤器对象
     */
    public Object clone() {
         try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
    }
}
