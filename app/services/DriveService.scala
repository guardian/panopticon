package services

import java.io.FileInputStream

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.{DriveScopes, Drive => DriveClient}
import models.File

import scala.collection.JavaConverters._


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
            .map(file => File(id = file.getId, title = file.getName, output = file.getMimeType))
    }
}

