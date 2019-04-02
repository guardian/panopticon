package services

import java.io.FileInputStream
import java.nio.file.{Files, Paths}

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.{DriveScopes, Drive => DriveClient}
import play.api.libs.json.{Format, Json}

import scala.collection.JavaConverters._

case class File(id: String) {}

object File {
    implicit val format: Format[File] = Json.format
}

case class FileList(fileList: List[File]) {}

object FileList {
    implicit val format: Format[FileList] = Json.format
}

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
            .setTeamDriveId(Config.teamDriveId)
        request.execute()
    }
}

