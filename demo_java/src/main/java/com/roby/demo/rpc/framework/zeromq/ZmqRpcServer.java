package com.roby.demo.rpc.framework.zeromq;

import java.lang.reflect.Method;

import org.zeromq.ZMQ;

import com.roby.demo.rpc.framework.bean.Config;
import com.roby.demo.rpc.framework.bean.RequestMessage;
import com.roby.demo.rpc.service.RpcServer;
public class ZmqRpcServer implements RpcServer{
    /** 
     * 暴露服务 
     *  
     * @param service 服务实现 
     * @param port 服务端口 
     * @throws Exception 
     */  
    public  void export(final Object service, Config config) throws Exception {  
    	   if (service == null)  
               throw new IllegalArgumentException("service instance == null");  
           if (config.getPort() <= 0 || config.getPort() > 65535)  
               throw new IllegalArgumentException("Invalid port " + config.getPort());  
           System.out.println("Export service " + service.getClass().getName() + " on port " + config.getPort());  
        ZMQ.Context context = ZMQ.context(1);  //这个表示创建用于一个I/O线程的context  
        ZMQ.Socket socket = context.socket(ZMQ.REP);  //创建一个response类型的socket，他可以接收request发送过来的请求，其实可以将其简单的理解为服务端  
        socket.bind ("tcp://*:"+config.getPort());    //绑定端口  
        while (!Thread.currentThread().isInterrupted()) {  
            byte[] request = socket.recv();  //获取request发送过来的数据  
            RequestMessage invocation = (RequestMessage)ByteUtil.ByteToObject(request);
            System.out.println("receive : " + request);  
            try {  
                Method method = service.getClass().getMethod(invocation.getMethodName(), invocation.getParameterTypes());  
                Object result = method.invoke(service, invocation.getArguments());  
                socket.send(ByteUtil.ObjectToByte(result));
            } catch (Throwable t) {  
               
            } finally {  
            	
            }  
            String response = "world";  
            //向request端发送数据  ，必须要要request端返回数据，没有返回就又recv，将会出错，这里可以理解为强制要求走完整个request/response流程  
        }  
        
        socket.close();  //先关闭socket  
        context.term();  //关闭当前的上下文  
    }



    
}
