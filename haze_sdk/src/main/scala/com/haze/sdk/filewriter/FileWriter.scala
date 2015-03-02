package com.haze.sdk.filewriter

import akka.actor.{Props, ActorSystem, Actor}
import java.util.Timer
import org.slf4j.LoggerFactory
import scala.collection.mutable.HashMap
import java.lang.System._
import com.haze.sdk._
import scala.Some
import com.haze.sdk.commons.TimeStr._
import com.haze.sdk.commons.TimeLong._

trait FileWriter {
  def output(msg: String)
  def close()
  def flush()

  val dateStr: String
  val dieTime = dateStr.yyyyMMddDate.getTime + FILE_LIFE
  def isDied = currentTimeMillis > dieTime
  var path: String
  var isEnable:Boolean
}

object FileWriter {
  def apply(logTime: Long): FileWriter = {
    val dateStr = logTime.yyyyMMddStr
    normalFileMap.getOrElse(dateStr, createNormalFile(dateStr))
  }

  def createNormalFile(dateStr: String): FileWriter = normalFileMap.synchronized {
    normalFileMap.get(dateStr) match {
      case Some(file) => file
      case None => {
        val file = hdfsHost match {
          case null => new LocalFileWriter(logDir, dateStr)
          case _ => new HdfsFileWriter(hdfsHost, dateStr)
        }
        normalFileMap(dateStr) = file
        println("create log file " + file.path)
        logger.info("create log file {}", file.path)
        file
      }
    }
  }

  def getEmergencyFile(dateStr: String) = emergencyFileMap.getOrElse(dateStr, createEmergencyFile(dateStr))

  def createEmergencyFile(dateStr: String) = emergencyFileMap.synchronized {
    emergencyFileMap.get(dateStr) match {
      case Some(file) => file
      case None => {
        val writer = new LocalFileWriter(logDir, dateStr)
        emergencyFileMap(dateStr) = writer
        writer
      }
    }
  }

  val reflushCyc = 1000
  private val reflushTimer: Timer = new Timer()
  reflushTimer.schedule(flushAll _, reflushCyc, reflushCyc)

  def flushAll() {
    flushFileMap(normalFileMap)
    flushFileMap(emergencyFileMap)
  }

  private def flushFileMap(fileMap: collection.mutable.Map[String, FileWriter]): Unit = fileMap.foreach {
    case (date, file) =>
      if (!file.isEnable) return
      file.flush()

      if (file.isDied) {
        fileMap.synchronized {
          fileMap.remove(date)
        }
        file.close()
      }
  }

  def closeAll() = {
    reflushTimer.cancel()
    closeForMap(normalFileMap)
    closeForMap(emergencyFileMap)
  }

  def closeForMap(map: HashMap[String, FileWriter]) {
    map.values.foreach(_.close)
    map.clear
  }

  def logDir_=(aLogDir: String) = {
    if (aLogDir.toLowerCase.startsWith("hdfs://")) {
      hdfsHost = aLogDir
    } else {
      logDirVar = aLogDir
    }
  }

  val actor = ActorSystem("haze").actorOf(Props[FileWriterActor], name = "FileWriterActor")
  val logger = LoggerFactory.getLogger(getClass)
  val normalFileMap = new HashMap[String, FileWriter]
  val emergencyFileMap = new HashMap[String, FileWriter]
  var hdfsHost: String = null
  private[this] var logDirVar: String = s"${getProperty(KEY_USER_HOME)}/.haze/${getProperty(KEY_APP_ID)}"
  def logDir: String = logDirVar

}


