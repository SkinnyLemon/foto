package data

import java.nio.file.{Files, Paths, StandardOpenOption}
import java.util.UUID
import scala.jdk.CollectionConverters.IteratorHasAsScala

class CloudDataHandler {
  def saveImage(imageBase64: String, username: String, description: String): String = {
    val image = javax.xml.bind.DatatypeConverter.parseBase64Binary(imageBase64)
    val id = UUID.randomUUID.toString

    DatastoreHandler.saveData(id, description, username)
    GoogleBucketHandler.uploadObject(id, image)

    id
  }

  def getImage(username: String, id: String): Array[Byte] = {
    GoogleBucketHandler.getObject(id)
  }

  def getDescription(username: String, id: String): String = {
    DatastoreHandler.getDescription(id)
  }

  def listImages(username: String): List[String] = {
    DatastoreHandler.listUserPictures(username)
  }

  def listUsers: List[String] = {
    DatastoreHandler.listUsers()
  }

  def listImages: List[String] = {
    DatastoreHandler.listAllImages()
  }
}
