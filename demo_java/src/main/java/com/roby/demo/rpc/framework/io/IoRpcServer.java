package com.roby.demo.rpc.framework.io;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

import com.roby.demo.rpc.framework.bean.Config;
import com.roby.demo.rpc.service.RpcServer;
public class IoRpcServer implements RpcServer {
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
        ServerSocket server = new ServerSocket(config.getPort());  
        for(;;) {  
            try {  
                final Socket socket = server.accept();  
                new Thread(new Runnable() {  
                    public void run() {   
                        try {  
                            try {  
                                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());  
                                try {  
                                    String methodName = input.readUTF();  
                                    Class<?>[] parameterTypes = (Class<?>[])input.readObject();  
                                    Object[] arguments = (Object[])input.readObject();  
                                    ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());  
                                    try {  
                                        Method method = service.getClass().getMethod(methodName, parameterTypes);  
                                        Object result = method.invoke(service, arguments);  
                                        output.writeObject(result);  
                                    } catch (Throwable t) {  
                                        output.writeObject(t);  
                                    } finally {  
                                        output.close();  
                                    }  
                                } finally {  
                                    input.close();  
                                }  
                            } finally {  
                                socket.close();  
                            }  
                        } catch (Exception e) {  
                            e.printStackTrace();  
                        }  
                    }  
                }).start();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }  
  
}
