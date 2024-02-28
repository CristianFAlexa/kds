FROM openjdk:17-oracle

COPY application.jar /var/app/current

CMD ["java", "-jar", "var/app/current/application.jar"]