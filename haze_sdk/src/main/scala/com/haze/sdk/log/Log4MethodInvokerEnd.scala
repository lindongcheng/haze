package com.haze.sdk.log

import com.haze.sdk.formater.MethodInvoker

case class Log4MethodInvokerEnd(override val mi: MethodInvoker) extends Log4MethodInvoker(mi) {

  import mi._

  override def invokeId = mi.id.endFlag
  override def selfExpend = clock.selfExpend
  override def totalExpend = clock.totalExpend
  override def exeTime = mi.clock.end
  override def targetObj = mi.targetObj
  override def method = mi.method
  override def result = {
    if (throwed != null) throwed
    else if (mi.method.getReturnType() == classOf[Unit]) "void"
    else returnVal
  }
}