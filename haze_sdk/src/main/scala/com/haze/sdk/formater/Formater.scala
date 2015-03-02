package com.haze.sdk.formater


trait Formater {
  val value: Any

  def clazz = value.getClass
  def className = clazz.getSimpleName()
  def hashcode = Integer.toHexString(value.hashCode())
  def packageName = clazz.getPackage().getName()
}




