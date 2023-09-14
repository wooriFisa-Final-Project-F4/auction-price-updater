# Auction-Price-Updater
![image](https://user-images.githubusercontent.com/119636839/267495758-ed712910-fd75-4f59-bda0-8ca1dff81322.png)
> Auction-Service에서 발행한 이벤트를 구독하는 컨슈머가 포함된 서버입니다.<br> 
> Record의 Value를 활용해 새로운 이벤트를 발행하는 프로듀서가 포함된 서버입니다.<br> 
> 위 서버는 다른 서비스의 API를 사용하기 위해 Open Feign 통신을 합니다.
<br>


## 🛠️ Dependency
|       기능       | 기술 스택                                                                       |
|:--------------:|:----------------------------------------------------------------------------|
|  Spring Boot   | - Spring Framework 2.7.15<br> - Java 17 <br> - Gradle 8.0 <br> - Spring Web |
|  Spring Cloud  | - Eureka <br> - Config <br> - Gateway <br> - OpenFeign        |
|Kafka|- Confluent Kafka 7.4.0<br> - Zookeeper 7.4.0 |
|    Database    | - Mysql 8.33                      |
|      ORM       | - JPA <br>- Native Query          |

<br>

## 📝 Auction-Price-Updater 기능

|   기능   | 내용                                                                                                 |
|:------:|:---------------------------------------------------------------------------------------------------|
|  입찰 성공 여부     | Record의 Value를 사용하여 입찰 성공 여부 판단     |
|  이벤트 발행        | 입찰 요청 결과를 저장하기 위한 Kafka Event 발행   |

<br>

<details>
<summary> 입찰 성공 여부  접기/펼치기</summary>

<h3> ConsumerRecord<String, KafkaDTO> </h3>

<table>
    <tr>
        <td> Name </td>
        <td> Type </td>
    </tr>
    <tr>
        <td> userId </td>
        <td> Long </td>
    </tr>
    <tr>
        <td> productId </td>
        <td> Long </td>
    </tr>
    <tr>
        <td> price </td>
        <td> String </td>
    </tr>
    <tr>
        <td> password </td>
        <td> String </td>
    </tr>
    <tr>
        <td> time </td>
        <td> LocalDateTime </td>
    </tr>
</table>

<h3> ProducerRecord<Long, SendToHisotryDTO> </h3>
<table>
    <tr>
        <td> Name </td>
        <td> Type </td>
    </tr>
    <tr>
        <td> bidUserId </td>
        <td> Long </td>
    </tr>
    <tr>
        <td> productId </td>
        <td> Long </td>
    </tr>
    <tr>
        <td> productName </td>
        <td> String </td>
    </tr>
    <tr>
        <td> productImage </td>
        <td> String </td>
    </tr>
    <tr>
        <td> bidPrice </td>
        <td> String </td>
    </tr>
    <tr>
        <td> userEmail </td>
        <td> String </td>
    </tr>
    <tr>
        <td> bidStatus </td>
        <td> String </td>
    </tr>
    <tr>
        <td> bidTime </td>
        <td> LocalDateTime </td>
    </tr>
</table>
<br>

```
- userId를 사용하여 Event 발행에 필요한 user 정보 요청

- productId를 사용하여 DB에서 상품 조회
  상품의 현재 입찰가가 입찰 요청 금액보다 높을 경우 입찰 실패
  상품의 현재 입찰자와 입찰 요청자가 같을 경우 입찰 실패

- 입찰에 성공한 경우 Mock API에 기존 입찰자, 현재 입찰자의 사용 가능 금액 변경 요청
  입찰에 성공한 경우 상품의 경매 정보 변경

- 입찰 요청 기록을 위한 Kafka Event 발행
  (같은 상품은 같은 파티션에 전달될 수 있도록 설정)

```
</details>
<br>



## Auction-Price-Updater Prooperties

```properties

#Basic
server.port=[port 번호]

# EUREKA
eureka.client.service-url.defaultZone=[Eureka-server-ip]/eureka

# MYSQL
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=[database 설정]
spring.datasource.username=[DB user id]

# JPA
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update

# KAFKA CONSUMER
spring.kafka.bootstrap-servers=[Kafka Broker ip]
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.LongSerializer
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
spring.kafka.consumer.group-id=auction-price-updater
spring.kafka.consumer.auto-offset-reset=earliest
spring.kafka.consumer.key-deserializer=org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
spring.kafka.consumer.properties.spring.deserializer.key.delegate.class=org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.properties.spring.deserializer.value.delegate.class=org.springframework.kafka.support.serializer.JsonDeserializer
spring.kafka.consumer.properties.spring.json.trusted.packages=*

# LOGGING
logging.pattern.console=%green(%d{yyyy-MM-dd HH:mm:ss.SSS}) %magenta([%thread]) %highlight(%-5level) %cyan(%logger{36}) - %yellow(%msg%n)
logging.level.org.hibernate.SQL=debug
logging.file.path=logs

#Values
kafka.topic.name=[Consumer topic name]
kafka.produce.topic.name=[Producer topic name]
mock.url=[Mock API url]
```


