package com.roby.demo.mq.zeromq;
import java.util.ArrayList;
import java.util.List;

import org.zeromq.ZFrame;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public class Main {
	public static class Broker{
		private ZMQ.Socket front;
		private ZMQ.Socket back;
		private ZMQ.Context context;
		
		public Broker() {
			this.context = ZMQ.context(1);
			this.front = this.context.socket(ZMQ.ROUTER);
			this.back = this.context.socket(ZMQ.DEALER);
		}
		
		public void start() {
			new Thread(new Runnable(){
				public void run() {
					front.bind("ipc://front");
					back.bind("ipc://back");
					ZMQ.Poller poller = new ZMQ.Poller(2);
					ZMQ.PollItem fItem = new ZMQ.PollItem(front, ZMQ.Poller.POLLIN);
					ZMQ.PollItem bItem =new ZMQ.PollItem(back, ZMQ.Poller.POLLIN);
					poller.register(fItem);
					poller.register(bItem);
					while (!Thread.currentThread().isInterrupted()) {
						poller.poll();
						if (fItem.isReadable()) {
							ZMsg msg = ZMsg.recvMsg(fItem.getSocket());
							msg.send(back);
						}
						if (bItem.isReadable()) {
							ZMsg msg = ZMsg.recvMsg(bItem.getSocket());
							msg.send(front);
						}
					}
				}
				
			}).start();
		}
	}
	
	public static class Client {
		private ZMQ.Context context;
		private ZMQ.Socket socket;
		
		public Client() {
			this.context = ZMQ.context(1);
			this.socket = context.socket(ZMQ.DEALER);
		}
		
		public void start() {
			new Thread(new Runnable(){

				public void run() {
					// TODO Auto-generated method stub
					socket.connect("ipc://front");
						String now = "hello" ;
						socket.send(now.getBytes(), 0);
						String back = new String(socket.recv(0));
						System.out.println("recv response is : " + back);
					
				}
				
			}).start();
		}
	}
	
	
	public static class Worker {
		private ZMQ.Context context;
		private ZMQ.Socket socket;
		private List<ZMsg> requests;
		
		public Worker() {
			this.context = ZMQ.context(1);
			this.socket = context.socket(ZMQ.DEALER);
			this.requests = new ArrayList<ZMsg>(); 
		}
		
		public void start() {
			new Thread(new Runnable(){
				public void run() {
					// TODO Auto-generated method stub
					socket.connect("ipc://back");
						ZMsg msg = ZMsg.recvMsg(socket);
						requests.add(msg);
						ZMsg msg2 = requests.remove(0);
						ZFrame request = msg.removeLast();
						String now = new String(request.getData());
						System.out.println("recv request : " + now);
						ZFrame out = new ZFrame("world");
						msg2.addLast(out);
						msg2.send(socket);
						
					
				}
				
			}).start();
		}
	}
	
	public static void main(String args[]) {
		Worker worker = new Worker();
		Client client = new Client();
		Broker broker = new Broker();
		broker.start();
		worker.start();
		client.start();
	}
}