package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 *
 * For more information, see https://www.playframework.com/documentation/latest/ScalaTestingWithScalaTest
 */
class DriveControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {

  "DriveController getAllRecords" should {

    "fetch json content from Google Drive API" in {
      val controller = new DriveController(stubControllerComponents())
      val getAllRecords = controller.getAllRecords.apply(FakeRequest(GET, "/api/getAllRecords"))

      status(getAllRecords) mustBe OK
      contentType(getAllRecords) mustBe Some("application/json")
    }

//    "render the index page from the application" in {
//      val controller = inject[HomeController]
//      val home = controller.index().apply(FakeRequest(GET, "/"))
//
//      status(home) mustBe OK
//      contentType(home) mustBe Some("text/html")
//      contentAsString(home) must include ("Welcome to Play")
//    }
//
//    "render the index page from the router" in {
//      val request = FakeRequest(GET, "/")
//      val home = route(app, request).get
//
//      status(home) mustBe OK
//      contentType(home) mustBe Some("text/html")
//      contentAsString(home) must include ("Welcome to Play")
//    }
  }
}
