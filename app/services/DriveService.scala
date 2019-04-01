package services

import java.io.FileInputStream

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.drive.model.{FileList, TeamDrive}
import com.google.api.services.drive.{Drive => DriveClient}

object DriveService {

    private val httpTransport = new NetHttpTransport()
    private val jacksonFactory = new JacksonFactory()

    private val googleCredential: GoogleCredential = new GoogleCredential.Builder()
        .setTransport(httpTransport)
        .setJsonFactory(jacksonFactory)
        .setServiceAccountPrivateKeyFromP12File(new FileInputStream("editorial-tools-training-f61d89f22df0.p12.json"))
        .build

    lazy val driveClient: DriveClient = new DriveClient.Builder(httpTransport, jacksonFactory, googleCredential)
      .build()

    def getAllRecords: FileList = {
        val request = driveClient.files().list()
            .setCorpora("teamDrive")
            .setIncludeTeamDriveItems(true)
            .setSupportsTeamDrives(true)
            .setTeamDriveId("insert team drive ID")

        request.execute()
    }
}
