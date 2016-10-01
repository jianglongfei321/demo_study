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
public class JavassistByteProxyClasstTest {
	public static void main(String[] args) throws Exception {
		  ClassPool mPool = new ClassPool(true); 
		 //创建一个类
		  CtClass ctClass= mPool.makeClass("_JavassistByteProxy"+new Random().nextLong());
		 //添加要实现的接口
		  //ctClass.addInterface(mPool.get("testService"));
		  //添加構造函數
		  ctClass.addConstructor(CtNewConstructor.defaultConstructor(ctClass));
	      //添加方法
		  ctClass.addMethod(CtNewMethod.make("public String echo3(String msg){return \"echo \"+msg;}", ctClass));
		  Class pc=ctClass.toClass();
	     Object  object =  pc.newInstance();
	     System.out.println(object.getClass().getMethod("echo3", new Class[]{String.class}).invoke(object, new Object[]{"haha"}));  
		  
	}
}
