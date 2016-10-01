package com.roby.demo.rpc.framework.io;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

import com.roby.demo.rpc.framework.bean.Config;
import com.roby.demo.rpc.service.RpcClient;
public class IoRpcClient implements RpcClient{
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
       Socket socket = new Socket(config.getHost(), config.getPort());  
       try {  
           ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());  
           try {  
               output.writeUTF(method.getName());  
               output.writeObject(method.getParameterTypes());
               output.writeObject(arguments);  
               ObjectInputStream input = new ObjectInputStream(socket.getInputStream());  
               try {  
                   Object result = input.readObject();  
                   if (result instanceof Throwable) {  
                       throw (Throwable) result;  
                   }  
                   return result;  
               } finally {  
                   input.close();  
               }  
           } finally {  
               output.close();  
           }  
       } finally {  
           socket.close();  
       }  
}
}
