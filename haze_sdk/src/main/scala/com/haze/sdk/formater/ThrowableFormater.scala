package com.haze.sdk.formater

case class ThrowableFormater(t: Throwable) {
  override def toString = com.shengpay.commons.core.utils.ThrowableUtils.getThrowableInfo(t)
}