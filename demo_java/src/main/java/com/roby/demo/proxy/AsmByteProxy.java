package com.roby.demo.proxy;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


/**
 * ASM库动态字节码代理实现：
 * @author jlf
 *
 */
public class AsmByteProxy {
	public static TaskService createProxy(final  Object object) throws Exception{
		   ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS); 
		   //创建一个类
		   String className = TaskService.class.getName() +  "AsmProxy";  
		   String classPath = className.replace('.', '/');  
		   String interfacePath = TaskService.class.getName().replace('.', '/');  
		   classWriter.visit(Opcodes.V1_6, //java版本
				   Opcodes.ACC_PUBLIC, //類修飾符
				   classPath,//類路徑
				   null, 
				   "java/lang/Object", new String[] {interfacePath});  
	          
		   //创建构造函数   
		     MethodVisitor initVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);  
		     
	        initVisitor.visitCode();  
	        initVisitor.visitVarInsn(Opcodes.ALOAD, 0);  
	        initVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");  
	        initVisitor.visitInsn(Opcodes.RETURN);  
	        initVisitor.visitMaxs(0, 0);  
	        initVisitor.visitEnd();  
	        //创建echo函数   
	       MethodVisitor methodVisitor = classWriter.visitMethod(Opcodes.ACC_PUBLIC, "echo", "(Ljava/lang/String;)L", null, null);  
	       methodVisitor.visitCode();  
	       methodVisitor.visitVarInsn(Opcodes.ALOAD, 1);  
	       methodVisitor.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/lang/String", "echo", "(Ljava/lang/String;)L");  
	       methodVisitor.visitInsn(Opcodes.ARETURN);  
	       methodVisitor.visitMaxs(0, 0);  
	       methodVisitor.visitEnd();  
	       
	          
	       classWriter.visitEnd();  
	       byte[] code = classWriter.toByteArray();  
	       TaskService bytecodeProxy = (TaskService) new ByteArrayClassLoader().getClass(className, code).newInstance();  
	       return bytecodeProxy;  
	}
	
	  private static class ByteArrayClassLoader extends ClassLoader {  
	        public ByteArrayClassLoader() {  
	            super(ByteArrayClassLoader.class.getClassLoader());  
	        }  
	  
	        public synchronized Class<?> getClass(String name, byte[] code) {  
	            if (name == null) {  
	                throw new IllegalArgumentException("");  
	            }  
	            return defineClass(name, code, 0, code.length);  
	        }  
	  
	    }  
}
