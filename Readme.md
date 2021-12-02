**HOW TO RUN** 

First you need to clone this project on your local and start your Elasticsearch and Kafka on default port.
To run go to your project directory do **sbt run**, It will run the Main class by itself


In this techhub tempalte ,I basically focused on ES-Kafka Connector using Spark taking small customer example having attributes as id, name and address

First, You have to create one elastic search index named as **demo**. Then put add data under doc type **external** using the below command :-

_**curl -XPUT 'localhost:9200/demo/external/1?pretty' -d '
{
"id":"1"
"name": "Ram",
"address" : "Delhi"

}'**_


You can also create a kafka topic using command :- **bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic input**
Otherwise the code will automatic topic with default partition and replication factor

Now start consumer for output topic using command(for checking the data)  :- **bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic output --property "print.key=true" --from-beginning**


