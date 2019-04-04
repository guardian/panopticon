package services

import java.io.FileInputStream

import play.api.libs.json.{JsNumber, JsString, JsSuccess, Json => PlayJson}
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.model.File
import com.google.api.services.drive.{DriveScopes, Drive => DriveClient}
import models.Output.{Document, Presentation, Spreadsheet, UnknownDriveOutput, UnsupportedDriveOutput, Video}
import models.Quarter.{Q1, Q2, Q3, Q4, UnknownQuarter}
import models.Status.Complete
import models._

import scala.collection.JavaConverters._
import scala.util.Try


object DriveService {

  private val httpTransport = new NetHttpTransport()
  private val jacksonFactory = new JacksonFactory()
  private val filePath = new FileInputStream(Config.serviceAccountKey)

  private val credential: GoogleCredential = GoogleCredential.fromStream(filePath)

  private val scopedCredential = credential.createScoped(List(DriveScopes.DRIVE_READONLY).asJava)

  lazy val driveClient: DriveClient = new DriveClient.Builder(httpTransport, jacksonFactory, scopedCredential)
    .build()

  def extractRawFile(file: File): DriveFile = {
    // The inline Try will handle nulls returned from Java library
    val customProps = Try {
      file.getProperties.asScala.toMap
    }.toOption.getOrElse(Map.empty)
    val thumbnailLink = Try {
      file.getThumbnailLink
    }.toOption.getOrElse("")
    val exportLinks = Try {
      file.getExportLinks.asScala.toMap
    }.toOption.getOrElse(Map.empty)

    DriveFile(
      id = file.getId,
      title = file.getName,
      output = file.getMimeType,
      outputPreview = file.getWebViewLink, // TODO - process to preview
      outputDownload = file.getWebContentLink, // TODO - returning nulls
      outputThumbnail = thumbnailLink, // TODO - returning 404s
      exportLinks = exportLinks,
      customProperties = customProps
    )
  }

  def convertToPreview(str: String) = {
    str.replaceAll("edit", "preview")
  }


  def getQuarter(str: String): Quarter = {
    str match {
      case "Q1" => Q1
      case "Q2" => Q2
      case "Q3" => Q3
      case "Q4" => Q4
      case "Unknown" => UnknownQuarter
      case _ => UnknownQuarter
    }
  }

  def getOutputType(str: String): Output = {
    str match {
      case "application/vnd.google-apps.presentation" => Presentation
      case "application/vnd.google-apps.document" => Document
      case "application/vnd.google-apps.spreadsheet" => Spreadsheet
      case "application/vnd.google-apps.video" => Video
      case "application/vnd.google-apps.unknown" => UnknownDriveOutput
      case _ => UnsupportedDriveOutput
    }
  }

  def transformToResearchRecord(file: DriveFile): ResearchRecord = {

    ResearchRecord.apply(
      id = file.id,
      title = file.title,
      team = file.customProperties.getOrElse("team", ""),
      output = getOutputType(file.output),
      outputUrl = convertToPreview(file.outputPreview),
      outputThumbnail = file.outputThumbnail,
      okr = file.customProperties.getOrElse("okr", ""),
      year = file.customProperties.getOrElse("year", "0").toInt,
      quarter = getQuarter("quarter"),
      tags = List("testtag")
    )
  }

  def getAllRecords: List[ResearchRecord] = {
    val request = driveClient.files().list()
      .setCorpora("teamDrive")
      .setIncludeTeamDriveItems(true)
      .setSupportsTeamDrives(true)
      .setFields("*")
      .setTeamDriveId(Config.teamDriveId)

      request.execute().getFiles.asScala.toList
        .map(file =>
          transformToResearchRecord(extractRawFile(file))
        )
  }
}



