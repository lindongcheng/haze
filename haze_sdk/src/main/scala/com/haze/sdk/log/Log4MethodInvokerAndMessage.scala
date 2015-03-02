package com.haze.sdk.log

import com.haze.sdk.formater.MethodInvoker

case class Log4MethodInvokerAndMessage(override val mi: MethodInvoker, message: Any, time: Long) extends Log4MethodInvoker(mi) {
  override def msg = message
  override def invokeId = mi.id.ingFlag
  override def exeTime = time
}
