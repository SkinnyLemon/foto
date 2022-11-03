package data

import com.google.cloud.datastore.StructuredQuery.{CompositeFilter, OrderBy, PropertyFilter}
import com.google.cloud.datastore.{DatastoreOptions, Entity, Query, QueryResults, ReadOption}

import java.util
import scala.collection.mutable.ListBuffer
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
        .setKind(kind)
        .setFilter(PropertyFilter.eq("userName", userName))
        .build();
    val queryResult: QueryResults[Entity] = datastore.run(query, Seq.empty[ReadOption]: _*)

    val results = new ListBuffer[String]
    while (queryResult.hasNext)
      results += queryResult.next().getKey().getName
    results.toList
  }

  def listAllImages(): List[(String, String)] = {
    val query = Query.newEntityQueryBuilder()
      .setKind(kind)
      .build();
    val queryResult: QueryResults[Entity] = datastore.run(query, Seq.empty[ReadOption]: _*)

    val results = new ListBuffer[(String, String)]
    while (queryResult.hasNext) {
      val next = queryResult.next()
      results += (next.getString("userName") -> next.getKey().getName)
    }
    results.toList
  }

  def listUsers(): List[String] = {
    val query = Query.newEntityQueryBuilder()
      .setKind(kind)
      .build();
    val queryResult: QueryResults[Entity] = datastore.run(query, Seq.empty[ReadOption]: _*)

    val results = new ListBuffer[String]
    while (queryResult.hasNext)
      results += queryResult.next().getString("userName")
    results.distinct.toList
  }
}
