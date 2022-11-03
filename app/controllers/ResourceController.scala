package controllers

import data.CloudDataHandler
import play.api.mvc._

import javax.inject._

@Singleton
class ResourceController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  val dataHandler = new CloudDataHandler
  def image(username: String, id: String): Action[AnyContent] = Action { request =>
    Ok(dataHandler.getImage(username, id))
  }
}
