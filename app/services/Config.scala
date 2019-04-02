package services

import com.typesafe.config.ConfigFactory

object Config {
    private val conf = ConfigFactory.load()

    val serviceAccountKey = conf.getString("SERVICE_ACCOUNT_KEY")
    val teamDriveId = conf.getString("TEAMDRIVE_ID")
}
