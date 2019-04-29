package util.httpclient

import akka.http.scaladsl.model.{HttpResponse, StatusCode, HttpHeader, HttpProtocol}

case class WrappedHttpResponse(
  val response: HttpResponse,
  val body: String
) {
  
  val status: StatusCode = response.status
  val headers: Seq[HttpHeader] = response.headers
  val protocol: HttpProtocol = response.protocol

}
