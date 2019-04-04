package models

import play.api.libs.json.{Format, Json}


case class File(
                   id: String,
                   title: String,
                   output: String,
                   customProperties: Map[String, String]
               ) {}

object File {
    implicit val format: Format[File] = Json.format
}

case class FileList(fileList: List[File]) {}

object FileList {
    implicit val format: Format[FileList] = Json.format
}
