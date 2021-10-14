package data

import com.google.common.base.Supplier

import java.io.{ByteArrayInputStream, InputStream}
import java.nio.file.{Files, Paths, StandardOpenOption}
import java.util.UUID
import java.util.stream.Collector
import scala.jdk.CollectionConverters.IteratorHasAsScala

class FileDataHandler {
  def saveImage(imageBase64: String, username: String, description: String): String = {
    val image = javax.xml.bind.DatatypeConverter.parseBase64Binary(imageBase64)

    val id = UUID.randomUUID.toString

    val imageDirectory = s"resources/images/$username"
    val descriptionDirectory = s"resources/descriptions/$username"
    val imageFile = Paths.get(s"$imageDirectory/$id.jpg")
    val descriptionFile = Paths.get(s"$descriptionDirectory/$id.txt")

    Files.createDirectories(Paths.get(imageDirectory))
    Files.createDirectories(Paths.get(descriptionDirectory))
    Files.write(imageFile, image, StandardOpenOption.CREATE)
    Files.write(descriptionFile, description.getBytes, StandardOpenOption.CREATE)

    id
  }

  def getImage(username: String, id: String): Array[Byte] = {
    val file = Paths.get(s"resources/images/$username/$id.jpg")
    Files.readAllBytes(file)
  }

  def getDescription(username: String, id: String): String = {
    val file = Paths.get(s"resources/descriptions/$username/$id.txt")
    Files.readString(file)
  }

  def listImages(username: String): List[String] = {
    val folder = Paths.get(s"resources/descriptions/$username")
    Files.list(folder).map(_.getFileName.toString.dropRight(4)).iterator().asScala.toList
  }

  def listUsers: List[String] = {
    val folder = Paths.get(s"resources/descriptions")
    Files.list(folder).map(_.getFileName.toString).iterator().asScala.toList
  }

  def listImages: List[String] = {
    listUsers.flatMap(user =>
      listImages(user)
        .map(id => s"$user/$id"))
  }
}
