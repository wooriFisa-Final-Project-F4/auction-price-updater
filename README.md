# Auction-Service

## Overview

Confluent Kafka를 이용한 프로젝트입니다. 이 프로젝트는 다음과 같은 주요 기능 및 라이브러리를 활용하고 있습니다

- Confluent Kafka
- OpenFeign Client
- Eureka Client for service discovery
- Spring Cloud Config for centralized configuration

## Requirements

- Java 17
- Spring Boot
- Confluent Kafka
- OpenFeign Client

## Stack

<p align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" alt="java" width="40" height="40"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" alt="spring" width="40" height="40"/>
  <img src="https://companieslogo.com/img/orig/CFLT-c4a50286.png?t=1627024622" alt="redis" width="40" height="40"/>
</p>

## 데이터베이스

<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-plain.svg" width="40" height="40"/>
          

## Mechanism

![image](https://github.com/wooriFisa-Final-Project-F4/auction-price-updater/assets/119636839/ed712910-fd75-4f59-bda0-8ca1dff81322)

Auction-Service에서 발행한 Kafka Event의 Value를 사용하여 입찰 결과를 판단<br>
입찰 결과를 저장하기 위한 Event 발행

- 현재 입찰가보다 입찰 요청 금액이 낮은 경우 입찰 실패
- 입찰 요청자와 현재 입찰자가 같을 경우 입찰 실패
- 입찰 성공 시 상품 경매 정보 수정
- 입찰 성공 시 Mock API로 입찰 요청자와 기존 입찰자의 경매 사용 금액 변경 요청

<br><br>
---
