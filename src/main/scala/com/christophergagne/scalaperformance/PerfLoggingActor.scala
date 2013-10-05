package com.christophergagne.scalaperformance

import akka.actor.{Actor, Props, ActorLogging}

import java.text.SimpleDateFormat
import java.util.TimeZone

object PerfLoggingActor {
  case class LogObject(method: String, startTime: Long, endTime: Long)
  case class LogMethodDuration(logInfo: LogObject)
}

class PerfLoggingActor() extends Actor with ActorLogging {
  import PerfLoggingActor._

  val config = context.system.settings.config.getConfig("scalaperformance.logging")
  val curTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
  curTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT"))

  def receive = {
    case LogMethodDuration(logInfo) => {
      if (config.getBoolean("via-logger")) logViaLogger(logInfo)
      if (config.getBoolean("via-jmx-client")) logViaJMXClient(logInfo)
      if (config.getBoolean("via-mdb-client")) logViaMDB(logInfo)
      if (config.getBoolean("via-mysql-client")) logViaMySQL(logInfo)
    }
  }

  def logViaLogger(logInfo: LogObject) {
    val startTimeString = curTimeFormat.format(logInfo.startTime)
    val endTimeString = curTimeFormat.format(logInfo.endTime)
    val duration = logInfo.endTime - logInfo.startTime
    val methodString = logInfo.method
    log.info(s"method=$methodString, start=$endTimeString, end=$startTimeString, duration=$duration")
  }

  def logViaJMXClient(logInfo: LogObject) {
    JMXClient.recordExecution(logInfo.method, logInfo.startTime, logInfo.endTime)
  }

  def logViaMDB(logInfo: LogObject) {

  }

  def logViaMySQL(logInfo: LogObject) {

  }
}
