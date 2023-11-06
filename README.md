# Order Service

This service is part of the T2-Project.
It is responsible for saving orders to the database.

## Build and Run

Refer to the [Documentation](https://t2-documentation.readthedocs.io/en/latest/guides/deploy.html) on how to build, run or deploy the T2-Project services.

## Usage

This service listens to incoming messages on a queue named 'order'.
The [orchestrator](https://github.com/t2-project/orchestrator) sends messages to that queue.

Normally you do not want to interact directly with the order service.
However it might by useful to run it locally for debugging.

## Application Properties

| property | read from env var | description |
| -------- | ----------------- | ----------- |
| spring.data.mongodb.uri | MONGO_HOST | host of the mondo db |

Properties for the CDC.
see  [eventuate tram cdc](https://eventuate.io/docs/manual/eventuate-tram/latest/getting-started-eventuate-tram.html) for explanations.

| property | read from env var |
| -------- | ----------------- |
| spring.datasource.url | SPRING_DATASOURCE_URL |
| spring.datasource.username | SPRING_DATASOURCE_USERNAME |
| spring.datasource.password | SPRING_DATASOURCE_PASSWORD |
| spring.datasource.driver-class-name | SPRING_DATASOURCE_DRIVER_CLASS_NAME |
| eventuatelocal.kafka.bootstrap.servers | EVENTUATELOCAL_KAFKA_BOOTSTRAP_SERVERS |
| eventuatelocal.zookeeper.connection.string | EVENTUATELOCAL_ZOOKEEPER_CONNECTION_STRING |
