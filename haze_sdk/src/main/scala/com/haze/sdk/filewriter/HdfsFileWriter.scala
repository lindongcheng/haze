/**
 *
 */
package com.haze.sdk.filewriter

import java.util.Timer
import java.lang.System._
import org.slf4j.LoggerFactory
import org.apache.hadoop.fs.{FileSystem, FSDataOutputStream}
import org.apache.hadoop.conf.Configuration
import FileWriter._
import com.haze.sdk._


class HdfsFileWriter(hdfsHost: String, val dateStr: String) extends FileWriter {
  var isEnable = false
  var path: String = null
  var out: FSDataOutputStream = mkOutputStream
  def mkOutputStream = {
    path = s"/haze/${getProperty(KEY_APP_ID)}/$dateStr/${LOCAL_HOST}_${currentTimeMillis()}.log"
    val fileSystem = FileSystem.get(hdfsHost, new Configuration)
    val output = fileSystem.create(path)
    isEnable = true
    output
  }
  new Timer().schedule(retryConnectHdfs _, reflushCyc, reflushCyc)
  def retryConnectHdfs() {
    if (isEnable) return

    try {
      out = mkOutputStream
      isEnable = true
    } catch {
      case t: Throwable => println(s"hflush or reconnect hdfs happened exception: ${t.getMessage}")
    }
  }

  def output(msg: String): Unit =
    try {
      if (isEnable) out.write((msg + LINE_BREAK).getBytes("UTF-8"))
      else getEmergencyFile(dateStr).output(msg)
    } catch {
      case t: Throwable => {
        isEnable = false
        logger.error(s"output msg [$msg] to [$this] happend exception:", t)
        getEmergencyFile(dateStr).output(msg)
      }
    }

  def close() = out.close()
  def flush() = out.hflush()
  val logger = LoggerFactory.getLogger(getClass)
}



