package models


import enumeratum.{Enum, EnumEntry, PlayJsonEnum}
import play.api.libs.json.{Format, Json}

sealed trait Quarter extends EnumEntry
object Quarter extends Enum[Quarter] with PlayJsonEnum[Quarter] {
  val values = findValues
  case object Q1 extends Quarter
  case object Q2 extends Quarter
  case object Q3 extends Quarter
  case object Q4 extends Quarter
  case object UnknownQuarter extends Quarter

}

sealed trait Status extends EnumEntry
object Status extends Enum[Status] with PlayJsonEnum[Status] {
  val values = findValues
  case object Complete extends Status
  case object InProgress extends Status
  case object Scheduled extends Status

}

sealed trait Output extends EnumEntry
object Output extends Enum[Output] with PlayJsonEnum[Output] {
  val values = findValues
  case object Presentation extends Output
  case object Document extends Output
  case object Spreadsheet extends Output
  case object Video extends Output
  case object UnknownDriveOutput extends Output
  case object UnsupportedDriveOutput extends Output
}

case class Tag(id: String)

case class ResearchRecord(
  id: String,
  title: String,
  team: String,
  output: Output,
  outputUrl: String,
  outputThumbnail: String,
  okr: String,
  year: Int,
  quarter: Quarter,
//  objectives: String,
//  researchOwner: String, // TODO could this be an email???
  tags: List[String], // TODO case class TAG
)
object ResearchRecord{
  implicit val format: Format[ResearchRecord] = Json.format
}

//case class ResearchRecordList(recordList: List[ResearchRecord]) {}
//
//object ResearchRecordList{
//  implicit val format: Format[ResearchRecordList] = Json.format
//}
