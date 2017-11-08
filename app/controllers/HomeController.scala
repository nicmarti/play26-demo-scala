package controllers

import javax.inject._
import play.api.mvc._

@Singleton
class HomeController @Inject()(messagesAction: MessagesActionBuilder, cc: ControllerComponents) extends AbstractController(cc) {

  def index = messagesAction {
    implicit request: MessagesRequest[AnyContent] =>
      Ok(views.html.index("Message sent from the controller"))
  }

}
