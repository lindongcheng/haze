package com.haze.sdk.integration

import org.aopalliance.intercept.{MethodInvocation, MethodInterceptor}
import com.haze.sdk.filewriter.FileWriter
import com.haze.sdk._
import com.haze.sdk.formater.MethodInvoker

class AOP extends MethodInterceptor {
  @throws(classOf[Throwable]) def invoke(mi: MethodInvocation) = MethodInvoker(mi).invoke

  def setAppId(appId: String): Unit = System.setProperty(KEY_APP_ID, appId.toLowerCase)
  def setLogDir(ld: String): Unit = {
    try {
      FileWriter.logDir = ld
    } catch {
      case t: Throwable => t.printStackTrace()
    }
  }
  def close = FileWriter.closeAll()
}

