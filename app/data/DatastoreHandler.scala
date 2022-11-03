package data

import com.google.cloud.datastore.StructuredQuery.{CompositeFilter, OrderBy, PropertyFilter}
import com.google.cloud.datastore.{DatastoreOptions, Entity, Query, QueryResults, ReadOption}

import java.util
import scala.jdk.CollectionConverters.IteratorHasAsScala

object DatastoreHandler {
  private val datastore = DatastoreOptions.getDefaultInstance.getService
  private val kind = "imageId"

  def saveData(key: String, description: String, userName: String): Unit = { // Instantiates a client
    val taskKey = datastore.newKeyFactory.setKind(kind).newKey(key)
    val imageData = Entity.newBuilder(taskKey)
      .set("description", description)
      .set("userName", userName)
      .build
    datastore.put(imageData)
  }

  def getDescription(key: String): String = {
    val taskKey = datastore.newKeyFactory.setKind(kind).newKey(key)
    val retrieved: Entity = datastore.get(taskKey, Seq.empty[ReadOption]: _*)
    retrieved.getString("description")
  }

  def listUserPictures(userName: String): List[String] = {
    val query = Query.newEntityQueryBuilder()
        .setKind("Task")
        .setFilter(PropertyFilter.eq("userName", userName))
        .build();
    val tasks: QueryResults[Entity] = datastore.run(query, Seq.empty[ReadOption]: _*)
    tasks.asScala.map(_.getKey.getName).toList
  }

  def listAllImages(): List[String] = {
    val query = Query.newEntityQueryBuilder()
      .setKind("Task")
      .build();
    val tasks: QueryResults[Entity] = datastore.run(query, Seq.empty[ReadOption]: _*)
    tasks.asScala.map(_.getKey.getName).toList
  }

  def listUsers(): List[String] = {
    val query = Query.newEntityQueryBuilder()
      .setKind("Task")
      .build();
    val tasks: QueryResults[Entity] = datastore.run(query, Seq.empty[ReadOption]: _*)
    tasks.asScala.map(_.getString("userName")).toList.distinct
  }
}

object Test extends App {
  DatastoreHandler.listUsers()
}