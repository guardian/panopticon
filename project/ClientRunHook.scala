import play.sbt.PlayRunHook
import sbt._

import scala.sys.process.Process

/**
  * Frontend build play run hook.
  * https://www.playframework.com/documentation/2.7.x/SBTCookbook
  */
object ClientRunHook {
  def apply(base: File): PlayRunHook = {
    object ClientBuildHook extends PlayRunHook {

      var process: Option[Process] = None
      
      var install: String =  ClientCommands.dependencyInstall
      var run: String = ClientCommands.serve

      // Windows requires npm commands prefixed with cmd /c
      if (System.getProperty("os.name").toLowerCase().contains("win")){
        install = "cmd /c" + install
        run = "cmd /c" + run
      }

      /**
        * Executed before play run start.
        * Run npm install if node modules are not installed.
        */
      override def beforeStarted(): Unit = {
        if (!(base / "client" / "node_modules").exists()) Process(install, base / "client").!
      }

      /**
        * Executed after play run start.
        * Run npm start
        */
      override def afterStarted(): Unit = {
        process = Option(
          Process(run, base / "client").run
        )
      }

      /**
        * Executed after play run stop.
        * Cleanup frontend execution processes.
        */
      override def afterStopped(): Unit = {
        process.foreach(_.destroy())
        process = None
      }

    }

    ClientBuildHook
  }
}
