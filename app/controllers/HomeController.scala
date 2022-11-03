package controllers

import data.CloudDataHandler
import play.api.mvc._

import javax.inject._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  val dataHandler = new CloudDataHandler

  def index: Action[AnyContent] = Action {
    val images = dataHandler.listImages
    Ok(views.html.index(images))
  }

  def foto(username: String, id: String): Action[AnyContent] = Action {
    val description = dataHandler.getDescription(username, id)
    Ok(views.html.foto(username, id, description))
  }

  def profile(username: String): Action[AnyContent] = Action {
    Ok(views.html.profile(username, dataHandler.listImages(username)))
  }

  def upload: Action[AnyContent] = Action {
    Ok(views.html.upload())
  }

  def overview: Action[AnyContent] = Action {
    val users = dataHandler.listUsers
    Ok(views.html.overview(users))
  }
}
