package com.haze.sdk.formater

import AnyRefFormat._

case class IterableFormater(value: Any, ite: Iterable[Any]) extends Formater {
  val formater = new StringBuffer(s"$className@$hashcode(length=$length){")
  var i = 0
  for (item <- ite if i < MAX_OUTPUT) {
    if (i > 0) formater.append(",")
    formater.append(item.detail)
    i += 1
  }
  val MAX_OUTPUT = 3
  if (MAX_OUTPUT < ite.size) formater.append("...")
  formater.append("}")

  override def toString = formater.toString()
  def length = ite.size
}
