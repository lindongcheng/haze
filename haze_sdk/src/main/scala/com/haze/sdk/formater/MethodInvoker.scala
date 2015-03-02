/**
 *
 */
package com.haze.sdk.formater

import java.lang.reflect.Method
import org.aopalliance.intercept.MethodInvocation
import MethodInvoker._
import java.util.concurrent.CopyOnWriteArrayList
import collection.JavaConversions._
import com.haze.sdk.filewriter.FileWriter
import com.haze.sdk.commons.{BatchSeqPK, Clock}
import com.haze.sdk.log.{Log4MethodInvokerEnd, Log4MethodInvokerBegin, Log}
import com.haze.sdk.log.Log4MethodInvokerEnd

trait Invoker {
  def invoke: Object
}

class MethodInvoker(val targetObj: AnyRef, val method: Method, val args: Array[AnyRef], val invoker: Invoker) {

  def invoke(): Object = {
    if (isIgnoredLogout) return invoker.invoke

    try {
      beforeInvoke()
      returnVal = invoker.invoke
      return returnVal
    } catch {
      case t: Throwable =>
        t.printStackTrace()
        throwed = t
        throw t
    } finally {
      afterInvoke()
    }
  }

  def isIgnoredLogout = ignoredMethods.contains(method.getName()) && method.getParameterTypes().length == 0

  def beforeInvoke() {
    clock.start
    parent = parentInvoke
    parentInvoke = this
    print(new Log4MethodInvokerBegin(this))
  }
  def afterInvoke() {
    clock.finish
    parentInvoke = parent
    print(Log4MethodInvokerEnd(this))
  }

  def print(logFormat: Log) = FileWriter.actor ! logFormat
  def rootTime: Long = if (parent == null) clock.begin else parent.rootTime

  def parent = parentVar
  def parent_=(parentMI: MethodInvoker) {
    if (parentMI == null) {
      id = new BatchSeqPK
    } else {
      parentVar = parentMI
      id = parentMI.add(this)
    }
  }

  def add(childMI: MethodInvoker): BatchSeqPK = {
    childrenMI.add(childMI)
    id.childPk(childrenMI.indexOf(childMI) + 1)
  }

  var id: BatchSeqPK = _
  val thread = Thread.currentThread
  val clock: Clock = new Clock(() => childrenMI.map(_.clock))

  var returnVal: AnyRef = _
  var throwed: Throwable = _

  private[this] var parentVar: MethodInvoker = null
  val childrenMI = new CopyOnWriteArrayList[MethodInvoker]
}

object MethodInvoker {

  implicit def f2Invoker(f: () => Object) = new Invoker() {
    def invoke = f()
  }

  val parentInvokeTL = new ThreadLocal[MethodInvoker]
  def parentInvoke = parentInvokeTL.get
  def parentInvoke_=(mi: MethodInvoker) = parentInvokeTL.set(mi)
  def apply(mi: MethodInvocation) = new MethodInvoker(mi.getThis(), mi.getMethod(), mi.getArguments(), () => mi.proceed())
  def ignoredMethods = Set("toString", "hashCode")
}
