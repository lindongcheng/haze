package com.haze.sdk.commons

import java.util.UUID

case class BatchSeqPK(batchNO: String, seqNO: String) {
  def this() = this(UUID.randomUUID.toString, "")
  def childPk(idx: Int): BatchSeqPK = BatchSeqPK(batchNO, seqNO + "." + idx)
  def beginFlag = toString + ":"
  def endFlag = toString + ";"
  def ingFlag = toString + "-"
  override def toString = batchNO + seqNO
}
