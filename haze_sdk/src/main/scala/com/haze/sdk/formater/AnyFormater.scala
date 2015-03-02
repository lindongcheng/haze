package com.haze.sdk.formater

import java.lang.reflect.Method
import collection.JavaConversions._

object AnyFormater {
  val BASE_TYPES: Set[Class[_]] = Set(
    classOf[scala.Byte],
    classOf[scala.Short],
    classOf[scala.Int],
    classOf[scala.Long],
    classOf[scala.Float],
    classOf[scala.Double],
    classOf[scala.Char],
    classOf[scala.Boolean],
    classOf[scala.BigInt],
    classOf[scala.BigDecimal],
    classOf[java.lang.Byte],
    classOf[java.lang.Short],
    classOf[java.lang.Integer],
    classOf[java.lang.Long],
    classOf[java.lang.Float],
    classOf[java.lang.Double],
    classOf[java.lang.Character],
    classOf[java.lang.Boolean],
    classOf[java.math.BigDecimal],
    classOf[java.math.BigInteger],
    classOf[java.lang.String],
    classOf[java.util.Date])

}

case class AnyFormater(value: Any) extends Formater {

  def detail: String = {
    val info = value match {
      case null => "null"
      case v: String => "\"%s\"".format(v)
      case v: Iterable[Any] => IterableFormater(value, v)
      case v: Array[Any] => IterableFormater(value, v)
      case v: java.util.Collection[_] => IterableFormater(value, v)
      case v: java.util.Map[_, _] => IterableFormater(value, v)
      case v: Throwable => ThrowableFormater(v)
      case v: AnyRef => AnyRefFormat(v)
      case _ => simple
    }

    String.valueOf(info)
  }

  def simple = {
    val info = value match {
      case null => "null"
      case v: Method => MethodFormat(v)
      case v: AnyRef if isBaseType => String.valueOf(v)
      case v: AnyRef => s"$className@$hashcode"
      case _ => String.valueOf(value)
    }

    String.valueOf(info)
  }

  def isBaseType = AnyFormater.BASE_TYPES.contains(clazz)

}
