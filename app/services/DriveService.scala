package services

import java.io.FileInputStream
import scala.collection.JavaConverters._
import scala.util.Try

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.model.File
import com.google.api.services.drive.{DriveScopes, Drive => DriveClient}
import models.Output.{Document, Presentation, Spreadsheet, UnknownDriveOutput, UnsupportedDriveOutput, Video}
import models.Quarter.{Q1, Q2, Q3, Q4, UnknownQuarter}
import models._


object DriveService {

  private val httpTransport = new NetHttpTransport()
  private val jacksonFactory = new JacksonFactory()
  private val filePath = new FileInputStream(Config.serviceAccountKey)

  private val credential: GoogleCredential = GoogleCredential.fromStream(filePath)
  private val scopedCredential = credential.createScoped(List(DriveScopes.DRIVE_READONLY).asJava)

  lazy val driveClient: DriveClient = new DriveClient.Builder(httpTransport, jacksonFactory, scopedCredential)
    .build()

  def getAllRecords: List[ResearchRecord] = {
    val request = driveClient.files().list()
      .setCorpora("teamDrive")
      .setIncludeTeamDriveItems(true)
      .setSupportsTeamDrives(true)
      .setFields("*")
      .setTeamDriveId(Config.teamDriveId)

    request.execute().getFiles.asScala.toList
      .map(file =>
        // transformToResearchRecord(extractRawFile(file)) // define functions with def
        (extractRawFile andThen transformToResearchRecord)(file) // define functions with val
    )
  }

  val extractRawFile = (fileStream: File) => {
    val customProps = Try {
      fileStream.getProperties.asScala.toMap
    }.toOption.getOrElse(Map.empty)
    val exportLinks = Try {
      fileStream.getExportLinks.asScala.toMap
    }.toOption.getOrElse(Map.empty)

    DriveFile(
      id = fileStream.getId,
      title = fileStream.getName,
      output = fileStream.getMimeType,
      outputPreview = fileStream.getWebViewLink,
      outputDownload = fileStream.getWebContentLink, // TODO - returning nulls
      exportLinks = exportLinks,
      customProperties = customProps
    )
  }

  val transformToResearchRecord = (file: DriveFile) => {
    ResearchRecord.apply(
      id = file.id,
      title = file.title,
      team = file.customProperties.getOrElse("team", "Unknown Team"),
      output = setOutputType(file.output),
      outputUrl = convertToPreviewLink(file.outputPreview),
      outputThumbnail = createThumbnailLink(file.id),
      outputDownload = file.outputDownload, // TODO returning null - permissions issue?
      okr = file.customProperties.getOrElse("okr", ""),
      year = file.customProperties.getOrElse("year", "0").toInt,
      quarter = setQuarter(file.customProperties.getOrElse("quarter", "Unknown Quarter")),
      tags = setRandomTags(file.customProperties.getOrElse("tags", "No tags found")) // Generate Random Tags
    )
  }

  def convertToPreviewLink(str: String) = {
    str.replaceAll("edit", "preview")
  }

  def createThumbnailLink(id: String): String = {
    s"https://drive.google.com/thumbnail?authuser=0&sz=w320&id=$id"
  }

  def setRandomTags(str: String): List[Tag] = {
    str match {
      case "No tags found" => Tag.selectRandomTags()
      case _ => Nil
    }
  }

  def setQuarter(str: String): Quarter = {
    str match {
      case "1" => Q1
      case "2" => Q2
      case "3" => Q3
      case "4" => Q4
      case "Q1" => Q1
      case "Q2" => Q2
      case "Q3" => Q3
      case "Q4" => Q4
      case "Unknown Quarter" => UnknownQuarter
      case _ => UnknownQuarter
    }
  }

  def setOutputType(str: String): Output = {
    str match {
      case "application/vnd.google-apps.presentation" => Presentation
      case "application/vnd.google-apps.document" => Document
      case "application/vnd.google-apps.spreadsheet" => Spreadsheet
      case "application/vnd.google-apps.video" => Video
      case "application/vnd.google-apps.unknown" => UnknownDriveOutput
      case _ => UnsupportedDriveOutput
    }
  }

}



