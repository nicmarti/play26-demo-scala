package controllers


import javax.inject._

import models.Webuser
import play.api.mvc._
import services.WebuserService

@Singleton
class WebuserController @Inject()(messagesAction: MessagesActionBuilder, cc: ControllerComponents, webuserService:WebuserService) extends AbstractController(cc) {

  def newWebuser = messagesAction {
    implicit request: MessagesRequest[AnyContent] =>
      Ok(views.html.newWebuser(Webuser.webuserForm))
  }

  def saveWebuser = messagesAction {
    implicit request: MessagesRequest[AnyContent] =>

      Webuser.webuserForm.bindFromRequest().fold(
        hasErrors => BadRequest(views.html.newWebuser(hasErrors)),
        validNewWebuser => {
          webuserService.saveWebuser(validNewWebuser)
          Redirect(routes.WebuserController.listAllWebusers()).flashing("success"->"Webuser created")
        }
      )
  }

  def listAllWebusers = messagesAction{
    implicit req:MessagesRequest[AnyContent]=>
      Ok(views.html.listAllWebusers(webuserService.listAll))
  }
}
