package controllers

import data.CloudDataHandler
import play.api.libs.json.JsValue
import play.api.mvc._

import javax.inject._

@Singleton
class UploadController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  val dataHandler = new CloudDataHandler
  def upload: Action[JsValue] = Action(parse.json(10000000)) { request =>
    val body = request.body
    val base64Image = body("data").toString.split(",")(1)
    val username = body("username").toString.drop(1).dropRight(1)
    val description = body("description").toString.drop(1).dropRight(1)
    val id = dataHandler.saveImage(base64Image, username, description)
    Ok(id)
  }
}
