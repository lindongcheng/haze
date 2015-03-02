package com.haze.sdk.filewriter

import java.lang.System._
import java.io.{BufferedWriter, Writer, File}
import com.haze.sdk._

class LocalFileWriter(fileDir: String, val dateStr: String) extends FileWriter {
  var path = s"$fileDir/${getProperty(KEY_APP_ID)}-$dateStr.log"
  val parentDir = new File(path).getParentFile
  if (!parentDir.exists) parentDir.mkdirs()

  var out: Writer = new BufferedWriter(new java.io.FileWriter(path, true), LOG_FILE_BUFFER_SIZE)
  var isEnable:Boolean=true
  def output(msg: String) = out.append(msg + LINE_BREAK)
  def close() = out.close()
  def flush() = out.flush()
}
