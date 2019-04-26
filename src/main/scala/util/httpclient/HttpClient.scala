package util.httpclient

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}

import scala.concurrent.Future
object HttpClient {

  def get(url: String): Future[HttpResponse] = {

    implicit val system = ActorSystem()
    implicit val executionContext = system.dispatcher

    val responseFuture: Future[HttpResponse] = Http().singleRequest(
      HttpRequest(
        uri = url
      )
    )
    responseFuture
  }

}
