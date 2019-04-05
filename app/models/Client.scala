package models

import enumeratum.{Enum, EnumEntry, PlayJsonEnum}
import play.api.libs.json.{Format, Json}

case class ResearchRecord(
  id: String,
  title: String,
  team: String,
  output: Output,
  outputUrl: String,
  outputThumbnail: String,
  outputDownload: String,
  //  outputExports: List[String],
  okr: String,
  year: Int,
  quarter: Quarter,
  //  objectives: String,
  //  researchOwner: String, // TODO could this be an email???
  tags: List[Tag], // TODO case class TAG
  // TODO add Status
)

object ResearchRecord{
  implicit val format: Format[ResearchRecord] = Json.format
}

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


