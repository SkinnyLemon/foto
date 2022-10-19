FROM hseeberger/scala-sbt:11.0.14.1_1.6.2_2.13.8 as builder
COPY . .
ENTRYPOINT sbt run