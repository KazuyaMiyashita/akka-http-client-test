
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import scala.util.{Try, Success, Failure}
import scala.concurrent.ExecutionContext.Implicits.global
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}

import util.httpclient.HttpClient

object Main extends App {

  def printResponse(response: HttpResponse): Unit = {
    println("status: %s".format(response.status))
    println("headers: %s".format(response.headers.map(s => "\n  - %s".format(s)).mkString))
    println("protocol: %s".format(response.protocol))
    println("entity: \n%s".format(HttpClient.extractBody(response)))
  }

  def isExit(s: String): Boolean = s == null || s == "" || s == "exit"

  var continue = true
  while(continue) {
    print("> ")
    io.StdIn.readLine match {
      case x if isExit(x) => {
        println("Bye!")
        continue = false
        HttpClient.terminate()
      }
      case url => {
        val resFuture: Future[HttpResponse] = HttpClient.get(url)
        Await.ready(resFuture, Duration.Inf)
        resFuture.value.get match {
          case Success(response) => printResponse(response)
          case Failure(f) => println(f)
        }
      }
    }
  }


}

