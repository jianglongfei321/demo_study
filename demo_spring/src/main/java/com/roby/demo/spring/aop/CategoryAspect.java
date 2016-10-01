package com.roby.demo.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 重用切入点定义 pointCut
 * execution()
 * 方法匹配模式串表示满足某一匹配模式的所有目标类方法连接点。如execution(* greetTo(..))表示所有目标类中的greetTo()方法。
 * spring aop通知(advice)分成五类： 
 * 前置通知[Before advice]：在连接点前面执行，前置通知不会影响连接点的执行，除非此处抛出异常。 
 * 正常返回通知[After returning advice]：在连接点正常执行完成后执行，如果连接点抛出异常，则不会执行。 
 * 异常返回通知[After throwing advice]：在连接点抛出异常后执行。 
 * 返回通知[After (finally) advice]：在连接点执行完成后执行，不管是正常执行完成，还是抛出异常，都会执行返回通知中的内容。 
 * 环绕通知[Around advice]：环绕通知围绕在连接点前后，比如一个方法调用的前后。这是最强大的通知类型，能在方法调用前后自定义一些操作。
 * 环绕通知还需要负责决定是继续处理join point(调用ProceedingJoinPoint的proceed方法)还是中断执行。 
 * @author jlf
 */
@Component
@Aspect
public class CategoryAspect {
	@Pointcut("execution(* com.roby.demo.spring.aop.TestService.echo(..))")
	public void pointCut() {
	}
	@Before("CategoryAspect.pointCut()")
	public void before(JoinPoint joinPoint) {
		System.out.println("before aspect executed");
	}
	@After("pointCut()")
	public void after(JoinPoint joinPoint) {
		System.out.println("after aspect executed");
	}
	@AfterReturning(pointcut = "pointCut()", returning = "returnVal")
	public void afterReturning(JoinPoint joinPoint, Object returnVal) {
		System.out.println("afterReturning executed, return result is "
				+ returnVal);
	}
	@Around("pointCut()")
	public void around(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("around start..");
		try {
			pjp.proceed();
		} catch (Throwable ex) {
			System.out.println("error in around");
			throw ex;
		}
		System.out.println("around end");
	}

	@AfterThrowing(pointcut = "pointCut()", throwing = "error")
	public void afterThrowing(JoinPoint jp, Throwable error) {
		System.out.println("error:" + error);
	}

}
