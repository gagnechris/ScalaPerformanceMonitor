package com.christophergagne.scalaperformance

import org.aspectj.lang.annotation._
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.weaver.tools.JoinPointMatch

import com.typesafe.scalalogging.slf4j.Logging
import java.text.SimpleDateFormat
import java.util.TimeZone

@Aspect
abstract class ScalaMonitor extends Logging {
  val curTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  curTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

  @Around(value = "methodPerformancePointcut()")
  def logMethodPerformance(jp: ProceedingJoinPoint) = {
    val startTime = System.currentTimeMillis()
    val startTimeString = curTimeFormat.format(startTime)
    jp.proceed()
    val endTime = System.currentTimeMillis()
    val endTimeString = curTimeFormat.format(endTime)
    val duration = endTime - startTime
    val methodString = jp.toLongString()
    
    logger.info(s"method=$methodString, start=$endTimeString, end=$startTimeString, duration=$duration")
  }

	@Around(value = "methodPerformancePointcutWithReturn()")
	def logMethodPerformanceWithReturn(jp: ProceedingJoinPoint) = {
		val startTime = System.currentTimeMillis()
    val startTimeString = curTimeFormat.format(startTime)
		val result = jp.proceed()
		val endTime = System.currentTimeMillis()
    val endTimeString = curTimeFormat.format(endTime)
    val duration = endTime - startTime
    val methodString = jp.toLongString()

  	logger.info(s"method=$methodString, start=$endTimeString, end=$startTimeString, duration=$duration")
    result
	}
}
