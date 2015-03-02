package com.haze.sdk

import java.io.FileOutputStream
import org.apache.hadoop.fs.{FSDataOutputStream, Path, FileSystem}
import org.apache.hadoop.conf.Configuration
import java.util.Timer

object Test extends App {
  val fileOutputStream = new FileOutputStream("d:/a.txt")
  var outputstream = mkHdfsOutputStream()
  var hdfsIsEnable = true
  val reflushCyc = 1000
  new Timer().schedule(retryConnectHdfs _, reflushCyc, reflushCyc)
  for (i <- 0 until Int.MaxValue) {
    write(i)
    Thread.sleep(1000)
  }

  def mkHdfsOutputStream():FSDataOutputStream = {
    val hdfsPath = s"/test/${System.currentTimeMillis()}"
    val hdfsFileSystem = FileSystem.get("hdfs://haze14:8010", new Configuration())
    val path = new Path(hdfsPath)
    if (hdfsFileSystem.exists(path)) {
      hdfsFileSystem.append(path)
    } else {
      hdfsFileSystem.create(path)
    }
  }

  def retryConnectHdfs() {
    try {
      if (hdfsIsEnable) {
        outputstream.hflush()
      } else {
        outputstream = mkHdfsOutputStream()
        hdfsIsEnable = true
      }
    } catch {
      case t: Throwable => {
          println(s"hflush or reconnect hdfs happened exception: ${t.getMessage}")
          t.printStackTrace()
        }
    }

    try {
      fileOutputStream.flush()
    } catch {
      case t: Throwable => println(s"flush local output stream happened exception: ${t.getMessage}")
    }
  }

  def write(i: Int) {
    try {
      if (hdfsIsEnable) {
        outputstream.write(s"$i \r\n".getBytes("UTF-8"))
        println(s"write msg [$i] to hdfs")
      } else {
        fileOutputStream.write(s"$i \r\n".getBytes("UTF-8"))
        println(s"write msg [$i] to local file")
      }
    } catch {
      case t: Throwable => {
          hdfsIsEnable = false
          println(s"write happened exception [${t.getMessage}}]")
          fileOutputStream.write(s"$i \r\n".getBytes("UTF-8"))
        }
    }
  }


}

/*
 if (i % 10 == 0) {
 try {
 outputstream.hsync()
 } catch {
 case t: Throwable => {
 println(s"hsync happened exception [${t.getMessage}}]")
 //            t.printStackTrace()
 }
 }
 }

 */