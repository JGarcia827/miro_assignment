# README

This software application was built to satifify the requirements as described in file [Miro Take Home Test](./docs/Miro_Take_Home_Test.pdf).

## Requirements

- Java JRE 11 or later

## Setup

To setup the application, execute the following command inside the ```assignment``` project folder:

```mvn clean package```

Note that all tests will be also executed. 

## Use

To start the application execute the following command inside the ```assignment``` project folder:

```mvn spring-boot:run```

By default the REST API can be reached at: ```http://localhost:8080/api/v1/widgets/```

The application's port can be configured in [application.properties](./src/main/resources/application.properties).

## REST Documentation

The REST API documentation can be found at [http://localhost:8080/v2/api-docs](http://localhost:8080/v2/api-docs). A Swagger UI environment is also available at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html), which also provides easy interaction with the REST API. Note that these links are only available when the application is running.

## Clarifications

### Complication 2 - Filtering

The actual implemenation runs in O(n) flat, and not with an average less than O(n). This could be achieved if the llist of points was ordered in ascending/descending order of the coordinate of one of the four points of a Widget. When traveling the list a point outside the given area automatically disqulifies that Widget and all the elements after is (and thus achieving an average less than O(n)). However, sorting the list itself carries a O(n logn) cost, and since we only have a list ordered by ascending z-index order, sorting will also have to be performed.

## Built with

- Java Open-JDK 11
- Spring Boot
- Spring Initializr
- Maven
- Visual Studio Code
- See `pom.xml` for other libraries and tools

## Logging

To easily monitor RESt activity a log file can be found at ```\assignment\logs\rest.log``` (generated on app startup). Of course, all application activity can also be monitored through the console.

## Demo/test data

To show the application's capabilities without previous data input, some demo data is automatically loaded at startup (which is also used in the tests). You can review it at [\assignment\src\main\resources\data.sql](\assignment\src\main\resources\data.sql)

## Other information

- Author: José García

PS: I hope that you have as much fun reviewing this application as I had building it :)