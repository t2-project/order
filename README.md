# Order Service

This service is part of the T2 Store.
It is responsible for saving orders to the database.

## Build and Run

### Preparation 1

Set environment variables. Either manually or by sourcing this file: [setenv.sh](https://github.com/t2-project/path/to/setenv.sh)

```
wget TODO
. ./setenv.sh
```

### Preparation 2

Set the [applicaton properites](https://github.com/t2-project/order/tree/main/src/main/resources) according to your local setup. 
Conferere [this section](#application-properties) for more details.


### Build Dependency

This service depends on [common](https://github.com/t2-project/common), thus you need to build common first:
```
git clone git@github.com:t2-project/common.git
cd common/
mvn clean install
```

And install it to your local maven repository:  
```
mvn install:install-file -Dfile=./target/common-1.0-SNAPSHOT.jar  -DpomFile=./pom.xm
```

### Build and Run Order Service

Now you can build and run the order service:
```
git clone git@github.com:t2-project/order.git
cd order/
./mvnw spring-boot:run
```

### Build Docker Image

There is a Docker file in this repository. 
You may build a docker image like this : 
```
docker build .
```

A docker image of this service can also be found on DockerHub : [stiesssh/order](https://hub.docker.com/r/stiesssh/order)

## Usage

This service listens to a messaging queue named ``order``. 

You do not want to interact with this service directly.


## Application Properties

property | read from env var | description |
-------- | ----------------- | ----------- |
spring.data.mongodb.uri | MONGO_HOST | host of the mondo db 

properties for the CDC. 
see  [eventuate tram cdc](https://eventuate.io/docs/manual/eventuate-tram/latest/getting-started-eventuate-tram.html) for explanations.

property | read from env var |
-------- | ----------------- |
spring.datasource.url | SPRING_DATASOURCE_URL |
spring.datasource.username | SPRING_DATASOURCE_USERNAME |
spring.datasource.password | SPRING_DATASOURCE_PASSWORD |
spring.datasource.driver-class-name | SPRING_DATASOURCE_DRIVER_CLASS_NAME |
eventuatelocal.kafka.bootstrap.servers | EVENTUATELOCAL_KAFKA_BOOTSTRAP_SERVERS |
eventuatelocal.zookeeper.connection.string | EVENTUATELOCAL_ZOOKEEPER_CONNECTION_STRING |
