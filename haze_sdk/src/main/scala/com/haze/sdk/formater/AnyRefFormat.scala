package com.haze.sdk.formater

import java.lang.reflect.{Field, AnnotatedElement}
import com.haze.sdk.Logout
import com.shengpay.commons.core.utils.ClassUtils.isConstantsField

object AnyRefFormat {
  val OUT_LOG_PACKAGES = List("com.shengpay", "com.snda", "com.sdp", "com.sdo")
  implicit def any2AnyFormater(f: Any) = AnyFormater(f)
  val class2FieldsMap = collection.mutable.Map[Class[_], Array[Field]]()
}

case class AnyRefFormat(value: AnyRef) extends Formater {

  import AnyRefFormat._

  override def toString: String =
    if (isOutDetail(clazz)) s"$className@$hashcode{$fieldValueInfo}"
    else value.simple

  def isOutDetail(cla: Class[_]) = {
    val cannotOutDetail = cla == null || cla.getPackage() == null || OUT_LOG_PACKAGES.find(packageName.startsWith(_)) == None || isAnnotationIgnore(cla)
    !cannotOutDetail
  }

  def isAnnotationIgnore(any: AnnotatedElement) = {
    val an = any.getAnnotation(classOf[Logout])
    an != null && an.ignore
  }

  def fieldValueInfo = {
    val values = for (f <- getLogOutFieldsByCache; v = fieldValue(f, value) if v != null) yield "%s=%s".format(f.getName, v.simple)
    values.mkString(",")
  }

  def fieldValue(field: Field, any: Any) = {
    field.setAccessible(true)
    field.get(any)
  }

  def getLogOutFieldsByCache: Array[Field] = {
    var fields = class2FieldsMap.getOrElse(clazz, null)
    if (fields == null) {
      fields = getLogOutFields(clazz)
      class2FieldsMap(clazz) = fields
    }
    fields
  }

  def getLogOutFields(cla: Class[_]): Array[Field] = {
    if (isOutDetail(cla)) cla.getDeclaredFields.filter(f => !isConstantsField(f) && !isAnnotationIgnore(f) && !f.getName().startsWith("ajc$tjp")) ++ getLogOutFields(cla.getSuperclass())
    else Array()
  }
}
