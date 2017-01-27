/**
  * Created by gianfrancob on 1/19/17.
  */
package dv.twitterstream

import org.apache.kafka.clients.producer.ProducerRecord

import scala.sys.addShutdownHook

object IngestApp extends App with Logging {
  var closing = false

  // close connection gracefully
  addShutdownHook {
    closing = true
    producer.close
    source.hosebirdClient.stop
  }

  log.info(Settings.config.toString)

  val source = Settings.tweetSource
  val producer = Settings.kafkaProducer
  val topic = Settings.rawTopic
  val partition = Settings.partition

  while (!source.hosebirdClient.isDone & !closing) {
    source.take() match {
      case Some(json) =>
        send(json)
      case None =>
    }
  }

  def send(msg: String): Unit = {
    val key = TweetKey(Settings.filterTerms)
    val keyPayload = Json.ByteArray.encode(key)
    val payload = msg.map(_.toByte).toArray
    val record = new ProducerRecord[Array[Byte], Array[Byte]](topic, partition, System.currentTimeMillis(), keyPayload, payload)
    log.info(s"Sending to Kafka ${record}")
    producer.send(record)
  }
}
