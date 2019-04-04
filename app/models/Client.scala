package models

import java.net.URL
import enumeratum.{Enum, EnumEntry, PlayJsonEnum}
import models.Status.Complete

sealed trait Quarter extends EnumEntry
object Quarter extends Enum[Quarter] with PlayJsonEnum[Quarter] {
  val values = findValues
  case object Q1 extends Quarter
  case object Q2 extends Quarter
  case object Q3 extends Quarter
  case object Q4 extends Quarter

}

sealed trait Status extends EnumEntry
object Status extends Enum[Status] with PlayJsonEnum[Status] {
  val values = findValues
  case object Complete extends Status
  case object InProgress extends Status
  case object Scheduled extends Status

}

case class Tag(id: String)

case class ResearchRecord(
  id: String,
  title: String,
  team: String,
  output: String,
  outputUrl: URL,
  outputThumbnail: URL,
  okr: String,
  year: Int,
  quarter: Quarter,
  objectives: String,
  researchOwner: String, // TODO could this be an email???
  tags: List[String], // TODO case class TAG
  status: Status = Complete
)

