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

@Singleton
class AsyncController @Inject()(cc: ControllerComponents, actorSystem: ActorSystem, ws: WSClient)(implicit exec: ExecutionContext) extends AbstractController(cc) {

  def message() = Action.async {
    getFutureMessage(1.second).map { msg => Ok(msg) }
  }

  private def getFutureMessage(delayTime: FiniteDuration): Future[String] = {
    val promise: Promise[String] = Promise[String]()
    actorSystem.scheduler.scheduleOnce(delayTime) {
      promise.success("Coucou demo Lunatech !")
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
