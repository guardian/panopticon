package controllers

import javax.inject._
import play.api.mvc._
import services.DriveService

import scala.concurrent.ExecutionContext

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class DriveController @Inject()(cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

    /**
      * Create an Action to render an HTML page.
      *
      * The configuration in the `routes` file means that this method
      * will be called when the application receives a `GET` request with
      * a path of `/`.
      */

    val url = "https://www.googleapis.com/drive/v3/teamdrives/0AEc1auC83s1rUk9PVA"

    def getAllRecords {
        DriveService.getAllRecords
    }
}
