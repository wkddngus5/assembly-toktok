# 국회 톡톡

## 프로젝트 구성

|DIRECTORY|설명|
|:--:|:--|
|spring-boot|백엔드 코드|
|front|프론트엔드 코드 - 기존코드의 백업으로 필요없는 경우 삭제합니다.|
|spring-boot/src/main/resources/public|정적 웹 리소스 위치입니다. 기존 작성된 웹코드를 옮겨 놓았습니다.|

## 백엔드 실행

* gradle 로 구성됨

  * osx, linux
  ```
  ./gradlew bootRun
  ```
  * windows
  ```
  gradlew.bat bootRun
  ```

  jar 파일 실행후 localhost:8080 으로 확인 가능

  ```
  curl localhost:8080
  ```

  또는 브라우저에서 http://loaclhost:8080 접속

## 참고사항

* 현재 분석단계로 프론트엔드 측을 서버사이드 렌더로 할지, 분리를 할지 정하지 못했습니다.
* UI 쪽 정적 리소스 작업은 해당사항 참고하여 작업 부탁드립니다.
