# We will create a producer java application, a consumer java application, and a website
frontend

Goal of this app is to read sales data from csv files and save it in a database. To prove kafka, we send the csv line to kafka and then read them and save them in the database.

## Apps
Backend
➢ Java openJDK 11
➢ Java Spring boot
➢ Event platform
Frontend
➢ Node 16.14 or newer
➢ Angular
➢ TypeScript

### Csv producer
A Producer Java application: we constantly receive sales files within one day. For example, we 
have 3 files contain sales data of October, November, December. We want the application to 
loop for those 3 files in sequence every minute. After reading file, the application will use event 
platform (e.g. Kafka) to share data with other applications and archive the raw files in another 
folder.
Example:
07:01AM -> read October file.
07:02AM -> read November file.
07:03AM -> read December file.
07:04AM -> read October file.
07:05AM -> read November file

#### Install
```
cd csv-producer
mvn clean package
java -jar target/csv-producer-0.0.1-SNAPSHOT.jar
```

In logs, lines appear one by one

### csv-consumer
A Consumer Java application will listen to event. If there is any new message, the application 
can read this message and push data to the web site in real time (no need to refresh web 
page).

Keep listening the topic, waiting for messages. Its idempotent, duplicated messages will not break it.
#### Install
```
flyway is used to manage the database schema
To prevent issue: org.flywaydb.core.api.FlywayException: Validate failed: Detected applied migration not resolved locally
Run delete from demo_db.flyway_schema_history where version = '@version'
To compile and start:
```
cd csv-consumer
mvn clean package
java -jar target/csv-consumer-persist-0.0.1-SNAPSHOT.jar
```

### csv-consumer-log
Spring boot app which listen to the `test` topic and write a log ligne for each received message.
#### Install
```
cd csv-consumer-log
mvn clean package
java -jar target/csv-consumer-log-0.0.1-SNAPSHOT.jar
```

### tools
In directory `dev-tools`, there is a `docker-compose.yml` file to start kafka. 2 json file to test the consumers. You can send them to kafka using kafkacat.
