package com.haze.sdk.formater

import java.lang.reflect.Method

case class MethodFormat(method: Method) {
  override def toString = s"$className.$methodName($paramsInfo)"

  def className = method.getDeclaringClass().getSimpleName()
  def methodName = method.getName()
  def paramsInfo = method.getParameterTypes().map(_.getSimpleName()).mkString(",")
}

