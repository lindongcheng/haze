package com.haze

import java.net.{URI, InetAddress}
import java.lang.reflect.Field
import java.util.TimerTask
import com.haze.sdk.formater.{AnyFormater, Invoker}
import com.haze.sdk.commons.{RichField, RichClass, TimeLong, TimeStr}

/**
 * Created by lindongcheng on 14-4-11.
 */
package object sdk {
  val KEY_APP_ID = "haze.appId"
  val KEY_USER_HOME = "user.home"
  val LINE_BREAK = "\r\n"
  val LOG_FILE_BUFFER_SIZE = 1024 * 1024
  val FILE_LIFE: Long = 1000 * 60 * 60 * 25l
  val LOCAL_HOST = InetAddress.getLocalHost.toString.replace('/', '_')

  implicit def str2URI(str: String) = URI.create(str)
  implicit def class2RichClass(any: Class[_]) = RichClass(any)
  implicit def field2RichField(f: Field) = RichField(f)

  implicit def f2TimerTask(f: () => Unit) = new TimerTask() {
    def run() {
      try {
        f()
      } catch {
        case t: Throwable => t.printStackTrace()
      }
    }
  }

}
