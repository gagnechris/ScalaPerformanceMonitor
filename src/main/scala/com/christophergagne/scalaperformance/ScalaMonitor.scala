package com.christophergagne.scalaperformance

import org.aspectj.lang.annotation._
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.weaver.tools.JoinPointMatch

import com.typesafe.scalalogging.slf4j.Logging
import compat.Platform.currentTime

@Aspect
abstract class ScalaMonitor extends Logging {
	@Around(value = "methodPerformancePointcut()")
	def logMethodPerformance(jp: ProceedingJoinPoint) = {
		val startTime = currentTime
		jp.proceed()
		val endTime = currentTime
		// JMXClient.recordExecution(jp.getSignature().toString(), startTime, endTime)
		logger.info(s"type=perf elapsedTime: ${endTime-startTime}, ${jp.getSignature()}")
	}
}
