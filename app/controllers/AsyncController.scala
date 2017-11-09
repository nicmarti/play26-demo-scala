package controllers

import javax.inject._

import akka.actor.ActorSystem
import play.api.mvc._

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future, Promise}
import javax.inject.Inject

import scala.concurrent.Future
import scala.concurrent.duration._
import play.api.mvc._
import play.api.http.HttpEntity
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString
import play.api.libs.ws.WSClient

import scala.concurrent.ExecutionContext

/**
  * This controller creates an `Action` that demonstrates how to write
  * simple asynchronous code in a controller. It uses a timer to
  * asynchronously delay sending a response for 1 second.
  *
  * @param cc          standard controller components
  * @param actorSystem We need the `ActorSystem`'s `Scheduler` to
  *                    run code after a delay.
  * @param exec        We need an `ExecutionContext` to execute our
  *                    asynchronous code.  When rendering content, you should use Play's
  *                    default execution context, which is dependency injected.  If you are
  *                    using blocking operations, such as database or network access, then you should
  *                    use a different custom execution context that has a thread pool configured for
  *                    a blocking API.
  */
@Singleton
class AsyncController @Inject()(cc: ControllerComponents, actorSystem: ActorSystem, ws: WSClient)(implicit exec: ExecutionContext) extends AbstractController(cc) {

  /**
    * Creates an Action that returns a plain text message after a delay
    * of 1 second.
    */
  def message() = Action.async {
    getFutureMessage(1.second).map { msg => Ok(msg) }
  }

  private def getFutureMessage(delayTime: FiniteDuration): Future[String] = {
    val promise: Promise[String] = Promise[String]()
    actorSystem.scheduler.scheduleOnce(delayTime) {
      promise.success("Coucou BDX.IO !!")
    }(actorSystem.dispatcher) // run scheduled tasks using the actor system's dispatcher
    promise.future
  }

  def loadRemoteWebservice() = Action.async {
    play.Logger.info("Start ***************")

    val futureResponse = ws.url("http://localhost:9000/users").get()

    val futureResult = futureResponse.map {
      response =>
        play.Logger.info("I am in the body, I got HTTP " + response.status)
        Ok(response.body)
    }

    play.Logger.info("End *******************")
    futureResult
  }

}
