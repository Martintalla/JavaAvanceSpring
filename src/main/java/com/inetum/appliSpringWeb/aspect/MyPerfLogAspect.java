package com.inetum.appliSpringWeb.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Profile("perf") // Pour que cet aspect ne soit activé qu'avec le profil "perf" au démarag du
					// main ou des tests
public class MyPerfLogAspect {
	@Pointcut("execution(* com.inetum.appliSpringWeb.service.*.*(..))")
	public void servicePointcut() {
	}

	@Pointcut("execution(* com.inetum.appliSpringWeb.rest.*.*(..))")
	public void restPointcut() {
	}

	@Pointcut("execution(* com.inetum.appliSpringWeb.doa.interfaces.*.*(..))")
	public void doainterfaces() {
	}

	Logger logger = LoggerFactory.getLogger(MyPerfLogAspect.class);

	@Around("servicePointcut()||doainterfaces()")
	public Object doXxxLog(ProceedingJoinPoint pjp) throws Throwable {
		logger.debug("<< trace == debut == " + pjp.getSignature().toLongString() + " <<");
		long td = System.nanoTime();
		Object objRes = pjp.proceed();
		long tf = System.nanoTime();
		logger.debug(
				">> trace == fin == " + pjp.getSignature().toShortString() + " [" + (tf - td) / 1000000.0 + " ms] >>");
		return objRes;
	}
}
