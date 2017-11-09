package models

import play.api.libs.json.Json

import play.api.data._
import play.api.data.Forms._

case class Webuser(username: String, age: Int)

object Webuser {
  implicit val webuserFormat = Json.format[Webuser]

  val webuserForm = Form(
    mapping(
      "user" -> nonEmptyText,
      "age" -> number(min = 18, max = 99)
    )
    (Webuser.apply)(Webuser.unapply)
  )
}