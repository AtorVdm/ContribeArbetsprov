# Book Store

### Requirements
* [Java 8][java8]
* [Apache Tomcat v8][tomcat]
* [Eclipse][eclipse]
* [Spring Boot v1.4.2][springbootplugin]

### Dependencies
* [Spring Boot Web Starter][spring-boot]
* [Spring Boot Test Starter][spring-boot-test]
* [Apache Commons Lang][commons-lang]
* [Apache Commons IO][commons-io]

### Running the project
* mvn clean insall
* run spring boot application starter using Spring Boot App configuration

### File structure
<pre>
├── pom.xml                                                         - application maven configuration file
├── src
│   ├── main                                                        - application source files
│   │   ├── java
│   │   │   └── com
│   │   │       └── atorvdm
│   │   │           └── contribe
│   │   │               ├── ContribeApplication.java                - spring boot application starter
│   │   │               ├── controller
│   │   │               │   ├── BaseBasket.java                     - an interface for a basic basket in the store
│   │   │               │   ├── BookList.java                       - an interface for a basic book list in the store
│   │   │               │   └── StoreController.java                - main controller responsible for restful interraction
│   │   │               ├── model
│   │   │               │   ├── Basket.java                         - model of a basket in the store
│   │   │               │   └── Book.java                           - model of a book in the store
│   │   │               └── util
│   │   │                   ├── MapContainer.java                   - container for Map objects
│   │   │                   ├── MapToCoupleArraySerializer.java     - class for Map serialization
│   │   │                   └── StoreUtils.java                     - class with methods-helpers
│   │   └── resources                                               - applicattion resources
│   │       ├── application.properties
│   └── test                                                        - application tests
│       └── java
│           └── com
│               └── atorvdm
│                   └── contribe
│                       ├── TestUtils.java
│                       ├── controller
│                       │   └── StoreControllerTest.java
│                       ├── model
│                       │   ├── BasketTest.java
│                       │   └── BookTest.java
│                       └── util
│                           └── StoreUtilsTest.java
└── target
</pre>

## Request examples
### Browsing the store
```shell
$ curl 'http://localhost:8080/store/list' -i -H 'Accept: application/json' -H 'Content-Type: application/json'
# or
$ curl 'http://localhost:8080/store/map' -i -H 'Accept: application/json' -H 'Content-Type: application/json'
```

### Adding a book to the store
```shell
$ curl 'http://localhost:8080/store/add' -i -X POST -H 'Accept: application/json' -H 'Content-Type: application/json' -d '{
    "title": "Generic Title",
    "author": "First Author",
    "price": 185.5
}'
```

### Buying books from the store
```shell
$ curl 'http://localhost:8080/store/buy' -i -X POST -H 'Accept: application/json' -H 'Content-Type: application/json' -d '[
	{
        "title": "Mastering åäö",
        "author": "Average Swede",
        "price": 762
    },
	{
        "title": "Desired",
        "author": "Rich Bloke",
        "price": 564.5
    }
]'
```

### Adding books to the basket
```shell
$ curl 'http://localhost:8080/store/addToBasket?quantity=2' -i -X POST -H 'Accept: application/json' -H 'Content-Type: application/json' -d '{
    "title": "Generic Title",
    "author": "First Author",
    "price": 185.5
}'
```

### Removing books from the basket
```shell
$ curl 'http://localhost:8080/store/removeFromBasket?quantity=1' -i -X POST -H 'Accept: application/json' -H 'Content-Type: application/json' -d '{
    "title": "Generic Title",
    "author": "First Author",
    "price": 185.5
}'
```

### Buying all books from the basket
```shell
$ curl 'http://localhost:8080/store/buyFromBasket' -i -X POST -H 'Accept: application/json' -H 'Content-Type: application/json'
```

### Browsing the basket
```shell
$ curl 'http://localhost:8080/store/basket' -i -H 'Accept: application/json' -H 'Content-Type: application/json'
```

[java8]:<http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html>
[tomcat]:<https://tomcat.apache.org/download-80.cgi>
[eclipse]:<http://www.eclipse.org/downloads/>
[springbootplugin]: <https://marketplace.eclipse.org/content/spring-tool-suite-sts-eclipse>
[spring-boot]: <https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web>
[spring-boot-test]: <https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test>
[commons-lang]: <https://mvnrepository.com/artifact/commons-lang/commons-lang>
[commons-io]: <https://mvnrepository.com/artifact/commons-io/commons-io>