package controllers

import javax.inject._
import play.api.libs.json.Json
import play.api.mvc._
import services.DriveService._
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


  def getAllRecords = Action {
    val researchRecordList = for {
      apiFiles <- getAllApiFiles
      driveFiles = apiFiles.map(extractFileData)
    } yield driveFiles.map(transformToResearchRecord)

    researchRecordList match {
      case Left(error) => InternalServerError(error)
      case Right(records) => Ok(Json.toJson(records))
    }
  }
}
