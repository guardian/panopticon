package models

import play.api.libs.json.{Format, Json}

case class DriveFile(
  id: String,
  title: String,
  output: String,
  outputPreview: String,
  outputDownload: String,
  outputThumbnail: String,
  exportLinks: Map[String, String],
  customProperties: Map[String, String]
) {}

object DriveFile {
  implicit val format: Format[DriveFile] = Json.format
}

case class RawFileList(fileList: List[DriveFile]) {}

object RawFileList {
  implicit val format: Format[RawFileList] = Json.format
}
