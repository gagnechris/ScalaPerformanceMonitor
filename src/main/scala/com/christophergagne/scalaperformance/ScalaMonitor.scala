package com.christophergagne.scalaperformance

import org.aspectj.lang.annotation._
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.weaver.tools.JoinPointMatch

import akka.actor.{ActorSystem, Props}

@Aspect
abstract class ScalaMonitor {
  import PerfLoggingActor._

  implicit val system = ActorSystem("perf-logging-system")
  val perfLoggingActor = system.actorOf(Props[PerfLoggingActor], "perf-logging-actor")

  @Around(value = "methodPerformancePointcut()")
  def logMethodPerformance(jp: ProceedingJoinPoint) = {
    val startTime = System.currentTimeMillis()

    jp.proceed()

    val endTime = System.currentTimeMillis()

    perfLoggingActor ! LogMethodDuration(new LogObject(jp.toLongString(), startTime, endTime))
  }

	@Around(value = "methodPerformancePointcutWithReturn()")
	def logMethodPerformanceWithReturn(jp: ProceedingJoinPoint) = {
    val startTime = System.currentTimeMillis()

    val result = jp.proceed()

    val endTime = System.currentTimeMillis()

    perfLoggingActor ! LogMethodDuration(new LogObject(jp.toLongString(), startTime, endTime))
    result
	}
}
