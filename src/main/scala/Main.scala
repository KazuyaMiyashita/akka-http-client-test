
import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import scala.util.{Try, Success, Failure}
// import scala.concurrent.ExecutionContext.Implicits.global
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}

import util.httpclient.{HttpClient, WrappedHttpResponse}

object Main extends App {

  def loop(): Unit = {
    print("> ")
    readLine match {
      case x if x == null || x == "" => {
        println("Bye!")
        HttpClient.terminate()
      }
      case url => {
        val resFuture: Future[WrappedHttpResponse] = HttpClient.get(url)
        Await.ready(resFuture, Duration.Inf)
        resFuture.value.get match {
          case Success(response) => println(response)
          case Failure(f) => println(f)
        }
        loop()
      }
    }
  }

  loop()


}

