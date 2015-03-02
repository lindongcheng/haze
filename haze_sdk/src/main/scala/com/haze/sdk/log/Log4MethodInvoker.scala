package com.haze.sdk.log

import com.haze.sdk.formater.MethodInvoker

class Log4MethodInvoker(val mi: MethodInvoker) extends Log {
  override def logFileBelongTime = mi.rootTime
  override def thread = mi.thread
}
