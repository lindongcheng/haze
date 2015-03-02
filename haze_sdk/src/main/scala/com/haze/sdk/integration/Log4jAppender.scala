package com.haze.sdk.integration

import org.apache.log4j.AppenderSkeleton
import com.haze.sdk.filewriter.FileWriter
import com.haze.sdk._
import com.haze.sdk.log.Log4Message

class Log4jAppender extends AppenderSkeleton {
  def append(le: org.apache.log4j.spi.LoggingEvent): Unit = if (System.getProperty(KEY_APP_ID) != null) FileWriter.actor ! Log4Message(le.getMessage(), System.currentTimeMillis())
  def close(): Unit = {}
  def requiresLayout(): Boolean = false
}
