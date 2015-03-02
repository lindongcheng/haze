package com.haze.sdk.log

import java.lang.reflect.Method
import com.haze.sdk.commons.TimeLong._
import com.haze.sdk.formater.AnyRefFormat._

trait Log {
  def info: String = elements.mkString(SEPARATOR)
  def elements = List[Any](
    invokeId,
    exeTime.HH_mm_ss_S,
    thread + "_" + thread.getId(),
    targetObj.simple,
    method.simple,
    args.map(_.detail).mkString(","),
    result.detail,
    selfExpend,
    totalExpend,
    user,
    msg)

  def logFileBelongTime: Long = System.currentTimeMillis
  def invokeId: String = null
  def exeTime: Long = System.currentTimeMillis
  def thread: Thread = Thread.currentThread
  def targetObj: Any = null
  def method: Method = null
  def args: Array[AnyRef] = Array()
  def msg: Any = null
  def selfExpend: Long = -1
  def totalExpend: Long = -1
  def result: Any = null
  def user: Any = System.getProperty("haze.log.user")
  val SEPARATOR = "#"
}








