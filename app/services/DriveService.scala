package services

import java.io.FileInputStream

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.{DriveScopes, Drive => DriveClient}
import models.File

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

  def getAllRecords = {
    val request = driveClient.files().list()
      .setCorpora("teamDrive")
      .setIncludeTeamDriveItems(true)
      .setSupportsTeamDrives(true)
      .setFields("*")
      .setTeamDriveId(Config.teamDriveId)

    request.execute().getFiles.asScala.toList
      .map(file => { // extract this into a pure function we can test

        // raw model file
        // function transforms
        // output target models

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

        File(
          id = file.getId,
          title = file.getName,
          output = file.getMimeType,
          outputPreview = file.getWebViewLink, // TODO - returning editable links - need to process to preview
          outputDownload = file.getWebContentLink, // TODO - returning nulls
          outputThumbnail = thumbnailLink, // TODO - returning 404s
          exportLinks = exportLinks,
          customProperties = customProps
        )
      })
  }
}

