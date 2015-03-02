package com.haze.sdk.log

import com.haze.sdk.formater.MethodInvoker

class Log4Message(val message: Any, val time: Long) extends Log {
  override def msg = message
  override def exeTime = time
}

object Log4Message {
  def apply(msg: Any, time: Long): Log = MethodInvoker.parentInvoke match {
    case null => new Log4Message(msg, time)
    case _ => Log4MethodInvokerAndMessage(MethodInvoker.parentInvoke, msg, time)
  }
}
