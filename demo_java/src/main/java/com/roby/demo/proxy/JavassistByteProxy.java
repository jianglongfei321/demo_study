package com.roby.demo.proxy;

import java.util.Random;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;


/**
 * Javassist库动态字节码代理实现：
 * @author jlf
 *
 */
public class JavassistByteProxy {
	public static TaskService createProxy() throws Exception{
		  ClassPool mPool = new ClassPool(true); 
		 //创建一个类
		  CtClass ctClass= mPool.makeClass(TaskService.class+"_JavassistByteProxy"+new Random().nextLong());
		 //添加要实现的接口
		  ctClass.addInterface(mPool.get(TaskService.class.getName()));
		  //添加構造函數
		  ctClass.addConstructor(CtNewConstructor.defaultConstructor(ctClass));
	      //添加方法
		  ctClass.addMethod(CtNewMethod.make("public String echo(String msg){return \"echo \"+msg;}", ctClass));
		  Class pc=ctClass.toClass();
	     return (TaskService)pc.newInstance();
		
	}
}
