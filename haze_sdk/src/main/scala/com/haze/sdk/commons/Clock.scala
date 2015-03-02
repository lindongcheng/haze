package com.haze.sdk.commons

import scala.collection.mutable.Buffer

class Clock(childrenClock: () => Buffer[Clock]) {
  var begin: Long = 0
  var end: Long = 0

  def start = begin = System.currentTimeMillis
  def finish = end = System.currentTimeMillis

  def totalExpend = end - begin
  def selfExpend = totalExpend - childrenClock().map(_.totalExpend).sum
}
