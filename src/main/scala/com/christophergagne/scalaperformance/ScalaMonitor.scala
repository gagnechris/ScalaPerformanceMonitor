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
  	
    // val current = Thread.currentThread
    // val stack = current.getStackTrace()
    // for(stackElement <- stack){
    //   logger.debug(stackElement.toString)
    // }
		JMXClient.recordExecution(jp.getTarget.getClass.getName.toString, startTime, endTime)
	}
}
