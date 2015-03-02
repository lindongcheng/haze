package com.haze.sdk.commons

import java.lang.reflect.Field
import java.lang.reflect.Modifier._

case class RichField(field: Field) {
  def isConstants = isFinal(field.getModifiers()) && isStatic(field.getModifiers())
  def notConstants = !isConstants
}
