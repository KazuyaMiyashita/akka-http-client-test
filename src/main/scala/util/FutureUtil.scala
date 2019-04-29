package util

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object FutureUtil {
  
  def calcTime[T](future: => Future[T]): Future[(T, Long)] = {
    val start = System.currentTimeMillis

    future map { future =>
      (future, System.currentTimeMillis - start)
    }
  }

}
