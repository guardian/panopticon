package models

import play.api.libs.json.{Format, Json}

case class DriveFile(
  id: String,
  title: String,
  output: String,
  outputPreview: String,
  outputDownload: String,
  outputIcon: String,
  exportLinks: Map[String, String],
  customProperties: Map[String, String]
) {}

object DriveFile {
  implicit val format: Format[DriveFile] = Json.format
}

case class DriveFileList(fileList: List[DriveFile]) {}

object DriveFileList {
  implicit val format: Format[DriveFileList] = Json.format
}
