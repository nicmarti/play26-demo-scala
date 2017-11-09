package services

import models.Webuser
import javax.inject._

import org.apache.commons.lang3.RandomStringUtils

trait WebuserService {

  def createManyWebusers

  def saveWebuser(webuser: Webuser)

  def listAll: List[Webuser]
}

@Singleton
class InMemoryWebuserService extends WebuserService {

  override def createManyWebusers: Unit = {
    for (j <- 18 to 80) {
      val webuser = Webuser(
        RandomStringUtils.randomAlphanumeric(10),
        j)
      saveWebuser(webuser)
    }

  }

  private var webusers: List[Webuser] = List()

  override def saveWebuser(webuser: Webuser): Unit = {
    webusers = webuser :: webusers
  }

  override def listAll: List[Webuser] = {
    if (webusers.isEmpty) {
      createManyWebusers
      webusers
    } else {

      webusers
    }

  }
}
