/**
 *
 */
package com.haze.sdk.integration

import ch.qos.logback.core.Appender
import com.haze.sdk.filewriter.FileWriter
import com.haze.sdk._
import com.haze.sdk.log.Log4Message


class LogbackAppender extends Appender[ch.qos.logback.classic.spi.LoggingEvent] {
  def doAppend(le: ch.qos.logback.classic.spi.LoggingEvent): Unit = if (System.getProperty(KEY_APP_ID) != null) FileWriter.actor ! Log4Message(le.getFormattedMessage(), le.getTimeStamp())

  def getName(): String = ""
  def setName(x$1: String): Unit = {}
  def addError(x$1: String, x$2: Throwable): Unit = {}
  def addError(x$1: String): Unit = {}
  def addInfo(x$1: String, x$2: Throwable): Unit = {}
  def addInfo(x$1: String): Unit = {}
  def addStatus(x$1: ch.qos.logback.core.status.Status): Unit = {}
  def addWarn(x$1: String, x$2: Throwable): Unit = {}
  def addWarn(x$1: String): Unit = {}
  def getContext(): ch.qos.logback.core.Context = null
  def setContext(x$1: ch.qos.logback.core.Context): Unit = {}
  def addFilter(x$1: ch.qos.logback.core.filter.Filter[ch.qos.logback.classic.spi.LoggingEvent]): Unit = {}
  def clearAllFilters(): Unit = {}
  def getCopyOfAttachedFiltersList(): java.util.List[ch.qos.logback.core.filter.Filter[ch.qos.logback.classic.spi.LoggingEvent]] = null
  def getFilterChainDecision(x$1: ch.qos.logback.classic.spi.LoggingEvent): ch.qos.logback.core.spi.FilterReply = null
  def isStarted(): Boolean = true
  def start(): Unit = {}
  def stop(): Unit = {}
}