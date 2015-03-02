package com.haze.sdk.log

import com.haze.sdk.formater.MethodInvoker

class Log4MethodInvokerBegin(override val mi: MethodInvoker) extends Log4MethodInvoker(mi) {
  override def invokeId = mi.id.beginFlag
  override def exeTime = mi.clock.begin
  override def method = mi.method
  override def targetObj = mi.targetObj
  override def args = mi.args
}