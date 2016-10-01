package com.roby.demo.rpc.framework.bean;

import io.netty.channel.Channel;


public class ResponseMessage  extends BaseMessage{
	
	private static final long serialVersionUID = 1L;

	private Object response;
	
	//private Channel channel;
	
	public  ResponseMessage() {
	}

	 
	 public ResponseMessage(long msgId,Object response,Channel channel) {
			super.msgId = msgId;
			this.response = response;
		//	this.channel = channel;
		}

	/*public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}*/

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}
	

}
