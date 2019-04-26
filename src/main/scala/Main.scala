
import scala.concurrent.Future
import scala.util.{Try, Success, Failure}
import scala.concurrent.ExecutionContext.Implicits.global
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}

import util.httpclient.HttpClient

object Main extends App {

  def printResponse(response: HttpResponse): Unit = {
    println("status: %s".format(response.status))
    println("headers:\n%s".format(response.headers.mkString("¥n¥t")))
    println("entity: %s".format(response.entity))
    println("protocol: %s".format(response.protocol))
  }

  while(true) {
    val url = io.StdIn.readLine

    val resFuture: Future[HttpResponse] = HttpClient.get(url)

    resFuture.onComplete {
      case Success(response) => printResponse(response)
      case Failure(f) => println(f)
    }
  }


}

