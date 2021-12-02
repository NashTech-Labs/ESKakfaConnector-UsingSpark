package com.example

import org.apache.spark._
import org.apache.spark.SparkContext
import org.elasticsearch.spark.rdd.EsSpark
import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.kafka.clients.producer.ProducerRecord
import play.api.libs.json._


object Main {

  def main(args:Array[String]):Unit={

      Logger.getLogger("demo").setLevel(Level.DEBUG)   //Setting the log level - DEBUG,ERROR,LOG
      System.setProperty("hadoop.home.dir", "/opt/spark")   //Setting the spark home directory

      val  conf = new SparkConf().setAppName("ES to Kafka").setMaster("local")
                    .set("es.nodes","localhost")
                    .set("es.port","9200")
                    .set("es.index.read.missing.as.empty","true")
      val sc = new SparkContext(conf)

      val rdd = EsSpark.esJsonRDD(sc,"demo/external")

      val data = rdd.flatMap{
          case (_, soJson) => {
              for (
                soSystem <- Customer.fromJson(soJson)
              ) yield {
                  Customer(
                    id = soSystem.id,
                    name = soSystem.name,
                    address = soSystem.address
                  )
              }
          }
      }

      data.coalesce(3).foreachPartition(
        partitionRecord => {
            val producer = KafkaStringProducer.createProducer("localhost:9092")
            val topic = "output"
            partitionRecord.foreach(record => {
                val id = record.id
                val json = Json.toJson(record).toString
                println(s"Publishing data info for Id ${id} to Kafka")
                val data = new ProducerRecord[String, String](topic, id, json)
                producer.send(data)

           })
        producer.close()
      })

    sc.stop()

  }
}
