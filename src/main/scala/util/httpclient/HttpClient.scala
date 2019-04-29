package util.httpclient

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.model.headers.HttpEncodings
import akka.http.scaladsl.coding.{Gzip, Deflate, NoCoding}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.util.ByteString

import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import akka.stream.ActorMaterializer

object HttpClient {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  def get(url: String): Future[WrappedHttpResponse] = {

    val responseFuture: Future[HttpResponse] = 
      Http().singleRequest(HttpRequest(uri = url))

    responseFuture
      .map(decodeResponse)
      .flatMap(wrapHttpResponse)
  }

  private def decodeResponse(res: HttpResponse): HttpResponse = {
    val decoder = res.encoding match {
      case HttpEncodings.gzip => Gzip
      case HttpEncodings.deflate => Deflate
      case HttpEncodings.identity => NoCoding
      case _ => NoCoding
    }

    decoder.decodeMessage(res)
  }

  def extractBody(res: HttpResponse): Future[String] = {
    Unmarshal(res.entity).to[String]
  }

  def wrapHttpResponse(res: HttpResponse): Future[WrappedHttpResponse] = {
    val future: Future[String] = extractBody(res)
    future.map { body =>
      WrappedHttpResponse(res, body)
    }
  }

  def terminate(): Unit = {
    system.terminate()
  }

}
