FROM java:openjdk-8u91-jdk
MAINTAINER jmcelwain@gmail.com
EXPOSE 8080
ADD wait-for-it.sh wait-for-it.sh
RUN chmod +x ./wait-for-it.sh
ADD vielheit-services-0.0.1-SNAPSHOT.jar app.jar
