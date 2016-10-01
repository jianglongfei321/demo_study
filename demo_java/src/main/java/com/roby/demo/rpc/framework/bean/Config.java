package com.roby.demo.rpc.framework.bean;

import java.io.Serializable;

public class Config implements Serializable{
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private int port =1234;
	    private String host = "127.0.0.1";
	    
		public Config() {
			super();
		}

		public Config(int port) {
			super();
			this.port = port;
		}

		public Config(int port, String host) {
			super();
			this.port = port;
			this.host = host;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}
}
