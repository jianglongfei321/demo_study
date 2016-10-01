package com.roby.demo.rpc.framework.bean;

import java.io.Serializable;

public class BaseMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 long msgId;

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}
	

}
