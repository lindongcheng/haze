package com.haze.sdk.commons

import java.text.SimpleDateFormat
import java.util.Date

case class TimeLong(tl: Long) {
  def yyyyMMddStr = new SimpleDateFormat("yyyyMMdd").format(new Date(tl))
  def yyyy_MM_dd_HH_mm_ss_S = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,S").format(new Date(tl))
  def HH_mm_ss_S = new SimpleDateFormat("HH:mm:ss,S").format(new Date(tl))
}

object TimeLong{
  implicit def long2TimeLong(tl: Long) = TimeLong(tl)
}