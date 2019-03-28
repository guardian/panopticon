import scala.sys.process.Process

/*
 * Client Build hook Scripts
 */

// Execution status success.
val Success = 0

// Execution status failure.
val Error = 1

// Run serve task when Play runs in dev mode, that is, when using 'sbt run'
// https://www.playframework.com/documentation/2.7.x/SBTCookbook
PlayKeys.playRunHooks += baseDirectory.map(ClientRunHook.apply).value

// True if build running operating system is windows.
val isWindows = System.getProperty("os.name").toLowerCase().contains("win")

// Execute on commandline, depending on the operating system. Used to execute npm commands.
def runOnCommandline(script: String)(implicit dir: File): Int = {
  if(isWindows){ Process("cmd /c set CI=true&&" + script, dir) } else { Process("env CI=true " + script, dir) } }!

// Check of node_modules directory exist in given directory.
def isNodeModulesInstalled(implicit dir: File): Boolean = (dir / "node_modules").exists()

// Execute `npm install` command to install all node module dependencies. Return Success if already installed.
def runNpmInstall(implicit dir: File): Int =
  if (isNodeModulesInstalled) Success else runOnCommandline(ClientCommands.dependencyInstall)

// Execute task if node modules are installed, else return Error status.
def ifNodeModulesInstalled(task: => Int)(implicit dir: File): Int =
  if (runNpmInstall == Success) task
  else Error

// Execute frontend test task. Update to change the frontend test task.
def executeClientTests(implicit dir: File): Int = ifNodeModulesInstalled(runOnCommandline(ClientCommands.test))

// Execute frontend prod build task. Update to change the frontend prod build task.
def executeProdBuild(implicit dir: File): Int = ifNodeModulesInstalled(runOnCommandline(ClientCommands.build))


// Create frontend build tasks for prod, dev and test execution.

lazy val `client-test` = taskKey[Unit]("Run client tests when testing application.")

`client-test` := {
  implicit val userInterfaceRoot = baseDirectory.value / " client"
  if (executeClientTests != Success) throw new Exception("Client tests failed!")
}

lazy val `client-prod-build` = taskKey[Unit]("Run client build when packaging the application.")

`client-prod-build` := {
  implicit val userInterfaceRoot = baseDirectory.value / "client"
  if (executeProdBuild != Success) throw new Exception("Oops! Client Build crashed.")
}

// Execute frontend prod build task prior to play dist execution.
dist := (dist dependsOn `client-prod-build`).value

// Execute frontend prod build task prior to play stage execution.
stage := (stage dependsOn `client-prod-build`).value

// Execute frontend test task prior to play test execution.
test := ((test in Test) dependsOn `client-test`).value