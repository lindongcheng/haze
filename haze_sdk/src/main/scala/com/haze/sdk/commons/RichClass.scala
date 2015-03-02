package com.haze.sdk.commons

import java.lang.reflect.Proxy

case class RichClass(clazz: Class[_]) {
  def isProxy = Proxy.isProxyClass(clazz)
  def packageName = {
    if (isProxy)
      "Proxy"
    else
      clazz.getPackage().getName()
  }

}
