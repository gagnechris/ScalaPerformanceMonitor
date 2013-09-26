package com.christophergagne.scalaperformance

import javax.management._
import java.lang.management.ManagementFactory

import com.typesafe.scalalogging.slf4j.Logging

object JMXClient extends Logging {
  var perfCounterMap = collection.mutable.Map[String, PerfCounters]()

  def recordExecution(monitorName: String, start: Long, end: Long) {
    perfCounterMap.getOrElseUpdate(monitorName, new PerfCounters(monitorName)).recordExecution(start, end)
  }

  class PerfCounters(val name: String) extends PerfCountersMBean{
    val mbs = ManagementFactory.getPlatformMBeanServer()
    val objectName = new ObjectName(s"${name}:type=Performance")
    mbs.registerMBean(this, objectName);
	}

  trait PerfCountersMBean {
    var accumulatedTime: Long = 0
    var maxTime: Long = 0
    var count: Int = 0
    var failureCount: Int = 0

    def recordExecution(start: Long, end: Long) {
      val timeElapsed = end - start
      accumulatedTime += timeElapsed
      maxTime = Math.max(timeElapsed, maxTime)
      count += 1
    }

    def getAccumulatedTime(): Long = {
      accumulatedTime
    }

    def getMaxTime(): Long = {
      maxTime
    }

    def getCount(): Long = {
      count
    }

    def reset() {
      accumulatedTime = 0
      maxTime = 0
      count = 0
      failureCount = 0
    }
  }
}
