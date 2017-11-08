package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()(messagesAction: MessagesActionBuilder, cc: ControllerComponents) extends AbstractController(cc) {

  def index = messagesAction {
    implicit request: MessagesRequest[AnyContent] =>
      Ok(views.html.index())
  }

  def demoTest = messagesAction {
    implicit request: MessagesRequest[AnyContent] =>
      Ok(views.html.demoTest("Message sent from the controller"))
  }

}
