package com.roby.demo.spring.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 表示标注了特定注解的目标方法连接点。
 * 如@annotation(com.baobaotao.anno.NeedTest)表示任何标注了@NeedTest注解的目标类方法。
 * @author jlf
 */
@Component
@Aspect
public class JoinPointAspect {
	@Before("execution(* com.roby.demo.spring.aop.TestService.joinPoint(..))")
	public void before(JoinPoint joinPoint) {
		System.out.println("Join point kind : + joinPoint.getKindO "); 
		System.out.println("MSignature declaring type : "+ joinPoint.getSignature().getDeclaringTypeName ()); 
		System.out.println("Signature name :"+ joinPoint.getSignature().getName()); 
		System.out.println("Arguments : "+ Arrays.toString(joinPoint.getArgs())); 
		System.out.println("Target class"+ joinPoint.getTarget().getClass().getName()); 
		System.out.println("This class"+ joinPoint.getThis().getClass().getName()); 
	}
}
