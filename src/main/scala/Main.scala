
import scala.concurrent.Future
import scala.util.{Try, Success, Failure}
import scala.concurrent.ExecutionContext.Implicits.global

import util.httpclient.HttpClient

object Main extends App {

  val url = io.StdIn.readLine

  val resFuture: Future[Option[String]] = HttpClient.get(url)

  resFuture.onComplete {
    case Success(messageOpt) => messageOpt match {
      case Some(message) => println(message)
      case None => println("none")
    }
    case Failure(f) => println(f)
  }

}

