#################
# Build the  JAVA APP #

FROM openjdk:17-jdk-alpine
VOLUME /tmp
ADD target/employee-management-tool-0.0.1-SNAPSHOT.jar employee-management-tool.jar
ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /employee-management-tool.jar" ]