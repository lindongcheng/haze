package com.haze.sdk.filewriter

import akka.actor.Actor
import com.haze.sdk.log.Log

class FileWriterActor extends Actor {
  def receive = {
    case log: Log => FileWriter(log.logFileBelongTime).output(log.info)
  }
}

