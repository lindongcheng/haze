package com.haze.sdk.commons

import java.text.SimpleDateFormat

object TimeStr{
  implicit def str2TimeStr(ts: String) = TimeStr(ts)
}
case class TimeStr(ds: String) {
  def yyyyMMddDate = (new SimpleDateFormat("yyyyMMdd")).parse(ds)
}
