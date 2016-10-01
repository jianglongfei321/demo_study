package com.roby.demo.rpc.framework.zeromq;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.zeromq.ZMQ;

import com.roby.demo.rpc.framework.bean.Config;
import com.roby.demo.rpc.framework.bean.RequestMessage;
import com.roby.demo.rpc.service.RpcClient;
import com.roby.demo.rpc.service.RpcServer;
public class ZmqRpcClient implements RpcClient{
   
    /** 
     * 引用服务 
     *  
     * @param <T> 接口泛型 
     * @param interfaceClass 接口类型 
     * @param host 服务器主机名 
     * @param port 服务器端口 
     * @return 远程服务 
     * @throws Exception 
     */  
    @SuppressWarnings("unchecked")  
    public  <T> T refer(final Class<T> interfaceClass, final Config config) throws Exception {  
    	  if (interfaceClass == null)  
              throw new IllegalArgumentException("Interface class == null");  
          if (! interfaceClass.isInterface())  
              throw new IllegalArgumentException("The " + interfaceClass.getName() + " must be interface class!");  
          if (config.getHost() == null )  
              throw new IllegalArgumentException("Host == null!");  
          if (config.getPort() <= 0 || config.getPort() > 65535)  
              throw new IllegalArgumentException("Invalid port " + config.getPort());  
        System.out.println("Get remote service " + interfaceClass.getName() + " from server " + config.getHost() + ":" + config.getPort());  
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass}, new ServiceHandler(config));  
    }  
}  
class ServiceHandler implements InvocationHandler{
	Config config;
    ServiceHandler(Config config) {
		this.config = config;
	}
    ServiceHandler() {
	}
	public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {  
            ZMQ.Context context = ZMQ.context(1);  //创建一个I/O线程的上下文  
            ZMQ.Socket socket = context.socket(ZMQ.REQ);   //创建一个request类型的socket，这里可以将其简单的理解为客户端，用于向response端发送数据  
            socket.connect("tcp://"+config.getHost()+":"+config.getPort());   //与response端建立连接  
            byte[] request =  ByteUtil.ObjectToByte(new RequestMessage(method.getName(),method.getParameterTypes(),arguments));
            socket.send(request);   //向reponse端发送数据  
            byte[] response = socket.recv();   
          return ByteUtil.ByteToObject(response);
       
	}
}
