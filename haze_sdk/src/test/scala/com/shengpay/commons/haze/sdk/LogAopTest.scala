package com.haze.sdk

import org.springframework.context.support.ClassPathXmlApplicationContext
import org.springframework.stereotype.Service
import javax.annotation.Resource
import collection.JavaConversions._
import java.util.HashMap
import org.slf4j.LoggerFactory
import java.util.HashSet
import com.haze.sdk.filewriter.FileWriter

object LogAopTest extends App {

  val factory = new ClassPathXmlApplicationContext("applicationContext.xml");
  val stockService = factory.getBean(classOf[AService]);
  try {
    println(List("lin", "dong", 1).mkString(","))
    System.setProperty("haze.log.user","lindongcheng")
    for (i <- 1 to 100) {
      stockService.getBaseInfo(Person("IBM", 200), Person("Ldc", 31), List(1: java.lang.Long))
      Thread.sleep(1000)
      println(s"run times $i")
    }
    Thread.sleep(1000 * 3)
    FileWriter.closeAll()
    println("close all")
  } finally {
    //    factory.destroy()
  }
}

class AP(var id: Long = 100)

case class Person(name: String, age: Int) extends AP

@Service
class AService {
  @Resource val b: BService = null

  @Logout(ignore = true) val log = LoggerFactory.getLogger(getClass)
  def getBaseInfo(p: Person, a: Person, list: java.util.List[java.lang.Long]): Unit = {
    log.info("aaaaaaaaaaaaaaaaaaaaaaaa")
    b.m1()
    b.m2(p.name)
  }

  override def toString(): String = "lindongcheng"
}

@Service
@Logout(ignore = true)
class BService {
  val logger = LoggerFactory.getLogger(getClass)
  def m1(): String = {
    "m1 println "
  }
  def m2(msg: String): java.util.Set[String] = {
    //    println("m2 " + msg)
    logger.error("test exception", new RuntimeException())
    val hm = new HashSet[String]()
    hm.add("lin")
    hm.add("dong")
    hm
    //    throw new RuntimeException()
  }
}


object Main extends App {
  System.getProperties().foreach(a => printf("%s=%s\n", a._1, a._2))
}